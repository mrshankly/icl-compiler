public class ASTIf implements ASTNode {
    private ASTNode condition, trueExpression, falseExpression;
    private IType type;

    public ASTIf(ASTNode condition, ASTNode trueExpression, ASTNode falseExpression) {
        this.condition = condition;
        this.trueExpression = trueExpression;
        this.falseExpression = falseExpression;
        this.type = null;
    }

    public IValue eval(Environment<IValue> env) {
        VBool cond = (VBool) condition.eval(env);

        if (cond.getValue()) {
            return trueExpression.eval(env);
        } else {
            return falseExpression.eval(env);
        }
    }

    public IType typecheck(Environment<IType> env) throws TypeException {
        IType conditionType = condition.typecheck(env);

        if (!(conditionType instanceof TBool)) {
            throw new TypeException(TBool.getInstance(), conditionType);
        }

        IType trueType = trueExpression.typecheck(env);
        IType falseType = falseExpression.typecheck(env);

        if (!(trueType.equals(falseType))) {
            throw new TypeException(trueType, falseType);
        }
        type = trueType;
        return trueType;
    }

    public IType getType() {
        return type;
    }

    public void compile(Environment<Integer> env) {
        Code code = Code.getInstance();

        condition.compile(env);
        String l1 = code.getNewLabel();
        code.emit("ifeq " + l1);

        trueExpression.compile(env);
        String l2 = code.getNewLabel();
        code.emit("goto " + l2);

        code.emit(l1 + ":");
        falseExpression.compile(env);

        code.emit(l2 + ":");
    }
}
