package sistemas.distribuidos.replicacaoativa.service;

import java.util.UUID;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RabbitMQMessageService {

    public static final UUID myId = UUID.randomUUID();
    private final String kiwiName = "sql-executor-" + myId;

    @Bean
    public Queue criarFila() {
        return new Queue(kiwiName, false, true, true);
    }

}
