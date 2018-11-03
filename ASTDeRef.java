public class ASTDeRef implements ASTNode {
    private ASTNode expression;

    public ASTDeRef(ASTNode expression) {
        this.expression = expression;
    }

    public IValue eval(Environment env) throws ArgumentsNumberMismatchException,
                                               InvalidTypeException,
                                               NameAlreadyDefinedException,
                                               NameNotDefinedException
    {
        IValue reference = expression.eval(env);

        if (!(reference instanceof VCell)) {
            throw new InvalidTypeException(VCell.TYPE, reference.showType());
        }
        return ((VCell) reference).get();
    }
}
