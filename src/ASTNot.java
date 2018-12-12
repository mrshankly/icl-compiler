public class ASTNot implements ASTNode {
    private ASTNode node;

    public ASTNot(ASTNode node) {
        this.node = node;
    }

    public IValue eval(Environment<IValue> env) {
        VBool value = (VBool) node.eval(env);
        return new VBool(!(value.getValue()));
    }

    public IType typecheck(Environment<IType> env) throws TypeException {
        IType type = node.typecheck(env);

        if (!(type instanceof TBool)) {
            throw new TypeException(TBool.getInstance(), type);
        }
        return type;
    }

    public void compile() {
        Code code = Code.getInstance();

        node.compile();
        code.emit("iconst_1");
        code.emit("ixor");
    }
}
