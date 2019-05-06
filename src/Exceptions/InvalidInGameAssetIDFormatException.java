package Exceptions;

public class InvalidInGameAssetIDFormatException extends RuntimeException {
    public InvalidInGameAssetIDFormatException(String message) {
        super(message);
    }
}
