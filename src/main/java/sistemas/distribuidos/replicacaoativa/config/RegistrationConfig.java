package sistemas.distribuidos.replicacaoativa.config;

import java.util.UUID;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueInformation;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import sistemas.distribuidos.replicacaoativa.service.HeartBeatService;
import sistemas.distribuidos.replicacaoativa.service.RabbitMQMessageService;

@RequiredArgsConstructor
@Component
public class RegistrationConfig implements CommandLineRunner {

    public static boolean liderançaObtida = false;
    private final RabbitAdmin rabbitAdmin;
    private final ConnectionFactory connectionFactory;
    private SimpleMessageListenerContainer container;
    @Value("${app.my.id}")
    private Integer instanceId;

    @Override
    public void run(String... args) throws Exception {
        pedirLideranca();
    }

    public boolean pedirLideranca() {
        System.out.println("Pedir liderança");
        QueueInformation info = rabbitAdmin.getQueueInfo("MembrosQueue");
        Queue queue = new Queue("MembrosQueue", true, false, false);
        if (info == null) {
            rabbitAdmin.declareQueue(queue);
        }
        rabbitAdmin.getRabbitTemplate().convertAndSend("MembrosQueue", "MyUuid:" + RabbitMQMessageService.myId);
        if (instanceId != 0)
            return false;
        liderançaObtida = true;
        escutarFila(queue);
        return true;
    }

    public void escutarFila(Queue queue) {
        this.container = new SimpleMessageListenerContainer(connectionFactory);
        container.setQueueNames(queue.getName());
        container.setMessageListener(message -> {
            String mensagem = new String(message.getBody());
            System.out.println(mensagem + "Messagem para o lider");
            if (mensagem.contains("MyUuid")) {
                String[] mensagemDivida = mensagem.split(":");
                HeartBeatService.replicantesUuids.add(UUID.fromString(mensagemDivida[1]));
            }
        });
        container.start();
    }

}
