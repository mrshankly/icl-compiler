public class ASTNegate implements ASTNode {
    private ASTNode node;

    public ASTNegate(ASTNode node) {
        this.node = node;
    }

    public IValue eval(Environment<IValue> env) {
        VInt value = (VInt) node.eval(env);
        return new VInt(-(value.getValue()));
    }

    public IType typecheck(Environment<IType> env) throws TypeException {
        IType type = node.typecheck(env);

        if (!(type instanceof TInt)) {
            throw new TypeException(TInt.getInstance(), type);
        }
        return type;
    }

    public void compile() {
        node.compile();
        Code.getInstance().emit("ineg");
    }
}
