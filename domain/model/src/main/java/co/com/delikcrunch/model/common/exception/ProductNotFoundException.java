package co.com.delikcrunch.model.common.exception;

public class ProductNotFoundException extends ApplicationException {

    private static final long serialVersionUID = 1L;

    public ProductNotFoundException(String codigoProducto) {
        this(obtenerMensajeError(codigoProducto), 404);
    }

    private ProductNotFoundException(String message, Integer code) {
        super(message, code);
    }

    private static String obtenerMensajeError(String codigoProducto) {
        return "El producto con ID ".concat(codigoProducto).concat(" no existe");
    }
}
