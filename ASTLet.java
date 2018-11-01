import java.util.List;
import java.util.ArrayList;

public class ASTLet implements ASTNode {
    private List<String> names;
    private List<ASTNode> init_exprs;
    private ASTNode body;

    public ASTLet(List<String> names, List<ASTNode> init_exprs, ASTNode body) {
        this.names = names;
        this.init_exprs = init_exprs;
        this.body = body;
    }

    public IValue eval(Environment env) throws ArgumentsNumberMismatchException, InvalidTypeException, NameNotDefinedException, NameAlreadyDefinedException {
        Environment bodyEnv = env.beginScope();

        for (int i = 0; i < names.size(); i++) {
            IValue value = init_exprs.get(i).eval(env);
            bodyEnv.assoc(names.get(i), value);
        }

        IValue ret_value = body.eval(bodyEnv);
        bodyEnv.endScope();
        return ret_value;
    }
}
