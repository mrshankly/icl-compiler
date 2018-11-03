public class InvalidTypeException extends Exception {
    public InvalidTypeException(String expected, String actual) {
        super(
            "Error: Expected an expression of type '" + expected +
            "' but found an expression of type '" + actual + "' instead."
        );
    }

    public InvalidTypeException() {
        super();
    }

    public InvalidTypeException(String message) {
        super(message);
    }
}
