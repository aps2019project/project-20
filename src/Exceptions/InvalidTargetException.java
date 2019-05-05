package Exceptions;

public class InvalidTargetException extends RuntimeException {
    public InvalidTargetException(){}
    public InvalidTargetException(String messsage){
        super(messsage);
    }
}
