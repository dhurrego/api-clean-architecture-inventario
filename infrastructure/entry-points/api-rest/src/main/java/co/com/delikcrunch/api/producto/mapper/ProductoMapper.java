package co.com.delikcrunch.api.producto.mapper;

import co.com.delikcrunch.api.producto.dto.ProductoDTO;
import co.com.delikcrunch.model.producto.Producto;

public class ProductoMapper {

    private ProductoMapper() { }

    public static ProductoDTO toProductoDTO(Producto producto) {
        return ProductoDTO.builder()
                .id(producto.getId())
                .nombre(producto.getNombre())
                .descripcion(producto.getDescripcion())
                .saldoTotal(producto.getSaldoTotal())
                .build();
    }

    public static Producto toProducto(ProductoDTO productoDTO) {
        return Producto.builder()
                .id(productoDTO.getId())
                .nombre(productoDTO.getNombre())
                .descripcion(productoDTO.getDescripcion())
                .saldoTotal(productoDTO.getSaldoTotal())
                .estado(Producto.Estado.ACTIVO)
                .build();
    }
}
