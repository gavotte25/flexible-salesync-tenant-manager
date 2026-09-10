package org.salesync.tenantmanager.configurations;

import org.springframework.amqp.core.*;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Binds a dedicated queue to the existing shared "topic-exchange" on the "auth" routing
 * key so Tenant Manager receives its own copy of every tenant-onboarding event that
 * authentication already publishes (consumed today only by type-service). Topic exchanges
 * fan a message out to every bound queue, so this requires no change to authentication or
 * type-service.
 */
@Configuration
public class RabbitMQConfig {

    @Bean
    public Queue tenantManagerQueue() {
        return new Queue("tenant-manager-queue", true, false, false);
    }

    @Bean
    public Exchange exchange() {
        return new TopicExchange("topic-exchange");
    }

    @Bean
    public Binding binding(Queue tenantManagerQueue, Exchange exchange) {
        return BindingBuilder.bind(tenantManagerQueue).to(exchange).with("auth").noargs();
    }

    @Bean
    public Jackson2JsonMessageConverter jackson2MessageConverter() {
        return new Jackson2JsonMessageConverter();
    }
}
