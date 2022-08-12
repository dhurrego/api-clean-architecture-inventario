package co.com.delikcrunch.usecase.producto;

import co.com.delikcrunch.model.common.exception.BusinessException;
import co.com.delikcrunch.model.common.exception.ProductNotFoundException;
import co.com.delikcrunch.model.producto.Producto;
import co.com.delikcrunch.model.producto.gateways.ProductoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
class GuardarProductoUseCaseTest {

    public static final int HTTP_STATUS_NOT_FOUND = 404;
    public static final String CODIGO_PRODUCTO = "0001";

    @InjectMocks
    private GuardarProductoUseCase guardarProductoUseCase;

    @Mock
    private ProductoRepository productoRepository;

    private Producto producto;
    private Producto productoBDActivoMock;
    private Producto productoBDInactivoMock;

    @BeforeEach
    void setUp() {
        producto = Producto.builder()
                .id("0001")
                .nombre("Producto test")
                .descripcion("Descripcion producto test")
                .saldoTotal(0)
                .build();

        productoBDActivoMock = producto.toBuilder()
                .estado(Producto.Estado.ACTIVO)
                .fechaCreacion(LocalDateTime.now())
                .fechaActualizacion(LocalDateTime.now())
                .fechaBaja(Optional.empty())
                .build();

        productoBDInactivoMock = producto.toBuilder()
                .estado(Producto.Estado.INACTIVO)
                .fechaCreacion(LocalDateTime.now())
                .fechaActualizacion(LocalDateTime.now())
                .fechaBaja(Optional.of(LocalDateTime.now()))
                .build();

        when(productoRepository.save(any(Producto.class))).thenReturn(productoBDActivoMock);
    }

    @Test()
    void guardarExitoso() {
        when(productoRepository.findById(anyString())).thenReturn(null);
        Producto productoGuardado = guardarProductoUseCase.guardar(producto);
        assertEquals(producto.getId(), productoGuardado.getId());
        assertEquals(producto.getSaldoTotal(), productoGuardado.getSaldoTotal());
        assertEquals(Producto.Estado.ACTIVO, productoGuardado.getEstado());
        assertNotNull(productoGuardado.getFechaActualizacion());
        assertNotNull(productoGuardado.getFechaCreacion());
    }

    @Test()
    void guardarConExcepcionPorProductoEncontrado() {
        when(productoRepository.findById(anyString())).thenReturn(producto);
        assertThrows(BusinessException.class, () -> guardarProductoUseCase.guardar(producto));
    }

    @Test()
    void inactivarExitoso() {
        when(productoRepository.findById(anyString())).thenReturn(producto);
        assertDoesNotThrow( () -> guardarProductoUseCase.inactivar(producto.getId()));
    }

    @Test()
    void inactivarConExcepcionProductoNoExiste() {
        when(productoRepository.findById(anyString())).thenReturn(null);
        ProductNotFoundException productNotFoundException = assertThrows(ProductNotFoundException.class, () -> guardarProductoUseCase.inactivar(CODIGO_PRODUCTO));
        assertEquals(HTTP_STATUS_NOT_FOUND, productNotFoundException.getCode());
    }

    @Test()
    void activarExitoso() {
        when(productoRepository.findById(anyString())).thenReturn(producto);
        assertDoesNotThrow( () -> guardarProductoUseCase.activar(producto.getId()));
    }

    @Test()
    void activarConExcepcionProductoNoExiste() {
        when(productoRepository.findById(anyString())).thenReturn(null);
        ProductNotFoundException productNotFoundException = assertThrows(ProductNotFoundException.class, () -> guardarProductoUseCase.activar(CODIGO_PRODUCTO));
        assertEquals(HTTP_STATUS_NOT_FOUND, productNotFoundException.getCode());
    }

    @Test
    void actualizarTodoExitoso() {
        when(productoRepository.findById(anyString())).thenReturn(productoBDActivoMock);
        Producto productoActualizado = guardarProductoUseCase.actualizarTodo(producto);
        assertEquals(producto.getId(), productoActualizado.getId());
        assertEquals(producto.getSaldoTotal(), productoActualizado.getSaldoTotal());
        assertEquals(Producto.Estado.ACTIVO, productoActualizado.getEstado());
        assertNotNull(productoActualizado.getFechaActualizacion());
        assertNotNull(productoActualizado.getFechaCreacion());
        assertEquals(Optional.empty(), productoActualizado.getFechaBaja());
    }

    @Test
    void actualizarTodoConExcepcionProductoInactivo() {
        when(productoRepository.findById(anyString())).thenReturn(productoBDInactivoMock);
        assertThrows(BusinessException.class, () -> guardarProductoUseCase.actualizarTodo(producto));
    }

    @Test()
    void actualizarTodoConExcepcionProductoNoExiste() {
        when(productoRepository.findById(anyString())).thenReturn(null);
        ProductNotFoundException productNotFoundException = assertThrows(ProductNotFoundException.class, () -> guardarProductoUseCase.actualizarTodo(producto));
        assertEquals(HTTP_STATUS_NOT_FOUND, productNotFoundException.getCode());
    }

    @Test
    void actualizarSaldoExitoso() {
        final Integer SALDO = 15;
        when(productoRepository.findById(anyString())).thenReturn(productoBDActivoMock);
        when(productoRepository.save(any(Producto.class))).thenReturn(productoBDActivoMock.toBuilder().saldoTotal(SALDO).build());
        Producto productoActualizado = guardarProductoUseCase.actualizarSaldo(producto.getId(), SALDO);
        assertEquals(producto.getId(), productoActualizado.getId());
        assertEquals(SALDO, productoActualizado.getSaldoTotal());
        assertEquals(Producto.Estado.ACTIVO, productoActualizado.getEstado());
        assertNotNull(productoActualizado.getFechaActualizacion());
        assertNotNull(productoActualizado.getFechaCreacion());
        assertEquals(Optional.empty(), productoActualizado.getFechaBaja());
    }

    @Test()
    void actualizarSaldoConExcepcionProductoNoExiste() {
        when(productoRepository.findById(anyString())).thenReturn(null);
        ProductNotFoundException productNotFoundException = assertThrows(ProductNotFoundException.class, () -> guardarProductoUseCase.actualizarSaldo(CODIGO_PRODUCTO, 0));
        assertEquals(HTTP_STATUS_NOT_FOUND, productNotFoundException.getCode());
    }

    @Test
    void actualizarSaldoConExcepcionProductoInactivo() {
        when(productoRepository.findById(anyString())).thenReturn(productoBDInactivoMock);
        assertThrows(BusinessException.class, () -> guardarProductoUseCase.actualizarSaldo(CODIGO_PRODUCTO, 0));
    }

    @Test
    void actualizarSaldoConExcepcionSaldoMenorACero() {
        String codigoProducto = producto.getId();
        when(productoRepository.findById(anyString())).thenReturn(productoBDActivoMock);
        assertThrows(BusinessException.class, () -> guardarProductoUseCase.actualizarSaldo(CODIGO_PRODUCTO, -1));
    }
}