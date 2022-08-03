package co.com.delikcrunch.model.notification;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class EmailNotification implements Serializable {

    private static final long serialVersionUID = 1L;

    private String asunto;
    private String contenido;
}
