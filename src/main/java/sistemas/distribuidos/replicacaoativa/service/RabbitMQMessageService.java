package sistemas.distribuidos.replicacaoativa.service;

import java.util.LinkedList;
import java.util.UUID;

import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RabbitMQMessageService {

    public static final UUID myId = UUID.randomUUID();
    @Value("${app.my.id}")
    private Integer instanceId;
    private final String queueName = "sql-queue-" + myId;
    private final String heartQueue = "heart-beat-queue-" + myId;

    @Bean
    public Queue criarFila() {
        return new Queue(queueName, false, true, true);
    }

    @Bean
    public Queue heartQueue() {
        return new Queue(heartQueue, false, true, true);
    }

}
