public class ASTMul implements ASTNode {
    private ASTNode left, right;
    private IType type;

    public ASTMul(ASTNode left, ASTNode right) {
        this.left = left;
        this.right = right;
        this.type = null;
    }

    public IValue eval(Environment<IValue> env) {
        VInt v1 = (VInt) left.eval(env);
        VInt v2 = (VInt) right.eval(env);

        return new VInt(v1.getValue() * v2.getValue());
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
        type = t1;
        return t1;
    }

    public IType getType() {
        return type;
    }

    public void compile(Environment<Integer> env) {
        left.compile(env);
        right.compile(env);
        Code.getInstance().emit("imul");
    }
}
