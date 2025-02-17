package sistemas.distribuidos.replicacaoativa.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MyRabbitMQConfig {

    @Bean
    public RabbitAdmin criaRabbitAdmin(ConnectionFactory conn) {
        return new RabbitAdmin(conn);
    }

    @Bean
    public ApplicationListener<ApplicationReadyEvent> inicializaAdmin(RabbitAdmin rabbitAdmin) {
        return event -> rabbitAdmin.initialize();
    }

    @Bean
    public Jackson2JsonMessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory,
            Jackson2JsonMessageConverter messageConverter) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(messageConverter);
        return rabbitTemplate;
    }

    @Bean
    public FanoutExchange executeExchange() {
        return new FanoutExchange("Execute", false, false);
    }

    @Bean
    public Binding executeKiwiToExecuteExchange(Queue criarFila, FanoutExchange executeExchange) {
        return BindingBuilder.bind(criarFila).to(executeExchange);
    }

    @Bean
    public Binding prepareKiwiToExecuteExchange(Queue prepareKiwi, TopicExchange adicaoDeMembroESelecaoDeLider) {
        return BindingBuilder.bind(prepareKiwi).to(adicaoDeMembroESelecaoDeLider).with("prepare");
    }

    @Bean
    public TopicExchange adicaoDeMembroESelecaoDeLider() {
        return new TopicExchange("Membros", false, false);
    }

}
