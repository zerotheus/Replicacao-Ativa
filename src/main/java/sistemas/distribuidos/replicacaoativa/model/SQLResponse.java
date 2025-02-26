package sistemas.distribuidos.replicacaoativa.model;

import java.util.UUID;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SQLResponse {

    private UUID uuid;
    private UUID queueUUID;
    private Integer instanceId;
    private boolean status;
    private String sql;

    public SQLResponse(UUID uuid, UUID queueUUID, Integer instanceId, boolean status, String sql) {
        this.uuid = uuid;
        this.queueUUID = queueUUID;
        this.instanceId = instanceId;
        this.status = status;
        this.sql = sql;
    }

}
