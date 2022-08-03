package co.com.delikcrunch.usecase.producto;

import co.com.delikcrunch.model.common.exception.BusinessException;
import co.com.delikcrunch.model.producto.Producto;
import co.com.delikcrunch.model.producto.gateways.ProductoRepository;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Objects;

@RequiredArgsConstructor
public class ListarProductosUseCase {

    private final ProductoRepository productoRepository;

    public List<Producto> listarTodos() {
        return productoRepository.findAll();
    }

    public Producto listarPorId(String id) {
        Producto producto = productoRepository.findById(id);

        if (Objects.isNull(producto)) {
            throw new BusinessException("Producto no encontrado", 404);
        }

        return producto;
    }

}
