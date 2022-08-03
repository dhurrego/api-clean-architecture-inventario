package co.com.delikcrunch.usecase.notification;

import co.com.delikcrunch.model.notification.EmailNotification;
import co.com.delikcrunch.model.notification.gateways.ProcessEmail;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ProcesarNotificacionEmailUseCase {

    private final ProcessEmail processEmail;

    public void procesar(EmailNotification emailNotification) {
        processEmail.enviarMail(emailNotification);
    }
}
