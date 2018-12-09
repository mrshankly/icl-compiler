public class ASTBool implements ASTNode {
    private boolean value;

    public ASTBool(boolean value) {
        this.value = value;
    }

    public IValue eval(Environment<IValue> env) {
        return new VBool(value);
    }

    public IType typecheck(Environment<IType> env) {
        return TBool.getInstance();
    }

    public void compile() {
        Code.getMain().emit(value ? "iconst_1" : "iconst_0");
    }
}
