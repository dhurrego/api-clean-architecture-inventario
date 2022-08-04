package co.com.delikcrunch.rabbitmq.publisher;

import co.com.delikcrunch.model.notification.EmailNotification;
import co.com.delikcrunch.model.notification.gateways.NotificationPublisher;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Slf4j
@EnableRabbit
@Component
@RequiredArgsConstructor
public class EmailNotificationPublisher implements NotificationPublisher {

    private final RabbitTemplate rabbitTemplate;

    @Value("${app.rabbitmq.exchange}")
    private String exchange;

    @Value("${app.rabbitmq.routing-key}")
    private String routingKey;

    @Override
    public void send(EmailNotification emailNotification) {
        rabbitTemplate.convertAndSend(exchange, routingKey, emailNotification);
        log.info("Se guarda mensaje en las colas del topicExchange {}, routingKey {}, el objecto {}", exchange, routingKey, emailNotification);
    }
}
