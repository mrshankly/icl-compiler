public class ASTPrintln implements ASTNode {
    private ASTNode node;
    private IType type;

    public ASTPrintln(ASTNode node) {
        this.node = node;
        this.type = null;
    }

    public IValue eval(Environment<IValue> env) {
        IValue value = node.eval(env);
        if (value instanceof VString) {
            System.out.println(((VString)value).getValue());
        } else {
            System.out.println(value.show());
        }
        return value;
    }

    public IType typecheck(Environment<IType> env) throws TypeException {
        type = node.typecheck(env);
        return type;
    }

    public IType getType() {
        return type;
    }

    public void compile(Environment<Integer> env) {
        /*Code code = Code.getInstance();

        node.compile(env);
        code.emit("iconst_1");
        code.emit("ixor");*/
    }
}
