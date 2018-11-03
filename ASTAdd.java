public class ASTAdd implements ASTNode {
    private ASTNode left, right;

    public ASTAdd(ASTNode left, ASTNode right) {
        this.left = left;
        this.right = right;
    }

    public IValue eval(Environment env) throws ArgumentsNumberMismatchException,
                                               InvalidTypeException,
                                               NameAlreadyDefinedException,
                                               NameNotDefinedException
    {
        IValue v1 = left.eval(env);
        IValue v2 = right.eval(env);

        if (!(v1 instanceof VInt)) {
            throw new InvalidTypeException(VInt.TYPE, v1.showType());
        }
        if (!(v2 instanceof VInt)) {
            throw new InvalidTypeException(VInt.TYPE, v2.showType());
        }
        return new VInt(((VInt) v1).getValue() + ((VInt) v2).getValue());
    }
}
