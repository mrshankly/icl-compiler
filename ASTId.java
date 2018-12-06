public class ASTId implements ASTNode {
    private String name;

    public ASTId(String name) {
        this.name = name;
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
            return env.find(name);
        } catch (NameNotDeclaredException e) {
            throw new TypeException(e.getMessage());
        }
    }
}
