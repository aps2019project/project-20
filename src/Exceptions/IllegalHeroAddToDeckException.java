package Exceptions;

public class IllegalHeroAddToDeckException extends RuntimeException{
    public IllegalHeroAddToDeckException(String message) {
        super(message);
    }
}
