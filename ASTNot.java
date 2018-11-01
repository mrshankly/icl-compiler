public class ASTNot implements ASTNode {
    private ASTNode node;

    public ASTNot(ASTNode node) {
        this.node = node;
    }

    public IValue eval() throws InvalidTypeException {
        IValue value = node.eval();

        if (!(value instanceof VBool)) {
            throw new InvalidTypeException();
        }
        return new VBool(!((VBool) value).getValue());
    }
}
