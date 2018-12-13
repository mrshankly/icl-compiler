public class ASTId implements ASTNode {
    private String name;
    private IType type;

    public ASTId(String name) {
        this.name = name;
        this.type = null;
    }

    public IValue eval(Environment<IValue> env) {
        IValue value = env.find(name);

        if (value instanceof VUndefined) {
            throw new NameNotDefinedException("Name '" + name + "' is undefined.");
        }
        return value;
    }

    public IType typecheck(Environment<IType> env) throws TypeException {
        try {
            type = env.find(name);
        } catch (NameNotDeclaredException e) {
            throw new TypeException(e.getMessage());
        }
        return type;
    }

    public IType getType() {
        return type;
    }

    public void compile() {
    }
}
