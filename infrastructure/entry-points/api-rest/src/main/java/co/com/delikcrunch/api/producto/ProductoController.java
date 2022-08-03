package co.com.delikcrunch.api.producto;

import co.com.delikcrunch.api.producto.dto.ProductoDTO;
import co.com.delikcrunch.model.producto.Producto;
import co.com.delikcrunch.usecase.notification.SolicitarNotificacionEmailUseCase;
import co.com.delikcrunch.usecase.producto.GuardarProductoUseCase;
import co.com.delikcrunch.usecase.producto.ListarProductosUseCase;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static co.com.delikcrunch.api.producto.mapper.ProductoMapper.toProducto;
import static co.com.delikcrunch.api.producto.mapper.ProductoMapper.toProductoDTO;

@RestController
@RequestMapping(value = "/productos", produces = MediaType.APPLICATION_JSON_VALUE)
@AllArgsConstructor
public class ProductoController {

    private final ListarProductosUseCase listarProductosUseCase;
    private final GuardarProductoUseCase guardarProductoUseCase;

    @GetMapping()
    public ResponseEntity<List<Producto>> listarTodos() {
        return ResponseEntity.ok(listarProductosUseCase.listarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Producto> listarPorId(@PathVariable("id") String id) {
        return ResponseEntity.ok(listarProductosUseCase.listarPorId(id));
    }

    @PostMapping()
    public ResponseEntity<ProductoDTO> guardar(@Valid @RequestBody ProductoDTO producto) {
        ProductoDTO productoDTO = toProductoDTO(guardarProductoUseCase.guardar(toProducto(producto)));
        return ResponseEntity.ok(productoDTO);
    }
}
