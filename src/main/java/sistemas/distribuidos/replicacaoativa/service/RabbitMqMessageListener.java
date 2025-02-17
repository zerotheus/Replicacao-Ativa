package sistemas.distribuidos.replicacaoativa.service;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import sistemas.distribuidos.replicacaoativa.model.SQLCommand;

@Service
public class RabbitMqMessageListener {

    @RabbitListener(queues = "#{criarFila.getName}")
    public void executor(SQLCommand sql) {
        System.out.println(sql.toString() + " Sql Executado, Executor Id: " + RabbitMQMessageService.myId);
    }

    @RabbitListener(queues = "#{prepareKiwi.getName}")
    public void preparar(SQLCommand sql) {
        System.out.println(sql.toString() + " Sql preparado, Executor Id: " + RabbitMQMessageService.myId);
    }

}
