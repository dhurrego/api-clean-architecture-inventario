package co.com.delikcrunch.api.producto.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class ProductoDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotEmpty(message = "El c\u00f3digo del producto es obligatorio")
    private String id;

    @NotEmpty(message = "El nombre del producto es obligatorio")
    @Size(message = "El nombre del producto no puede superar los 150 caracteres")
    private String nombre;

    @NotEmpty(message = "La descripci\u00f3n del producto es obligatoria")
    @Size(message = "La descripci\u00f3n del producto no puede superar los 200 caracteres")
    private String descripcion;

    @NotNull(message = "El saldo total es obligatorio")
    @Min(value = 0, message = "El saldo total del producto no puede ser menor de cero (0)")
    private Integer saldoTotal;
}
