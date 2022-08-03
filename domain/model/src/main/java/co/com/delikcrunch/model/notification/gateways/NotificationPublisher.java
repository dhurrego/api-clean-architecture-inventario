package co.com.delikcrunch.model.notification.gateways;

import co.com.delikcrunch.model.notification.EmailNotification;

public interface NotificationPublisher {

    void send(EmailNotification emailNotification);
}
