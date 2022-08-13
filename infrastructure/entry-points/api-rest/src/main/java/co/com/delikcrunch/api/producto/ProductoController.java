package co.com.delikcrunch.api.producto;

import co.com.delikcrunch.api.producto.dto.ProductoDTO;
import co.com.delikcrunch.model.producto.Producto;
import co.com.delikcrunch.usecase.producto.GuardarProductoUseCase;
import co.com.delikcrunch.usecase.producto.ListarProductosUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static co.com.delikcrunch.api.producto.mapper.ProductoMapper.toProducto;

@RestController
@RequestMapping(value = "/productos", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
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
    public ResponseEntity<Producto> guardar(@Valid @RequestBody ProductoDTO producto) {
        return ResponseEntity.ok(guardarProductoUseCase.guardar(toProducto(producto)));
    }

    @PutMapping()
    public ResponseEntity<Producto> actualizarTodo(@Valid @RequestBody ProductoDTO producto) {
        return ResponseEntity.ok(guardarProductoUseCase.actualizarTodo(toProducto(producto)));
    }

    @PatchMapping("/inactivar/{id}")
    public ResponseEntity<Void> inactivar(@PathVariable("id") String id) {
        guardarProductoUseCase.inactivar(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/activar/{id}")
    public ResponseEntity<Void> activar(@PathVariable("id") String id) {
        guardarProductoUseCase.activar(id);
        return ResponseEntity.noContent().build();
    }
}
