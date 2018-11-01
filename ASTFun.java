import java.util.List;
import java.util.ArrayList;

public class ASTFun implements ASTNode {
    private List<String> params;
    private ASTNode body;

    public ASTFun(List<String> params, ASTNode body) {
        this.params = params;
        this.body = body;
    }

    public IValue eval(Environment env) {
        return new VClosure(params, body, env);
    }
}
