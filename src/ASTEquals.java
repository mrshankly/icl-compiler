public class ASTEquals implements ASTNode {
    private ASTNode left, right;

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
        return TBool.getInstance();
    }

    public void compile() {
        Code mainCode = Code.getMain();

        left.compile();
        right.compile();

        String l1 = mainCode.getNewLabel();
        mainCode.emit("if_icmpeq " + l1);

        mainCode.emit("iconst_0");
        String l2 = mainCode.getNewLabel();
        mainCode.emit("goto " + l2);

        mainCode.emit(l1 + ":");
        mainCode.emit("iconst_1");

        mainCode.emit(l2 + ":");
    }
}
