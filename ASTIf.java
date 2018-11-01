public class ASTIf implements ASTNode {
    private ASTNode condition, trueExpression, falseExpression;

    public ASTIf(ASTNode condition, ASTNode trueExpression, ASTNode falseExpression) {
        this.condition = condition;
        this.trueExpression = trueExpression;
        this.falseExpression = falseExpression;
    }

    public IValue eval() throws InvalidTypeException {
        IValue v1 = condition.eval();

        if (!(v1 instanceof VBool)) {
            throw new InvalidTypeException("Type mismatch");
        }
        if(((VBool)v1).getValue())
            return trueExpression.eval();
        else
            return falseExpression.eval();
    }
}