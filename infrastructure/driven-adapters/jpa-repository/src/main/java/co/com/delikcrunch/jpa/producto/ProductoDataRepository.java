package co.com.delikcrunch.jpa.producto;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.QueryByExampleExecutor;

public interface ProductoDataRepository extends CrudRepository<ProductoData, String>, QueryByExampleExecutor<ProductoData> {
}
