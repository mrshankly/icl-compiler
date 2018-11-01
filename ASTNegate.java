public class ASTNegate implements ASTNode {
  private ASTNode node;

    public ASTNegate(ASTNode node) {
        this.node = node;
    }

    public IValue eval() throws InvalidTypeException {
        IValue v1 = node.eval();

        if (!(v1 instanceof VInt)) {
            throw new InvalidTypeException();
        }
        return new VInt(-1*((VInt) v1).getValue());
    }
}