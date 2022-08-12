package co.com.delikcrunch.usecase.producto;

import co.com.delikcrunch.model.common.exception.ProductNotFoundException;
import co.com.delikcrunch.model.producto.Producto;
import co.com.delikcrunch.model.producto.gateways.ProductoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
class ListarProductosUseCaseTest {

    public static final int HTTP_STATUS_NOT_FOUND = 404;
    public static final String CODIGO_PRODUCTO = "0001";

    @InjectMocks
    private ListarProductosUseCase listarProductosUseCase;

    @Mock
    private ProductoRepository productoRepository;

    @BeforeEach
    void setUp() {
        when(productoRepository.findAll()).thenReturn(Arrays.asList(new Producto()));
        when(productoRepository.findById(anyString())).thenReturn(new Producto());
    }

    @Test
    void listarTodos() {
        List<Producto> productos = assertDoesNotThrow(listarProductosUseCase::listarTodos);
        assertNotNull(productos);
    }

    @Test
    void listarPorId() {
        Producto producto = assertDoesNotThrow(() -> listarProductosUseCase.listarPorId(CODIGO_PRODUCTO));
        assertNotNull(producto);
    }

    @Test
    void listarPorIdProductoNoEncontrado() {
        when(productoRepository.findById(anyString())).thenReturn(null);
        ProductNotFoundException productNotFoundException = assertThrows(ProductNotFoundException.class, () -> listarProductosUseCase.listarPorId("0001"));
        assertEquals(HTTP_STATUS_NOT_FOUND, productNotFoundException.getCode());
    }
}