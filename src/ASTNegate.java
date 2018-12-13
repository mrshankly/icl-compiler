public class ASTNegate implements ASTNode {
    private ASTNode node;
    private IType type;

    public ASTNegate(ASTNode node) {
        this.node = node;
        this.type = null;
    }

    public IValue eval(Environment<IValue> env) {
        VInt value = (VInt) node.eval(env);
        return new VInt(-(value.getValue()));
    }

    public IType typecheck(Environment<IType> env) throws TypeException {
        IType t = node.typecheck(env);

        if (!(t instanceof TInt)) {
            throw new TypeException(TInt.getInstance(), t);
        }
        type = t;
        return t;
    }

    public IType getType() {
        return type;
    }

    public void compile(Environment<Integer> env) {
        node.compile(env);
        Code.getInstance().emit("ineg");
    }
}
