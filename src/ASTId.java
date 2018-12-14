public class ASTId implements ASTNode {
    private String name;
    private IType type;

    public ASTId(String name) {
        this.name = name;
        this.type = null;
    }

    public IValue eval(Environment<IValue> env) {
        IValue value = env.find(name).value;

        if (value instanceof VUndefined) {
            throw new NameNotDefinedException("Name '" + name + "' is undefined.");
        }
        return value;
    }

    public IType typecheck(Environment<IType> env) throws TypeException {
        try {
            type = env.find(name).value;
        } catch (NameNotDeclaredException e) {
            throw new TypeException(e.getMessage());
        }
        return type;
    }

    public IType getType() {
        return type;
    }

    public void compile(Environment<Integer> env) {
        Environment<Integer>.FindResult<Integer> result = env.find(name);
        int level = result.level;
        int index = result.value;

        Code code = Code.getInstance();
        String jvmType = Code.getJVMType(type);

        Environment<Integer> currentEnv = env;
        Environment<Integer> parentEnv = env.endScope();

        code.emit("aload_3");
        for (int i = 1; i <= level; i++) {
            String currentFrame = currentEnv.getFrameName();
            String parentFrame = parentEnv.getFrameName();

            code.emit("getfield " + currentFrame + "/sl L" + parentFrame + ";");
            currentEnv = parentEnv;
            parentEnv = parentEnv.endScope();
        }
        code.emit("getfield " + currentEnv.getFrameName() + "/v" + index + " " + jvmType);
    }
}
