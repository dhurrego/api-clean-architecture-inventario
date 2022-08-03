package co.com.delikcrunch.model.producto.gateways;

import co.com.delikcrunch.model.producto.Producto;

import java.util.List;

public interface ProductoRepository {

    List<Producto> findAll();
    Producto findById(String id);
    Producto save(Producto producto);
}
