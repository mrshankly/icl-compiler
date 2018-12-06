public class NameAlreadyDefinedException extends RuntimeException {
    public NameAlreadyDefinedException() {
        super();
    }

    public NameAlreadyDefinedException(String message) {
        super(message);
    }
}
