package co.com.delikcrunch.jpa.producto;

import co.com.delikcrunch.jpa.helper.AdapterOperations;
import co.com.delikcrunch.model.producto.Producto;
import co.com.delikcrunch.model.producto.gateways.ProductoRepository;
import org.reactivecommons.utils.ObjectMapper;
import org.springframework.stereotype.Repository;

@Repository
public class ProductoRepositoryAdapter extends AdapterOperations<Producto, ProductoData, String, ProductoDataRepository> implements ProductoRepository
{

    public ProductoRepositoryAdapter(ProductoDataRepository repository, ObjectMapper mapper) {
        super(repository, mapper, d -> mapper.mapBuilder(d, Producto.ProductoBuilder.class).build());
    }
    
}
