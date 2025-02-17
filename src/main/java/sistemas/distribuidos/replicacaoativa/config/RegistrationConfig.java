package sistemas.distribuidos.replicacaoativa.config;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueInformation;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class RegistrationConfig implements CommandLineRunner {

    public static boolean liderançaObtida = false;
    private final RabbitAdmin rabbitAdmin;

    @Override
    public void run(String... args) throws Exception {
        System.out.println("Start");
        liderançaObtida = pedirLideranca();
    }

    public boolean pedirLideranca() {
        System.out.println("Pedir liderança");
        QueueInformation info = rabbitAdmin.getQueueInfo("MembrosKiwi");
        if (info == null) {
            rabbitAdmin.declareQueue(new Queue("MembrosKiwi", false, false, false));
            return true;
        }
        return false;
    }

}
