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
        IType t = expression.typecheck(env);

        if (!(t instanceof TRef)) {
            throw new TypeException(
                String.format(
                    "Expected an expression of type 'ref' but found and expression of type '%s' instead.", t.show()
                )
            );
        }
        type = ((TRef) t).getType();
        return type;
    }

    public IType getType() {
        return type;
    }

    public void compile(Environment<Integer> env) {
        Code code = Code.getInstance();
        String jvmType = Code.getJVMType(type);
        String classname = Code.getRefClass(type);

        expression.compile(env);
        code.emit("checkcast " + classname);
        code.emit("getfield " + classname + "/value " + jvmType);
    }
}
