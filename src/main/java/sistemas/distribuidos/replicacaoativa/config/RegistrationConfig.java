package sistemas.distribuidos.replicacaoativa.config;

import org.springframework.boot.CommandLineRunner;

public class RegistrationConfig implements CommandLineRunner {

    @Override
    public void run(String... args) throws Exception {
        System.out.println("Start");
    }

    public boolean euSouOLider() {
        return false;
    }

}
