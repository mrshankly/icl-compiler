public class ASTNotEquals implements ASTNode {
    private ASTNode left, right;

    public ASTNotEquals(ASTNode left, ASTNode right) {
        this.left = left;
        this.right = right;
    }

    public IValue eval(Environment<IValue> env) {
        IValue v1 = left.eval(env);
        IValue v2 = right.eval(env);

        return new VBool(!(v1.equals(v2)));
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
    }
}
