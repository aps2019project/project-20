package Exceptions;

public class InsufficientManaException extends RuntimeException {
    public InsufficientManaException() {super();}
    public InsufficientManaException(String message) {
        super(message);
    }
}
