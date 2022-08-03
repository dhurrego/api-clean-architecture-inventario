package co.com.delikcrunch.usecase.producto;

import co.com.delikcrunch.model.common.exception.BusinessException;
import co.com.delikcrunch.model.notification.EmailNotification;
import co.com.delikcrunch.model.producto.Producto;
import co.com.delikcrunch.model.producto.gateways.ProductoRepository;
import co.com.delikcrunch.usecase.notification.SolicitarNotificacionEmailUseCase;
import lombok.RequiredArgsConstructor;

import java.util.Objects;

@RequiredArgsConstructor
public class GuardarProductoUseCase {

    private final ProductoRepository productoRepository;

    private final SolicitarNotificacionEmailUseCase solicitarNotificacionEmailUseCase;

    public Producto guardar(Producto producto) {
        Producto productoExistente = productoRepository.findById(producto.getId());

        if (Objects.nonNull(productoExistente)) throw new BusinessException("Ya existe un producto con el mismo ID", 400);

        solicitarNotificacionEmailUseCase.solicitar(EmailNotification.builder()
                .contenido("Producto guardado ".concat(producto.getId()))
                .asunto("Se ha guarado el producto")
                .build());

        return productoRepository.save(producto);
    }

    public Producto actualizarTodo(Producto producto) {
        Producto productoExistente = productoRepository.findById(producto.getId());

        if (Objects.isNull(productoExistente)) throw new BusinessException("El producto con ID ".concat(producto.getId()).concat(" no existe"), 404);

        return productoRepository.save(producto);
    }

    public Producto actualizarSaldo(String id, Integer saldo) {
        Producto producto = productoRepository.findById(id);

        if (Objects.isNull(producto)) throw new BusinessException("El producto con ID ".concat(id).concat(" no existe"), 404);

        if(saldo < 0) throw new BusinessException("El saldo del producto no puede ser menor a cero (0)", 400);

        producto.setSaldoTotal(saldo);

        return productoRepository.save(producto);
    }

}
