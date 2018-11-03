public class ArgumentsNumberMismatchException extends Exception {
    public ArgumentsNumberMismatchException(int expected, int actual) {
        super("Error: Function with arity of " + expected + " applied to " + actual + " arguments.");
    }

    public ArgumentsNumberMismatchException() {
        super();
    }

    public ArgumentsNumberMismatchException(String message) {
        super(message);
    }
}
