package co.com.delikcrunch.usecase.producto;

import co.com.delikcrunch.model.common.exception.BusinessException;
import co.com.delikcrunch.model.common.exception.ProductNotFoundException;
import co.com.delikcrunch.model.producto.Producto;
import co.com.delikcrunch.model.producto.gateways.ProductoRepository;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;

@RequiredArgsConstructor
public class GuardarProductoUseCase {

    private final ProductoRepository productoRepository;

    public Producto guardar(Producto producto) {
        Producto productoExistente = productoRepository.findById(producto.getId());

        if (Objects.nonNull(productoExistente))
            throw new BusinessException("Ya existe un producto con el mismo ID", 400);

        return productoRepository.save(
                producto.toBuilder()
                        .estado(Producto.Estado.ACTIVO)
                        .fechaCreacion(LocalDateTime.now())
                        .fechaActualizacion(LocalDateTime.now())
                        .fechaBaja(Optional.empty())
                        .build()
        );
    }

    public Producto actualizarTodo(Producto producto) {
        Producto productoExistente = productoRepository.findById(producto.getId());

        if (Objects.isNull(productoExistente))
            throw new ProductNotFoundException(producto.getId());

        if (Producto.Estado.INACTIVO.equals(productoExistente.getEstado()))
            throw new BusinessException("El producto con ID ".concat(producto.getId()).concat(" se encuentra INACTIVO y por tanto no puede ser actualizado"), 404);

        return productoRepository.save(
                productoExistente.toBuilder()
                        .saldoTotal(producto.getSaldoTotal())
                        .descripcion(producto.getDescripcion())
                        .nombre(producto.getNombre())
                        .fechaActualizacion(LocalDateTime.now())
                        .build()
        );
    }

    public Producto actualizarSaldo(String id, Integer saldo) {
        Producto producto = productoRepository.findById(id);

        if (Objects.isNull(producto))
            throw new ProductNotFoundException(id);

        if (Producto.Estado.INACTIVO.equals(producto.getEstado()))
            throw new BusinessException("El producto con ID ".concat(producto.getId()).concat(" se encuentra INACTIVO y por tanto no se puede actualizar el saldo"), 404);

        if (saldo < 0) throw new BusinessException("El saldo del producto no puede ser menor a cero (0)", 400);

        return productoRepository.save(
                producto.toBuilder()
                        .saldoTotal(saldo)
                        .fechaActualizacion(LocalDateTime.now())
                        .build()
        );
    }

    public void inactivar(String id) {
        Producto producto = productoRepository.findById(id);

        if (Objects.isNull(producto))
            throw new ProductNotFoundException(id);

        productoRepository.save(
                producto.toBuilder()
                        .estado(Producto.Estado.INACTIVO)
                        .fechaActualizacion(LocalDateTime.now())
                        .fechaBaja(Optional.of(LocalDateTime.now()))
                        .build()
        );
    }

    public void activar(String id) {
        Producto producto = productoRepository.findById(id);

        if (Objects.isNull(producto))
            throw new ProductNotFoundException(id);

        productoRepository.save(
                producto.toBuilder()
                        .estado(Producto.Estado.ACTIVO)
                        .fechaActualizacion(LocalDateTime.now())
                        .fechaBaja(Optional.empty())
                        .build()
        );
    }

}
