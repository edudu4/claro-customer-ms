package com.github.edudu4.claro_customer_ms.messaging.producer;

import com.github.edudu4.claro_customer_ms.config.RabbitMQConfig;
import com.github.edudu4.claro_customer_ms.messaging.event.ClienteCriadoEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class ClienteProducer {
    private final RabbitTemplate rabbitTemplate;

    public void enviarClienteCriado(ClienteCriadoEvent event) {
        rabbitTemplate.convertAndSend(
                RabbitMQConfig.CLIENTE_EXCHANGE,
                RabbitMQConfig.CLIENTE_ROUTING_KEY,
                event
        );
    }
}
