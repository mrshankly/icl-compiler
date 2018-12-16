public class ASTAssign implements ASTNode {
    private ASTNode left, right;
    private IType type;

    public ASTAssign(ASTNode left, ASTNode right) {
        this.left = left;
        this.right = right;
        this.type = null;
    }

    public IValue eval(Environment<IValue> env) {
        VCell reference = (VCell) left.eval(env);
        IValue value = right.eval(env);

        reference.set(value);
        return value;
    }

    public IType typecheck(Environment<IType> env) throws TypeException {
        IType ref = left.typecheck(env);

        if (!(ref instanceof TRef)) {
            throw new TypeException(
                String.format(
                    "Expected an expression of type 'ref' but found and expression of type '%s' instead.", ref.show()
                )
            );
        }

        IType expected = ((TRef) ref).getType();
        IType actual = right.typecheck(env);

        if (!(actual.equals(expected))) {
            throw new TypeException(expected, actual);
        }
        type = actual;
        return actual;
    }

    public IType getType() {
        return type;
    }

    public void compile(Environment<Integer> env) {
        Code code = Code.getInstance();

        right.compile(env);
        code.emit("dup");

        left.compile(env);
        code.emit("checkcast " + type.getJVMReferenceClass());
        code.emit("swap");
        code.emit("putfield " + type.getJVMReferenceClass() + "/value " + type.getJVMType());
    }
}
