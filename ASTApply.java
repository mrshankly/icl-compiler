import java.util.List;
import java.util.ArrayList;

public class ASTApply implements ASTNode {
    private ASTNode lambda;
    private List<ASTNode> args;

    public ASTApply(ASTNode lambda, List<ASTNode> args) {
        this.lambda = lambda;
        this.args = args;
    }

    public IValue eval(Environment env) throws ArgumentsNumberMismatchException, InvalidTypeException, NameNotDefinedException, NameAlreadyDefinedException {
        IValue v1 = lambda.eval(env);
        if (!(v1 instanceof VClosure)) {
            throw new InvalidTypeException();
        }
        VClosure closure = (VClosure)v1;

        List<String> params = closure.getParams();
        if (args.size() != params.size()) {
            throw new ArgumentsNumberMismatchException();
        }

        Environment innerEnv = closure.getEnvironment().beginScope();
        for (int i = 0; i < args.size(); i++) {
            innerEnv.assoc(params.get(i), args.get(i).eval(env));
        }

        IValue value = closure.getBody().eval(innerEnv);
        innerEnv.endScope();
        return value;
    }
}
