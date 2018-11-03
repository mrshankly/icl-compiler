public class ASTWhile implements ASTNode {
    private ASTNode condition, expression;

    public ASTWhile(ASTNode condition, ASTNode expression) {
        this.condition = condition;
        this.expression = expression;
    }

    public IValue eval(Environment env) throws ArgumentsNumberMismatchException,
                                               InvalidTypeException,
                                               NameAlreadyDefinedException,
                                               NameNotDefinedException
    {
        IValue v1 = condition.eval(env);
        if (!(v1 instanceof VBool)) {
            throw new InvalidTypeException("Type mismatch");
        }

        while (((VBool) v1).getValue()) {
            expression.eval(env);

            v1 = condition.eval(env);
            if (!(v1 instanceof VBool)) {
                throw new InvalidTypeException("Type mismatch");
            }
        }
        return v1;
    }
}
