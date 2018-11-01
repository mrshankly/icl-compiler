public class ASTGreaterThan implements ASTNode {
    private ASTNode left, right;

    public ASTGreaterThan(ASTNode left, ASTNode right) {
        this.left = left;
        this.right = right;
    }

    public IValue eval(Environment env) throws InvalidTypeException, NameNotDefinedException, NameAlreadyDefinedException {
        IValue v1 = left.eval(env);
        IValue v2 = right.eval(env);

        if (!(v1 instanceof VInt) || !(v2 instanceof VInt)) {
            throw new InvalidTypeException();
        }
        return new VBool(((VInt) v1).getValue() > ((VInt) v2).getValue());
    }
}
