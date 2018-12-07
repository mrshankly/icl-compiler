public class ASTNum implements ASTNode {
    private int value;

    public ASTNum(int value) {
        this.value = value;
    }

    public IValue eval(Environment<IValue> env) {
        return new VInt(value);
    }

    public IType typecheck(Environment<IType> env) {
        return TInt.getInstance();
    }
}
