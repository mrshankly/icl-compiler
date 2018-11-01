public class ASTLet implements ASTNode {
    private String name;
    private ASTNode init, body;

    public ASTLet(String name, ASTNode init, ASTNode body) {
        this.name = name;
        this.init = init;
        this.body = body;
    }

    public IValue eval(Environment env) throws InvalidTypeException, NameNotDefinedException, NameAlreadyDefinedException {
        IValue v1 = init.eval(env);
        
        Environment bodyEnv = env.beginScope();
        bodyEnv.assoc(name,v1);
        
        IValue v2 = body.eval(bodyEnv);
        bodyEnv.endScope();
        return v2;
    }
}