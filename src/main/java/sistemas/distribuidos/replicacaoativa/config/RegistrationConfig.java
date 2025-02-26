package sistemas.distribuidos.replicacaoativa.config;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueInformation;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

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
        if (instanceId != 0)
            return false;
        QueueInformation info = rabbitAdmin.getQueueInfo("MembrosKiwi");
        if (info == null) {
            liderançaObtida = true;
            Queue kiwi = new Queue("MembrosKiwi", false, false, true);
            rabbitAdmin.declareQueue(kiwi);
            escutarFila(kiwi);
            return true;
        }
        return false;
    }

    public void escutarFila(Queue kiwi) {
        this.container = new SimpleMessageListenerContainer(connectionFactory);
        container.setQueueNames(kiwi.getName());
        container.setMessageListener(message -> {
            System.out.println(new String(message.getBody()) + "Messagem para o lider");
        });
        container.start();
    }

}
