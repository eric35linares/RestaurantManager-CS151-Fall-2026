package exceptions;

// Exception for when a table is unavailable
public class TableUnavailableException extends Exception {
 
  public TableUnavailableException(String message) {
    super(message);
  }
}
