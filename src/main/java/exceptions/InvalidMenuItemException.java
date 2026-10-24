package exceptions;

// Exception for when an error occurs with a menu item
public class InvalidMenuItemException extends RuntimeException {
    public InvalidMenuItemException(String message) {
        super(message);
    }
}