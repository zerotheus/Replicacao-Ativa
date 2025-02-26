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
    private final String kiwiName = "sql-kiwi-" + myId;
    private final String heartKiwi = "heart-beat-kiwi-" + myId;

    @Bean
    public Queue criarFila() {
        return new Queue(kiwiName, false, true, true);
    }

    @Bean
    public Queue heartKiwi() {
        return new Queue(heartKiwi, false, true, true);
    }

}
