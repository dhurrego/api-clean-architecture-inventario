package co.com.delikcrunch.model.notification.gateways;

import co.com.delikcrunch.model.notification.EmailNotification;

public interface ProcessEmail {
    void enviarMail(EmailNotification mail);
}
