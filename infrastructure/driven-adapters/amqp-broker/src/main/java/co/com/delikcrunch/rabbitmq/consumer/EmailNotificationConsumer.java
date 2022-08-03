package co.com.delikcrunch.rabbitmq.consumer;

import co.com.delikcrunch.model.notification.EmailNotification;
import co.com.delikcrunch.usecase.notification.ProcesarNotificacionEmailUseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class EmailNotificationConsumer {

    private final ProcesarNotificacionEmailUseCase procesarNotificacionEmailUseCase;

    @RabbitListener(queues = {"${app.rabbitmq.queue.email.name}"})
    public void procesarNotificacion(@Payload EmailNotification notification) {
        procesarNotificacionEmailUseCase.procesar(notification);
    }
}
