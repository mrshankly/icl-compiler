public class ASTDeRef implements ASTNode {
    private ASTNode expression;
    private IType type;

    public ASTDeRef(ASTNode expression) {
        this.expression = expression;
        this.type = null;
    }

    public IValue eval(Environment<IValue> env) {
        VCell reference = (VCell) expression.eval(env);
        return reference.get();
    }

    public IType typecheck(Environment<IType> env) throws TypeException {
        IType type = expression.typecheck(env);

        if (!(type instanceof TRef)) {
            throw new TypeException(
                String.format(
                    "Expected an expression of type 'ref' but found and expression of type '%s' instead.", type.show()
                )
            );
        }
        this.type = ((TRef) type).getType();
        return this.type;
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

        expression.compile();
        code.emit("checkcast " + classname);
        code.emit("getfield " + classname + "/value " + jvmType);
    }
}
