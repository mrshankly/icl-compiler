public class ASTAnd implements ASTNode {
    private ASTNode left, right;

    public ASTAnd(ASTNode left, ASTNode right) {
        this.left = left;
        this.right = right;
    }

    public IValue eval() throws InvalidTypeException {
        IValue v1 = left.eval();
        IValue v2 = right.eval();

        if (!(v1 instanceof VBool) || !(v2 instanceof VBool)) {
            throw new InvalidTypeException();
        }
        return new VBool(((VBool) v1).getValue() && ((VBool) v2).getValue());
    }
}
