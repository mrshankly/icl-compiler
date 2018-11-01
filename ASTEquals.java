public class ASTEquals implements ASTNode {
    private ASTNode left, right;

    public ASTEquals(ASTNode left, ASTNode right) {
        this.left = left;
        this.right = right;
    }

    public IValue eval(Environment env) throws InvalidTypeException, NameNotDefinedException, NameAlreadyDefinedException {
        IValue v1 = left.eval(env);
        IValue v2 = right.eval(env);

        if (!(v1.getClass().equals(v2.getClass()))) {
            throw new InvalidTypeException("Type mismatch");
        }
        return new VBool(v1.equals(v2));
    }
}
