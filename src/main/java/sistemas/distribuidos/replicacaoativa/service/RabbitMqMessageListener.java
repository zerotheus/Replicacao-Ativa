package sistemas.distribuidos.replicacaoativa.service;

import java.sql.Time;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import sistemas.distribuidos.replicacaoativa.model.HeartBeat;
import sistemas.distribuidos.replicacaoativa.model.SQLCommand;
import sistemas.distribuidos.replicacaoativa.model.SQLResponse;

@Service
@RequiredArgsConstructor
public class RabbitMqMessageListener {

    private final JdbcTemplate jdbcTemplate;
    private final RabbitTemplate rabbitTemplate;
    @Value("${app.my.id}")
    private Integer instanceId;

    @RabbitListener(queues = "#{criarFila.getName}")
    public void executor(SQLCommand sql) {
        System.out.println(sql.toString() + " Sql Executado, Executor Id: " + RabbitMQMessageService.myId);
        try {
            jdbcTemplate.execute(sql.getSql());
        } catch (Exception e) {
            rabbitTemplate.convertAndSend("MembrosQueue",
                    new SQLResponse(sql.getId(), RabbitMQMessageService.myId, instanceId, false, sql.getSql()));
        }
        rabbitTemplate.convertAndSend("MembrosQueue",
                new SQLResponse(sql.getId(), RabbitMQMessageService.myId, instanceId, true, sql.getSql()));
    }

    @RabbitListener(queues = "#{heartQueue.getName}")
    public HeartBeat heart(HeartBeat beat) throws Exception {
        // System.out.println(beat.toString());
        Integer espera = new Random().nextInt(7);
        try {
            Thread.sleep(espera * 999);
        } catch (Exception e) {
            Thread.currentThread().interrupt();
        }
        return new HeartBeat(instanceId, RabbitMQMessageService.myId, "HeartBeat");
    }

}
