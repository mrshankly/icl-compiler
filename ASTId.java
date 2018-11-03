public class ASTId implements ASTNode {
    private String name;

    public ASTId(String name) {
        this.name = name;
    }

    public IValue eval(Environment env) throws NameNotDefinedException {
        return env.find(name);
    }
}
