public class ASTWhile implements ASTNode {
    private ASTNode condition, expression;

    public ASTWhile(ASTNode condition, ASTNode expression) {
        this.condition = condition;
        this.expression = expression;
    }

    public IValue eval() throws InvalidTypeException {
        IValue v1 = condition.eval();

        if (!(v1 instanceof VBool)) {
            throw new InvalidTypeException("Type mismatch");
        }

        while (((VBool) v1).getValue()) {
            expression.eval();

            v1 = condition.eval();
            if (!(v1 instanceof VBool)) {
                throw new InvalidTypeException("Type mismatch");
            }
        }
        return v1;
    }
}
