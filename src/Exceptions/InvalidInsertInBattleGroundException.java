package Exceptions;

public class InvalidInsertInBattleGroundException extends RuntimeException {
    public InvalidInsertInBattleGroundException(){}
    public InvalidInsertInBattleGroundException(String message){
        super(message);
    }
}
