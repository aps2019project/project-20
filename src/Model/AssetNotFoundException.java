package Model;

public class AssetNotFoundException extends RuntimeException {
    public AssetNotFoundException(String message){
        super(message);
    }
}
