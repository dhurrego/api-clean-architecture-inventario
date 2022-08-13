package co.com.delikcrunch.api.producto.mapper;

import co.com.delikcrunch.api.producto.dto.ProductoDTO;
import co.com.delikcrunch.model.producto.Producto;

public class ProductoMapper {

    private ProductoMapper() { }

    public static Producto toProducto(ProductoDTO productoDTO) {
        return Producto.builder()
                .id(productoDTO.getId())
                .nombre(productoDTO.getNombre())
                .descripcion(productoDTO.getDescripcion())
                .saldoTotal(productoDTO.getSaldoTotal())
                .build();
    }
}
