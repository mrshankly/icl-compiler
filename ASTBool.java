public class ASTBool implements ASTNode {
    private boolean value;

    public ASTBool(boolean value) {
        this.value = value;
    }

    public IValue eval(Environment env) {
        return new VBool(value);
    }
}
