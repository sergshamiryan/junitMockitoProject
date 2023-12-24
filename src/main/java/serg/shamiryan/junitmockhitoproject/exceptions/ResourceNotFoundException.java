package serg.shamiryan.junitmockhitoproject.exceptions;

public class ResourceNotFoundException extends RuntimeException {

    public ResourceNotFoundException(String message) {
        super(message);
    }

    public ResourceNotFoundException(String message, String args) {
        super(String.format(message, args));
    }

    public ResourceNotFoundException(String message, long args) {
        super(String.format(message, args));
    }

    public ResourceNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
