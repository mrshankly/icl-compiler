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
        this.type = actual;
        return actual;
    }

    public void compile() {
        Code code = Code.getInstance();
        String jvmType, classname;

        if (type instanceof TInt || type instanceof TBool) {
            classname = "ref_int";
            jvmType = "I";
        } else {
            classname = "ref_obj";
            jvmType = "Ljava/lang/Object";
        }

        left.compile();
        code.emit("checkcast " + classname);
        code.emit("dup");

        right.compile();
        code.emit("putfield " + classname + "/value " + jvmType);
        code.emit("getfield " + classname + "/value " + jvmType);
    }
}
