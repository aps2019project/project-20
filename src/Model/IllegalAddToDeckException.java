package Model;

public class IllegalAddToDeckException extends RuntimeException{
    public IllegalAddToDeckException(String message) {
        super(message);
    }
}
