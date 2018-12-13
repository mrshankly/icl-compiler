public class ASTNew implements ASTNode {
    private ASTNode expression;
    private IType type;

    public ASTNew(ASTNode expression) {
        this.expression = expression;
        this.type = null;
    }

    public IValue eval(Environment<IValue> env) {
        return new VCell(expression.eval(env));
    }

    public IType typecheck(Environment<IType> env) throws TypeException {
        type = expression.typecheck(env);
        return new TRef(type);
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

        // Create ref class if it doesn't already exist.
        if (code.startCode(classname + ".j")) {
            code.emit(".class " + classname);
            code.emit(".super java/lang/Object");
            code.emit(".field public value " + jvmType);
            code.emit(".method public <init>()V");
            code.emit("aload_0");
            code.emit("invokenonvirtual java/lang/Object/<init>()V");
            code.emit("return");
            code.emit(".end method");
            code.endCode();
        }

        code.emit("new " + classname);
        code.emit("dup");
        code.emit("invokespecial " + classname + "/<init>()V");
        code.emit("dup");
        expression.compile();
        code.emit("putfield " + classname + "/value " + jvmType);
    }
}
