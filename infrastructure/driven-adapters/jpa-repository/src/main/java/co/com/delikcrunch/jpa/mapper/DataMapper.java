package co.com.delikcrunch.jpa.mapper;

import co.com.delikcrunch.jpa.producto.ProductoData;
import co.com.delikcrunch.model.producto.Producto;
import org.reactivecommons.utils.ObjectMapper;

import java.util.Objects;
import java.util.Optional;

public class DataMapper {

    private DataMapper() {
    }

    public static Producto toEntity(ProductoData productoData) {

        if(Objects.isNull(productoData)) {
            return null;
        }

        return Producto.builder()
                .id(productoData.getId())
                .nombre(productoData.getNombre())
                .descripcion(productoData.getDescripcion())
                .saldoTotal(productoData.getSaldoTotal())
                .fechaCreacion(productoData.getFechaCreacion())
                .fechaActualizacion(productoData.getFechaActualizacion())
                .fechaBaja(Optional.ofNullable(productoData.getFechaBaja()))
                .estado(Producto.Estado.valueOf(productoData.getEstado()))
                .build();
    }

    public static ProductoData toData(Producto entity, ObjectMapper mapper) {
        ProductoData productoData = mapper.map(entity, ProductoData.class);
        entity.getFechaBaja().ifPresent(productoData::setFechaBaja);
        return productoData;
    }


}
