public class ASTEquals implements ASTNode {
    private ASTNode left, right;

    public ASTEquals(ASTNode left, ASTNode right) {
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

        if (!(v1.getClass().equals(v2.getClass()))) {
            throw new InvalidTypeException(v1.showType(), v2.showType());
        }
        return new VBool(v1.equals(v2));
    }
}
