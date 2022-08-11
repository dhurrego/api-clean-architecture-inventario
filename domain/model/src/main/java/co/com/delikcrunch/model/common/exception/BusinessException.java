package co.com.delikcrunch.model.common.exception;

public class BusinessException extends ApplicationException{

    private static final long serialVersionUID = 1L;

    public BusinessException(String message) {
        this(message, 400);
    }

    public BusinessException(String message, Integer code) {
        super(message, code);
    }
}
