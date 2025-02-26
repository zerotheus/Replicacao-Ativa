package sistemas.distribuidos.replicacaoativa.model;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HeartBeat {

    private Integer instanceId;
    private UUID queuesUuid;
    private String message;

}
