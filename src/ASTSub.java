public class ASTSub implements ASTNode {
    private ASTNode left, right;

    public ASTSub(ASTNode left, ASTNode right) {
        this.left = left;
        this.right = right;
    }

    public IValue eval(Environment<IValue> env) {
        VInt v1 = (VInt) left.eval(env);
        VInt v2 = (VInt) right.eval(env);

        return new VInt(v1.getValue() - v2.getValue());
    }

    public IType typecheck(Environment<IType> env) throws TypeException {
        IType t1 = left.typecheck(env);
        IType t2 = right.typecheck(env);

        if (!(t1 instanceof TInt)) {
            throw new TypeException(TInt.getInstance(), t1);
        }
        if (!(t2 instanceof TInt)) {
            throw new TypeException(TInt.getInstance(), t2);
        }
        return t1;
    }

    public void compile() {
        left.compile();
        right.compile();
        Code.getMain().emit("isub");
    }
}
