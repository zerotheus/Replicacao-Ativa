package sistemas.distribuidos.replicacaoativa.service;

import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import sistemas.distribuidos.replicacaoativa.model.HeartBeat;

@Service
@RequiredArgsConstructor
public class HeartBeatService {

    public static List<UUID> replicantesUuids = new LinkedList<>();

    private final RabbitTemplate rabbitTemplate;
    private final RabbitAdmin rabbitAdmin;

    @Scheduled(fixedRate = 7000)
    private void heartBeat() {
        replicantesUuids.forEach(kiwiUUID -> esperarBeat(kiwiUUID));
    }

    private void esperarBeat(UUID kiwiUUID) {
        System.out.println(kiwiUUID);
        rabbitTemplate.setReplyTimeout(3000);
        Object resposta = rabbitTemplate.convertSendAndReceive("heart-beat-kiwi-" + kiwiUUID,
                new HeartBeat(0, RabbitMQMessageService.myId, "Lider"));
        if (resposta == null) {
            System.out.println("delete:sql-kiwi-" + kiwiUUID);
            // rabbitAdmin.deleteQueue("sql-kiwi-" + kiwiUUID);
            return;
        }
        System.out.println(((HeartBeat) resposta).toString());
    }

}
