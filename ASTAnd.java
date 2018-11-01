public class ASTAnd implements ASTNode {
    private ASTNode left, right;

    public ASTAnd(ASTNode left, ASTNode right) {
        this.left = left;
        this.right = right;
    }

    public IValue eval(Environment env) throws InvalidTypeException, NameNotDefinedException, NameAlreadyDefinedException{
        IValue v1 = left.eval(env);
        IValue v2 = right.eval(env);

        if (!(v1 instanceof VBool) || !(v2 instanceof VBool)) {
            throw new InvalidTypeException();
        }
        return new VBool(((VBool) v1).getValue() && ((VBool) v2).getValue());
    }
}
