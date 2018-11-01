public class ASTDeRef implements ASTNode {
    private ASTNode expression;

    public ASTDeRef(ASTNode expression) {
        this.expression = expression;
    }

    public IValue eval(Environment env) throws ArgumentsNumberMismatchException, InvalidTypeException, NameNotDefinedException, NameAlreadyDefinedException {
        IValue reference = expression.eval(env);

        if (!(reference instanceof VCell)) {
            throw new InvalidTypeException();
        }
        return ((VCell) reference).get();
    }
}
