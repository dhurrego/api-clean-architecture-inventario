package co.com.delikcrunch.api.handler;

import co.com.delikcrunch.api.handler.dto.ExceptionResponseDTO;
import co.com.delikcrunch.model.common.exception.BusinessException;
import co.com.delikcrunch.model.common.exception.ProductNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.support.WebExchangeBindException;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;

@Slf4j
@ControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ExceptionResponseDTO> manejarBusinessException(BusinessException exception, ServletWebRequest request){
        registrarLog(request.getRequest().getMethod(), request.getRequest().getRequestURI(), exception);
        return ResponseEntity
                .status(HttpStatus.valueOf(exception.getCode()))
                .contentType(MediaType.APPLICATION_JSON)
                .body(ExceptionResponseDTO.builder()
                        .mensaje(exception.getMessage())
                        .code(exception.getCode())
                        .timestamp(LocalDateTime.now())
                        .build()
                );
    }

    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<ExceptionResponseDTO> manejarProductNotFoundException(ProductNotFoundException exception, ServletWebRequest request){
        registrarLog(request.getRequest().getMethod(), request.getRequest().getRequestURI(), exception);
        return ResponseEntity
                .status(HttpStatus.valueOf(exception.getCode()))
                .contentType(MediaType.APPLICATION_JSON)
                .body(ExceptionResponseDTO.builder()
                        .mensaje(exception.getMessage())
                        .code(exception.getCode())
                        .timestamp(LocalDateTime.now())
                        .build()
                );
    }

    @ExceptionHandler(WebExchangeBindException.class)
    public ResponseEntity<ExceptionResponseDTO> manejarWebExchangeBindException(WebExchangeBindException exception, ServletWebRequest request){
        String mensaje = exception.getBindingResult()
                .getAllErrors()
                .get(0)
                .getDefaultMessage();
        registrarLog(request.getRequest().getMethod(), request.getRequest().getRequestURI(), exception);
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .contentType(MediaType.APPLICATION_JSON)
                .body(ExceptionResponseDTO.builder()
                        .mensaje(mensaje)
                        .code(HttpStatus.BAD_REQUEST.value())
                        .timestamp(LocalDateTime.now())
                        .build()
                );
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ExceptionResponseDTO> manejarExcepcionGeneral(Exception exception, ServletWebRequest request){
        registrarLog(request.getRequest().getMethod(), request.getRequest().getRequestURI(), exception);
        exception.printStackTrace();
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .contentType(MediaType.APPLICATION_JSON)
                .body(ExceptionResponseDTO.builder()
                        .mensaje("Se presento un interno")
                        .code(HttpStatus.INTERNAL_SERVER_ERROR.value())
                        .timestamp(LocalDateTime.now())
                        .build()
                );
    }

    @Override
    protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException exception, HttpHeaders headers, HttpStatus status, WebRequest request) {
        registrarLog(((ServletWebRequest)request).getRequest().getMethod(), ((ServletWebRequest)request).getRequest().getRequestURI(), exception);
        return ResponseEntity
                .status(status)
                .contentType(MediaType.APPLICATION_JSON)
                .body(ExceptionResponseDTO.builder()
                        .mensaje("El endpoint que esta intentando consumir no esta soportado")
                        .code(status.value())
                        .timestamp(LocalDateTime.now())
                        .build()
                );
    }

    @Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception exception, Object body, HttpHeaders headers, HttpStatus status, WebRequest request) {
        registrarLog(((ServletWebRequest)request).getRequest().getMethod(), ((ServletWebRequest)request).getRequest().getRequestURI(), exception);
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .contentType(MediaType.APPLICATION_JSON)
                .body(ExceptionResponseDTO.builder()
                        .mensaje("Se presento un interno")
                        .code(HttpStatus.INTERNAL_SERVER_ERROR.value())
                        .timestamp(LocalDateTime.now())
                        .build()
                );
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException exception, HttpHeaders headers, HttpStatus status, WebRequest request) {
        String mensaje = exception.getBindingResult()
                .getAllErrors()
                .get(0)
                .getDefaultMessage();
        registrarLog(((ServletWebRequest)request).getRequest().getMethod(), ((ServletWebRequest)request).getRequest().getRequestURI(), exception);
        return ResponseEntity
                .status(status)
                .contentType(MediaType.APPLICATION_JSON)
                .body(ExceptionResponseDTO.builder()
                        .mensaje(mensaje)
                        .code(status.value())
                        .timestamp(LocalDateTime.now())
                        .build()
                );
    }

    private void registrarLog(String metodoHttp, String uri, Exception ex) {
        log.error("Se produjo un error al consumir {} {}, Exception: {}", metodoHttp, uri, ex);
    }

}
