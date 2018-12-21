import java.util.List;

public class VClosure implements IValue {
    private List<String> params;
    private ASTNode body;
    private Environment<IValue> env;

    public VClosure(List<String> params, ASTNode body, Environment<IValue> env) {
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

    public Environment<IValue> getEnvironment() {
        return env;
    }

    @Override
    public String show() {
        return String.format("closure[%d]", params.size());
    }

    @Override
    public boolean equals(Object obj) {
        return obj == this;
    }
}
