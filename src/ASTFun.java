import java.util.List;
import java.util.ArrayList;

public class ASTFun implements ASTNode {
    private List<String> params;
    private List<IType> paramsTypes;
    private ASTNode body;
    private IType type;

    public ASTFun(List<String> params, List<IType> paramsTypes, ASTNode body) {
        this.params = params;
        this.paramsTypes = paramsTypes;
        this.body = body;
        this.type = null;
    }

    public IValue eval(Environment<IValue> env) {
        return new VClosure(params, body, env);
    }

    public IType typecheck(Environment<IType> env) throws TypeException {
        Environment<IType> bodyEnv = env.beginScope();

        try {
            for (int i = 0; i < paramsTypes.size(); i++) {
                bodyEnv.assoc(params.get(i), paramsTypes.get(i));
            }
        } catch (NameAlreadyDefinedException e) {
            throw new TypeException(e.getMessage());
        }
        IType ret = body.typecheck(bodyEnv);
        bodyEnv.endScope();

        type = new TClosure(paramsTypes, ret);
        return type;
    }

    public IType getType() {
        return type;
    }

    public void compile(Environment<Integer> env) {
    }
}
