import java.util.List;
import java.util.ArrayList;

public class ASTFun implements ASTNode {
    private List<String> params;
    private List<IType> paramsTypes;
    private ASTNode body;

    public ASTFun(List<String> params, List<IType> paramsTypes, ASTNode body) {
        this.params = params;
        this.paramsTypes = paramsTypes;
        this.body = body;
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

        return new TClosure(paramsTypes, ret);
    }

    public void compile() {
    }
}
