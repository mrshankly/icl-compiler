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

    public IType getType() {
        return TBool.getInstance();
    }

    public void compile(Environment<Integer> env) {
        Code.getInstance().emit(value ? "iconst_1" : "iconst_0");
    }
}
