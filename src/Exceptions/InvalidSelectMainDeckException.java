package Exceptions;

public class InvalidSelectMainDeckException extends RuntimeException{
    public InvalidSelectMainDeckException(String message) {
        super(message);
    }
}
