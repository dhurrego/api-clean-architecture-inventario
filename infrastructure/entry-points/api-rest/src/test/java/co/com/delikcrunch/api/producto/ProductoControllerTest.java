package co.com.delikcrunch.api.producto;

import co.com.delikcrunch.api.producto.dto.ProductoDTO;
import co.com.delikcrunch.model.common.exception.BusinessException;
import co.com.delikcrunch.model.common.exception.ProductNotFoundException;
import co.com.delikcrunch.model.producto.Producto;
import co.com.delikcrunch.usecase.producto.GuardarProductoUseCase;
import co.com.delikcrunch.usecase.producto.ListarProductosUseCase;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = ProductoController.class)
class ProductoControllerTest {

    public static final String CODIGO_PRODUCTO = "0001";
    public static final String MENSAJE_ERROR_PRODUCTO_FOUND = "El producto ya existe";

    @MockBean
    private ListarProductosUseCase listarProductosUseCase;

    @MockBean
    private GuardarProductoUseCase guardarProductoUseCase;

    @Autowired
    private MockMvc mockMvc;

    private Producto productoResponse;
    private ProductoDTO productoDTORequestValid;
    private ProductoDTO productoDTORequestInvalid;

    @BeforeEach
    void setUp() {
        productoDTORequestValid = ProductoDTO.builder()
                .id(CODIGO_PRODUCTO)
                .nombre("PRODUCTO TEST")
                .descripcion("DESCRIPCION TEST")
                .saldoTotal(189)
                .build();

        productoDTORequestInvalid = ProductoDTO.builder().build();

        productoResponse = Producto.builder()
                .id(CODIGO_PRODUCTO)
                .nombre("PRODUCTO TEST")
                .descripcion("DESCRIPCION TEST")
                .saldoTotal(180)
                .estado(Producto.Estado.ACTIVO)
                .fechaCreacion(LocalDateTime.now())
                .fechaActualizacion(LocalDateTime.now())
                .fechaBaja(Optional.empty())
                .build();


        when(listarProductosUseCase.listarTodos()).thenReturn(Collections.emptyList());
    }

    @Test
    void listarTodosOK() throws Exception {
        mockMvc.perform(
                get("/productos")
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE));
    }

    @Test
    void listarPorIdOK() throws Exception {
        when(listarProductosUseCase.listarPorId(anyString())).thenReturn(productoResponse);
        mockMvc.perform(
                get("/productos/".concat(CODIGO_PRODUCTO))
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.nombre").exists())
                .andExpect(jsonPath("$.descripcion").exists())
                .andExpect(jsonPath("$.saldoTotal").exists());
    }

    @Test
    void listarPorIdNotFound() throws Exception {
        when(listarProductosUseCase.listarPorId(anyString())).thenThrow(new ProductNotFoundException(CODIGO_PRODUCTO));
        mockMvc.perform(
                get("/productos/{id}",CODIGO_PRODUCTO)
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.mensaje").exists())
                .andExpect(jsonPath("$.code").exists())
                .andExpect(jsonPath("$.timestamp").exists());
    }

    @Test
    void guardarOK() throws Exception {
        when(guardarProductoUseCase.guardar(any(Producto.class))).thenReturn(productoResponse);
        mockMvc.perform(
                post("/productos")
                        .content(asJsonString(productoDTORequestValid))
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.nombre").exists())
                .andExpect(jsonPath("$.descripcion").exists())
                .andExpect(jsonPath("$.saldoTotal").exists());
    }

    @Test
    void guardarBussinessException() throws Exception {
        when(guardarProductoUseCase.guardar(any(Producto.class))).thenThrow(new BusinessException(MENSAJE_ERROR_PRODUCTO_FOUND, HttpStatus.BAD_REQUEST.value()));
        mockMvc.perform(
                post("/productos")
                        .content(asJsonString(productoDTORequestValid))
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.mensaje").exists())
                .andExpect(jsonPath("$.code").exists())
                .andExpect(jsonPath("$.timestamp").exists());
    }

    @Test
    void guardarRequestInvalid() throws Exception {
        mockMvc.perform(
                post("/productos")
                        .content(asJsonString(productoDTORequestInvalid))
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.mensaje").exists())
                .andExpect(jsonPath("$.code").exists())
                .andExpect(jsonPath("$.timestamp").exists());
    }

    @Test
    void actualizarTodoOK() throws Exception {
        when(guardarProductoUseCase.actualizarTodo(any(Producto.class))).thenReturn(productoResponse);
        mockMvc.perform(
                put("/productos")
                        .content(asJsonString(productoDTORequestValid))
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.nombre").exists())
                .andExpect(jsonPath("$.descripcion").exists())
                .andExpect(jsonPath("$.saldoTotal").exists());
    }

    @Test
    void actualizarTodoProductoNotFound() throws Exception {
        when(guardarProductoUseCase.actualizarTodo(any(Producto.class))).thenThrow(new ProductNotFoundException(CODIGO_PRODUCTO));

        mockMvc.perform(
                put("/productos")
                        .content(asJsonString(productoDTORequestValid))
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.mensaje").exists())
                .andExpect(jsonPath("$.code").exists())
                .andExpect(jsonPath("$.timestamp").exists());
    }

    @Test
    void actualizarTodoRequestInvalid() throws Exception {
        mockMvc.perform(
                put("/productos")
                        .content(asJsonString(productoDTORequestInvalid))
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.mensaje").exists())
                .andExpect(jsonPath("$.code").exists())
                .andExpect(jsonPath("$.timestamp").exists());
    }

    @Test
    void inactivarOK() throws Exception {
        doNothing().when(guardarProductoUseCase).inactivar(anyString());

        mockMvc.perform(
                patch("/productos/inactivar/{id}", CODIGO_PRODUCTO)
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andDo(print())
                .andExpect(status().isNoContent());
    }

    @Test
    void activarOK() throws Exception {
        doNothing().when(guardarProductoUseCase).activar(anyString());
        mockMvc.perform(
                patch("/productos/activar/{id}", CODIGO_PRODUCTO)
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andDo(print())
                .andExpect(status().isNoContent());
    }

    @Test
    void endpointNoSoportado() throws Exception {
        mockMvc.perform(
                put("/productos/inactivar/{id}", CODIGO_PRODUCTO)
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andDo(print())
                .andExpect(status().isMethodNotAllowed())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.mensaje").exists())
                .andExpect(jsonPath("$.code").exists())
                .andExpect(jsonPath("$.timestamp").exists());
    }

    @Test
    void errorNoControlado() throws Exception {
        when(guardarProductoUseCase.actualizarTodo(any(Producto.class))).thenThrow(new RuntimeException("Error inesperado"));
        mockMvc.perform(
                put("/productos")
                        .content(asJsonString(productoDTORequestValid))
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andDo(print())
                .andExpect(status().isInternalServerError())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.mensaje").exists())
                .andExpect(jsonPath("$.code").exists())
                .andExpect(jsonPath("$.timestamp").exists());
    }

    private static String asJsonString(final Object obj) throws Exception{
        return new ObjectMapper().writeValueAsString(obj);
    }
}