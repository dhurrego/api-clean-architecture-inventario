package co.com.delikcrunch.rabbitmq.publisher;

import co.com.delikcrunch.model.notification.EmailNotification;
import co.com.delikcrunch.model.notification.gateways.NotificationPublisher;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@EnableRabbit
@Component
@RequiredArgsConstructor
public class EmailNotificationPublisher implements NotificationPublisher {

    private final RabbitTemplate rabbitTemplate;
    private final Queue queue;

    @Override
    public void send(EmailNotification emailNotification) {
        rabbitTemplate.convertAndSend(queue.getName(), emailNotification);
        log.info("Se guarda mensaje en la cola {}", emailNotification);
    }
}
