package co.com.delikcrunch.usecase.notification;

import co.com.delikcrunch.model.notification.EmailNotification;
import co.com.delikcrunch.model.notification.gateways.NotificationPublisher;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class SolicitarNotificacionEmailUseCase {

    private final NotificationPublisher notificationPublisher;

    public void solicitar(EmailNotification emailNotification) {
        notificationPublisher.send(emailNotification);
    }
}
