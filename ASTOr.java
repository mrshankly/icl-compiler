public class ASTOr implements ASTNode {
    private ASTNode left, right;

    public ASTOr(ASTNode left, ASTNode right) {
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

        if (!(v1 instanceof VBool)) {
            throw new InvalidTypeException(VBool.TYPE, v1.showType());
        }
        if (!(v2 instanceof VBool)) {
            throw new InvalidTypeException(VBool.TYPE, v2.showType());
        }
        return new VBool(((VBool) v1).getValue() || ((VBool) v2).getValue());
    }
}
