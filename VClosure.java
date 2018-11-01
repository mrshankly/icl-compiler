import java.util.List;
import java.util.ArrayList;

public class VClosure implements IValue {
    private List<String> params;
    private ASTNode body;
    private Environment env;

    public VClosure(List<String> params, ASTNode body, Environment env) {
        this.params = params;
        this.body = body;
        this.env = env;
    }

    public List<String> getParams() {
        return params;
    }

    public ASTNode getBody() {
        return body;
    }

    public Environment getEnvironment() {
        return env;
    }

    public String show() {
        return String.format("Closure[%d]", params.size());
    }
}
