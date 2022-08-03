package co.com.delikcrunch.usecase.producto;

import co.com.delikcrunch.model.common.exception.BusinessException;
import co.com.delikcrunch.model.producto.Producto;
import co.com.delikcrunch.model.producto.gateways.ProductoRepository;
import lombok.RequiredArgsConstructor;

import java.util.Objects;

@RequiredArgsConstructor
public class GuardarProductoUseCase {

    private final ProductoRepository productoRepository;

    public Producto guardar(Producto producto) {
        Producto productoExistente = productoRepository.findById(producto.getId());

        if (Objects.nonNull(productoExistente)) throw new BusinessException("Ya existe un producto con el mismo ID", 400);

        return productoRepository.save(producto);
    }

    public Producto actualizarTodo(Producto producto) {
        Producto productoExistente = productoRepository.findById(producto.getId());

        if (Objects.isNull(productoExistente)) throw new BusinessException("El producto con ID ".concat(producto.getId()).concat(" no existe"), 404);

        if (Producto.Estado.INACTIVO.equals(productoExistente.getEstado())) throw new BusinessException("El producto con ID ".concat(producto.getId()).concat(" se encuentra INACTIVO y por tanto no puede ser actualizado"), 404);

        return productoRepository.save(
                productoExistente.toBuilder()
                .saldoTotal(producto.getSaldoTotal())
                .descripcion(producto.getDescripcion())
                .nombre(producto.getNombre())
                .build()
        );
    }

    public Producto actualizarSaldo(String id, Integer saldo) {
        Producto producto = productoRepository.findById(id);

        if (Objects.isNull(producto)) throw new BusinessException("El producto con ID ".concat(id).concat(" no existe"), 404);

        if (Producto.Estado.INACTIVO.equals(producto.getEstado())) throw new BusinessException("El producto con ID ".concat(producto.getId()).concat(" se encuentra INACTIVO y por tanto no se puede actualizar el saldo"), 404);

        if(saldo < 0) throw new BusinessException("El saldo del producto no puede ser menor a cero (0)", 400);

        producto.setSaldoTotal(saldo);

        return productoRepository.save(producto);
    }

    public void inactivar(String id) {
        Producto producto = productoRepository.findById(id);

        if (Objects.isNull(producto)) throw new BusinessException("El producto con ID ".concat(id).concat(" no existe"), 404);

        producto.setEstado(Producto.Estado.INACTIVO);

        productoRepository.save(producto);
    }

    public void activar(String id) {
        Producto producto = productoRepository.findById(id);

        if (Objects.isNull(producto)) throw new BusinessException("El producto con ID ".concat(id).concat(" no existe"), 404);

        producto.setEstado(Producto.Estado.ACTIVO);

        productoRepository.save(producto);
    }

}
