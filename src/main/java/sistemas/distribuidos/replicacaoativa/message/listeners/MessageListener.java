package sistemas.distribuidos.replicacaoativa.message.listeners;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import sistemas.distribuidos.replicacaoativa.model.SQLCommand;

@Component
public class MessageListener {

    @RabbitListener(queues = "Execute")
    public void executar(SQLCommand sql) {
        System.out.println(sql.toString() + " Executado");
    }

}
