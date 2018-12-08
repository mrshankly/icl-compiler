public class ASTWhile implements ASTNode {
    private ASTNode condition, expression;

    public ASTWhile(ASTNode condition, ASTNode expression) {
        this.condition = condition;
        this.expression = expression;
    }

    public IValue eval(Environment<IValue> env) {
        VBool cond = (VBool) condition.eval(env);

        while (cond.getValue()) {
            expression.eval(env);
            cond = (VBool) condition.eval(env);
        }
        return cond;
    }

    public IType typecheck(Environment<IType> env) throws TypeException {
        IType conditionType = condition.typecheck(env);

        if (!(conditionType instanceof TBool)) {
            throw new TypeException(TBool.getInstance(), conditionType);
        }
        expression.typecheck(env);

        return conditionType;
    }

    public void compile() {
    }
}
