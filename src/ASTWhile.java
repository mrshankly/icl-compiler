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
        Code mainCode = Code.getMain();

        String l1 = mainCode.getNewLabel();
        mainCode.emit(l1 + ":");
        condition.compile();
        String l2 = mainCode.getNewLabel();
        mainCode.emit("ifeq " + l2);

        expression.compile();
        mainCode.emit("pop");
        mainCode.emit("goto " + l1);

        mainCode.emit(l2 + ":");
        mainCode.emit("iconst_0");
    }
}
