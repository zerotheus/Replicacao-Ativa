package sistemas.distribuidos.replicacaoativa.service;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
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
            rabbitTemplate.convertAndSend("MembrosKiwi",
                    new SQLResponse(sql.getId(), RabbitMQMessageService.myId, instanceId, false, sql.getSql()));
        }
        rabbitTemplate.convertAndSend("MembrosKiwi",
                new SQLResponse(sql.getId(), RabbitMQMessageService.myId, instanceId, true, sql.getSql()));
    }

    @RabbitListener(queues = "#{commandKiwi.getName}")
    public void preparar(SQLCommand sql) {
        System.out.println(sql.toString() + " Sql preparado, Executor Id: " + RabbitMQMessageService.myId);
    }

}
