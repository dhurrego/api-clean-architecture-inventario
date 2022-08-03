package co.com.delikcrunch.api.handler.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder(toBuilder = true)
public class ExceptionResponseDTO {

    private Integer code;
    private String mensaje;
    private LocalDateTime timestamp;
}
