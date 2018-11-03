public class ASTNew implements ASTNode {
    private ASTNode expression;

    public ASTNew(ASTNode expression) {
        this.expression = expression;
    }

    public IValue eval(Environment env) throws ArgumentsNumberMismatchException,
                                               InvalidTypeException,
                                               NameAlreadyDefinedException,
                                               NameNotDefinedException
    {
        return new VCell(expression.eval(env));
    }
}
