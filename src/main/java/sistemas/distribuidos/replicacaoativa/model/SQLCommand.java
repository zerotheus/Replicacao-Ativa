package sistemas.distribuidos.replicacaoativa.model;

import java.util.UUID;

import lombok.Data;

@Data
public class SQLCommand {
    private UUID id = UUID.randomUUID();
    private String sql;
}
