import java.util.List;
import java.util.ArrayList;

public class VClosure implements IValue {
    public static final String TYPE = "closure";

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

    @Override
    public String show() {
        return String.format("Closure[%d]", params.size());
    }

    @Override
    public String showType() {
        return TYPE;
    }

    @Override
    public boolean equals(Object obj) {
        return obj == this;
    }
}
