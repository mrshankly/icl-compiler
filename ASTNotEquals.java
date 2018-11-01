public class ASTNotEquals implements ASTNode {
    private ASTNode left, right;

    public ASTNotEquals(ASTNode left, ASTNode right) {
        this.left = left;
        this.right = right;
    }

    public IValue eval() throws InvalidTypeException {
        IValue v1 = left.eval();
        IValue v2 = right.eval();

        if (!(v1.getClass().equals(v2.getClass()))) {
            throw new InvalidTypeException("Type mismatch");
        }
        return new VBool(!(v1.equals(v2)));
    }
}