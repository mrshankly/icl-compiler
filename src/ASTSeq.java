public class ASTSeq implements ASTNode {
    private ASTNode expression1, expression2;
    private IType type;

    public ASTSeq(ASTNode expression1, ASTNode expression2) {
        this.expression1 = expression1;
        this.expression2 = expression2;
        this.type = null;
    }

    public IValue eval(Environment<IValue> env) {
        expression1.eval(env);
        return expression2.eval(env);
    }

    public IType typecheck(Environment<IType> env) throws TypeException {
        expression1.typecheck(env);
        type = expression2.typecheck(env);
        return type;
    }

    public IType getType() {
        return type;
    }

    public void compile() {
        expression1.compile();
        Code.getInstance().emit("pop");
        expression2.compile();
    }
}
