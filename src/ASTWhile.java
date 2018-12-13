public class ASTWhile implements ASTNode {
    private ASTNode condition, expression;
    private IType type;

    public ASTWhile(ASTNode condition, ASTNode expression) {
        this.condition = condition;
        this.expression = expression;
        this.type = null;
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

        type = conditionType;
        return conditionType;
    }

    public IType getType() {
        return type;
    }

    public void compile(Environment<Integer> env) {
        Code code = Code.getInstance();

        String l1 = code.getNewLabel();
        code.emit(l1 + ":");
        condition.compile(env);
        String l2 = code.getNewLabel();
        code.emit("ifeq " + l2);

        expression.compile(env);
        code.emit("pop");
        code.emit("goto " + l1);

        code.emit(l2 + ":");
        code.emit("iconst_0");
    }
}
