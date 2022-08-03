package co.com.delikcrunch.model.common.exception;

public class ApplicationException extends RuntimeException {

    private final Integer code;

    public ApplicationException(String message) {
        this(message, null);
    }

    public ApplicationException(String message, Integer code) {
        super(message);
        this.code = code;
    }

    public Integer getCode(){
        return code;
    }
}
