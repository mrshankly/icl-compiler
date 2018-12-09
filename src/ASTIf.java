public class ASTIf implements ASTNode {
    private ASTNode condition, trueExpression, falseExpression;

    public ASTIf(ASTNode condition, ASTNode trueExpression, ASTNode falseExpression) {
        this.condition = condition;
        this.trueExpression = trueExpression;
        this.falseExpression = falseExpression;
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
        return trueType;
    }

    public void compile() {
        Code mainCode = Code.getMain();

        condition.compile();
        String l1 = mainCode.getNewLabel();
        mainCode.emit("ifeq " + l1);

        trueExpression.compile();
        String l2 = mainCode.getNewLabel();
        mainCode.emit("goto " + l2);

        mainCode.emit(l1 + ":");
        falseExpression.compile();

        mainCode.emit(l2 + ":");
    }
}
