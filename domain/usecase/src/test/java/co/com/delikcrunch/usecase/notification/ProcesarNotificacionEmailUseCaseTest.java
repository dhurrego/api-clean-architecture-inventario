package co.com.delikcrunch.usecase.notification;

import co.com.delikcrunch.model.notification.EmailNotification;
import co.com.delikcrunch.model.notification.gateways.ProcessEmail;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

@ExtendWith(SpringExtension.class)
class ProcesarNotificacionEmailUseCaseTest {

    @InjectMocks
    private ProcesarNotificacionEmailUseCase procesarNotificacionEmailUseCase;

    @Mock
    private ProcessEmail processEmail;

    @Test
    void procesar() {
        EmailNotification emailNotification = EmailNotification.builder()
                .contenido("CONTENIDO TEST")
                .asunto("ASUNTO TEST")
                .build();
        assertDoesNotThrow(() -> procesarNotificacionEmailUseCase.procesar(emailNotification));
    }

}