public class ASTGreaterThan implements ASTNode {
    private ASTNode left, right;

    public ASTGreaterThan(ASTNode left, ASTNode right) {
        this.left = left;
        this.right = right;
    }

    public IValue eval() throws InvalidTypeException {
        IValue v1 = left.eval();
        IValue v2 = right.eval();

        if (!(v1 instanceof VInt) || !(v2 instanceof VInt)) {
            throw new InvalidTypeException();
        }
        return new VBool(((VInt) v1).getValue() > ((VInt) v2).getValue());
    }
}
