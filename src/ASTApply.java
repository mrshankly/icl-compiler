import java.util.List;
import java.util.ArrayList;

public class ASTApply implements ASTNode {
    private ASTNode lambda;
    private List<ASTNode> args;
    private IType type;

    public ASTApply(ASTNode lambda, List<ASTNode> args) {
        this.lambda = lambda;
        this.args = args;
        this.type = null;
    }

    public IValue eval(Environment<IValue> env) {
        VClosure closure = (VClosure) lambda.eval(env);
        List<String> params = closure.getParams();

        Environment<IValue> innerEnv = closure.getEnvironment().beginScope();
        for (int i = 0; i < args.size(); i++) {
            innerEnv.assoc(params.get(i), args.get(i).eval(env));
        }

        IValue value = closure.getBody().eval(innerEnv);
        innerEnv.endScope();
        return value;
    }

    public IType typecheck(Environment<IType> env) throws TypeException {
        IType lambdaType = lambda.typecheck(env);

        if (!(lambdaType instanceof TClosure)) {
            throw new TypeException(
                String.format(
                    "Expected an expression of type 'closure' but found and expression of type '%s' instead.",
                    lambdaType.show()
                )
            );
        }

        List<IType> paramsTypes = ((TClosure) lambdaType).getParamsTypes();
        if (args.size() != paramsTypes.size()) {
            throw new TypeException(
                "Function with arity of " + paramsTypes.size() + " applied to " + args.size() + " arguments."
            );
        }
        for (int i = 0; i < args.size(); i++) {
            IType expected = paramsTypes.get(i);
            IType actual = args.get(i).typecheck(env);

            if (!(actual.equals(expected))) {
                throw new TypeException(expected, actual);
            }
        }

        type = ((TClosure) lambdaType).getReturnType();
        return type;
    }

    public IType getType() {
        return type;
    }

    public void compile(Environment<Integer> env) {
    }
}
