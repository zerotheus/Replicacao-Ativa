package sistemas.distribuidos.replicacaoativa.controller;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import sistemas.distribuidos.replicacaoativa.model.SQLCommand;

@RestController
@RequestMapping("/Sql")
@RequiredArgsConstructor
public class SQLController {

    private final RabbitTemplate rabbitTemplate;

    @PostMapping
    public ResponseEntity<SQLCommand> enviarComandoAoGrupo(@RequestBody SQLCommand sqlCommand) {
        rabbitTemplate.convertAndSend("Execute","", sqlCommand);
        return ResponseEntity.ok().body(sqlCommand);
    }

}
