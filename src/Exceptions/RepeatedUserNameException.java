package Exceptions;

public class RepeatedUserNameException extends RuntimeException {
    public RepeatedUserNameException(String message){
        super(message);
    }
}
