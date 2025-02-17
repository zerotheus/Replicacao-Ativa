package sistemas.distribuidos.replicacaoativa.config;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueInformation;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
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

    @Override
    public void run(String... args) throws Exception {
        System.out.println("Start");
        pedirLideranca();
    }

    public boolean pedirLideranca() {
        System.out.println("Pedir liderança");
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
        if (!liderançaObtida) {
            return;
        }
        System.out.println("Lider");
        this.container = new SimpleMessageListenerContainer(connectionFactory);
        container.setQueueNames(kiwi.getName());
        container.setMessageListener(message -> {
            System.out.println(new String(message.getBody()) + "Messagem para o lider");
        });
        container.start();
        return;
    }

}
