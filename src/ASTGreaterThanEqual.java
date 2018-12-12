public class ASTGreaterThanEqual implements ASTNode {
    private ASTNode left, right;

    public ASTGreaterThanEqual(ASTNode left, ASTNode right) {
        this.left = left;
        this.right = right;
    }

    public IValue eval(Environment<IValue> env) {
        VInt v1 = (VInt) left.eval(env);
        VInt v2 = (VInt) right.eval(env);

        return new VBool(v1.getValue() >= v2.getValue());
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
        return TBool.getInstance();
    }

    public void compile() {
        Code code = Code.getInstance();

        left.compile();
        right.compile();

        String l1 = code.getNewLabel();
        code.emit("if_icmpge " + l1);

        code.emit("iconst_0");
        String l2 = code.getNewLabel();
        code.emit("goto " + l2);

        code.emit(l1 + ":");
        code.emit("iconst_1");

        code.emit(l2 + ":");
    }
}
