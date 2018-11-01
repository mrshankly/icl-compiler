public class ASTNum implements ASTNode {
    private int value;

    public ASTNum(int value) {
        this.value = value;
    }

    public IValue eval(Environment env) {
        return new VInt(value);
    }
}
