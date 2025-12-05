package org.example.restspring.domain.errores;

public class ForbbidenException extends RuntimeException {
    public ForbbidenException(String message) {
        super(message);
    }
}
