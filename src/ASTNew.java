public class ASTNew implements ASTNode {
    private ASTNode expression;

    public ASTNew(ASTNode expression) {
        this.expression = expression;
    }

    public IValue eval(Environment<IValue> env) {
        return new VCell(expression.eval(env));
    }

    public IType typecheck(Environment<IType> env) throws TypeException {
        return new TRef(expression.typecheck(env));
    }
}
