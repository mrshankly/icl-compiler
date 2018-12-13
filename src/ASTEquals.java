public class ASTEquals implements ASTNode {
    private ASTNode left, right;
    private IType type;

    public ASTEquals(ASTNode left, ASTNode right) {
        this.left = left;
        this.right = right;
    }

    public IValue eval(Environment<IValue> env) {
        IValue v1 = left.eval(env);
        IValue v2 = right.eval(env);

        return new VBool(v1.equals(v2));
    }

    public IType typecheck(Environment<IType> env) throws TypeException {
        IType t1 = left.typecheck(env);
        IType t2 = right.typecheck(env);

        if (!(t1.equals(t2))) {
            throw new TypeException(t1, t2);
        }
        type = TBool.getInstance();
        return type;
    }

    public IType getType() {
        return type;
    }

    public void compile() {
        Code code = Code.getInstance();

        left.compile();
        right.compile();

        String l1 = code.getNewLabel();
        code.emit("if_icmpeq " + l1);

        code.emit("iconst_0");
        String l2 = code.getNewLabel();
        code.emit("goto " + l2);

        code.emit(l1 + ":");
        code.emit("iconst_1");

        code.emit(l2 + ":");
    }
}
