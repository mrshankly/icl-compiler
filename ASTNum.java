public class ASTNum implements ASTNode {
    private int value;

    public ASTNum(int value) {
        this.value = value;
    }

    public IValue eval() {
        return new VInt(value);
    }
}
