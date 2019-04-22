package Exceptions;

public class InsufficientMoneyInBuyFromShopException extends RuntimeException{
    public InsufficientMoneyInBuyFromShopException(String message) {
        super(message);
    }
}
