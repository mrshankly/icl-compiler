public class ASTAnd implements ASTNode {
    private ASTNode left, right;

    public ASTAnd(ASTNode left, ASTNode right) {
        this.left = left;
        this.right = right;
    }

    public IValue eval(Environment<IValue> env) {
        VBool v1 = (VBool) left.eval(env);
        VBool v2 = (VBool) right.eval(env);

        return new VBool(v1.getValue() && v2.getValue());
    }

    public IType typecheck(Environment<IType> env) throws TypeException {
        IType t1 = left.typecheck(env);
        IType t2 = right.typecheck(env);

        if (!(t1 instanceof TBool)) {
            throw new TypeException(TBool.getInstance(), t1);
        }
        if (!(t2 instanceof TBool)) {
            throw new TypeException(TBool.getInstance(), t2);
        }
        return t1;
    }

    public void compile() {
        Code mainCode = Code.getMain();

        left.compile();
        String l1 = mainCode.getNewLabel();
        mainCode.emit("ifeq " + l1);

        right.compile();
        mainCode.emit("ifeq " + l1);

        mainCode.emit("iconst_1");
        String l2 = mainCode.getNewLabel();
        mainCode.emit("goto " + l2);

        mainCode.emit(l1 + ":");
        mainCode.emit("iconst_0");
        mainCode.emit(l2 + ":");
    }
}
