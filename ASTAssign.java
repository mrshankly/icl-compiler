public class ASTAssign implements ASTNode {
    private ASTNode left, right;

    public ASTAssign(ASTNode left, ASTNode right) {
        this.left = left;
        this.right = right;
    }

    public IValue eval(Environment env) throws ArgumentsNumberMismatchException,
                                               InvalidTypeException,
                                               NameAlreadyDefinedException,
                                               NameNotDefinedException
    {
        IValue reference = left.eval(env);

        if (!(reference instanceof VCell)) {
            throw new InvalidTypeException(VCell.TYPE, reference.showType());
        }

        IValue value = right.eval(env);
        ((VCell) reference).set(value);
        return value;
    }
}
