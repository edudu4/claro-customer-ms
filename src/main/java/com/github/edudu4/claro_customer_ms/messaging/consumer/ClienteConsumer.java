package com.github.edudu4.claro_customer_ms.messaging.consumer;

import com.github.edudu4.claro_customer_ms.config.RabbitMQConfig;
import lombok.extern.log4j.Log4j2;
import com.github.edudu4.claro_customer_ms.messaging.event.ClienteCriadoEvent;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@Log4j2
public class ClienteConsumer {
    @RabbitListener(queues = RabbitMQConfig.CLIENTE_QUEUE)
    public void consumirClienteCriado(ClienteCriadoEvent event) {
        log.info("Mensagem recebida da fila:");
        log.info("Evento: {}", event.toString());
    }
}
