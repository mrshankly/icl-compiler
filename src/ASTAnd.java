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
        Code code = Code.getInstance();

        left.compile();
        String l1 = code.getNewLabel();
        code.emit("ifeq " + l1);

        right.compile();
        code.emit("ifeq " + l1);

        code.emit("iconst_1");
        String l2 = code.getNewLabel();
        code.emit("goto " + l2);

        code.emit(l1 + ":");
        code.emit("iconst_0");
        code.emit(l2 + ":");
    }
}
