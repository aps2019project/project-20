package Exceptions;

public class NoAvailableBufferForCardException extends RuntimeException {
    public NoAvailableBufferForCardException(){};
    public NoAvailableBufferForCardException(String message){
        super(message);
    }
}
