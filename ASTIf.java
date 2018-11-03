public class ASTIf implements ASTNode {
    private ASTNode condition, trueExpression, falseExpression;

    public ASTIf(ASTNode condition, ASTNode trueExpression, ASTNode falseExpression) {
        this.condition = condition;
        this.trueExpression = trueExpression;
        this.falseExpression = falseExpression;
    }

    public IValue eval(Environment env) throws ArgumentsNumberMismatchException,
                                               InvalidTypeException,
                                               NameAlreadyDefinedException,
                                               NameNotDefinedException
    {
        IValue v1 = condition.eval(env);

        if (!(v1 instanceof VBool)) {
            throw new InvalidTypeException(VBool.TYPE, v1.showType());
        }
        if (((VBool) v1).getValue()) {
            return trueExpression.eval(env);
        } else {
            return falseExpression.eval(env);
        }
    }
}
