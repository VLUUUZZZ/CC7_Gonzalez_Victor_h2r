package mx.edu.utez.H2R_Victor_Gonzalez_CC7.exception;

import org.springframework.http.HttpStatus;

public class CustomException extends RuntimeException {
    private final HttpStatus status;

    public CustomException(String mensaje) {
        super(mensaje);
        this.status = HttpStatus.BAD_REQUEST;
    }

    public CustomException(String mensaje, HttpStatus status) {
        super(mensaje);
        this.status = status;
    }

    public HttpStatus getStatus() {
        return status;
    }
}
