package sistemas.distribuidos.replicacaoativa;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class ReplicacaoativaApplication {

	public static void main(String[] args) {
		SpringApplication.run(ReplicacaoativaApplication.class, args);
	}

}
