package Model;

public class IllegalRemoveFromDeckException extends RuntimeException{
    public IllegalRemoveFromDeckException(String message) {
        super(message);
    }
}
