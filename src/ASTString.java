public class ASTString implements ASTNode {
    private String value;

    public ASTString(String value) {
        this.value = value;
    }

    public IValue eval(Environment<IValue> env) {
        return new VString(value);
    }

    public IType typecheck(Environment<IType> env) {
        return TString.getInstance();
    }

    public IType getType() {
        return TString.getInstance();
    }

    public void compile(Environment<Integer> env) {
        Code.getInstance().emit("ldc \"" + value + "\"");
    }
}
