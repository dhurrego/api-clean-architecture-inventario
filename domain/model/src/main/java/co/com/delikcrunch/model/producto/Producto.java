package co.com.delikcrunch.model.producto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Optional;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class Producto implements Serializable {

    private static final long serialVersionUID = 2405172041950251807L;

    public enum Estado {
        ACTIVO, INACTIVO
    }

    private String id;
    private String nombre;
    private String descripcion;
    private Integer saldoTotal;
    private Estado estado;
    private LocalDateTime fechaCreacion;
    private LocalDateTime fechaActualizacion;
    private transient Optional<LocalDateTime> fechaBaja;
}
