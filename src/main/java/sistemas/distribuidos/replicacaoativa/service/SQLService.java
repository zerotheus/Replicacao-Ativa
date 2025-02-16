package sistemas.distribuidos.replicacaoativa.service;

import org.springframework.stereotype.Service;

import sistemas.distribuidos.replicacaoativa.model.SQLCommand;

@Service
public class SQLService {

    private Integer myID = null;

    public void executar(SQLCommand sql) {
        System.out.println(sql.toString() + " Executado");
    }

}
