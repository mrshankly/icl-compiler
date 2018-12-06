public class TypeException extends Exception {
    public TypeException(IType expected, IType actual) {
        super(
            String.format(
                "Expected an expression of type '%s' but found and expression of type '%s' instead.",
                expected.show(),
                actual.show()
            )
        );
    }

    public TypeException() {
        super();
    }

    public TypeException(String message) {
        super(message);
    }
}
