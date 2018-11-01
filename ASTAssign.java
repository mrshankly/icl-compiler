public class ASTAssign implements ASTNode {
    private ASTNode left, right;

    public ASTAssign(ASTNode left, ASTNode right) {
        this.left = left;
        this.right = right;
    }

    public IValue eval(Environment env) throws InvalidTypeException, NameNotDefinedException, NameAlreadyDefinedException {
        IValue reference = left.eval(env);
        
        if (!(reference instanceof VCell)) {
            throw new InvalidTypeException();
        }
      
        IValue value = right.eval(env);
        ((VCell) reference).set(value);
        return value;
    }
}