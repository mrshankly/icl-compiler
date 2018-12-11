public class ASTGreaterThan implements ASTNode {
    private ASTNode left, right;

    public ASTGreaterThan(ASTNode left, ASTNode right) {
        this.left = left;
        this.right = right;
    }

    public IValue eval(Environment<IValue> env) {
        VInt v1 = (VInt) left.eval(env);
        VInt v2 = (VInt) right.eval(env);

        return new VBool(v1.getValue() > v2.getValue());
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

        Code mainCode = Code.getMain();

        left.compile();
        right.compile();

        String l1 = mainCode.getNewLabel();
        
        mainCode.emit("if_icmpgt " + l1);

        mainCode.emit("iconst_0");
        String l2 = mainCode.getNewLabel();
        mainCode.emit("goto " + l2);

        mainCode.emit(l1 + ":");
        mainCode.emit("iconst_1");
        
        mainCode.emit(l2 + ":");
    }
}
