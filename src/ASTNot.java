public class ASTNot implements ASTNode {
    private ASTNode node;
    private IType type;

    public ASTNot(ASTNode node) {
        this.node = node;
        this.type = null;
    }

    public IValue eval(Environment<IValue> env) {
        VBool value = (VBool) node.eval(env);
        return new VBool(!(value.getValue()));
    }

    public IType typecheck(Environment<IType> env) throws TypeException {
        IType t = node.typecheck(env);

        if (!(t instanceof TBool)) {
            throw new TypeException(TBool.getInstance(), t);
        }
        type = t;
        return t;
    }

    public IType getType() {
        return type;
    }

    public void compile(Environment<Integer> env) {
        Code code = Code.getInstance();

        node.compile(env);
        code.emit("iconst_1");
        code.emit("ixor");
    }
}
