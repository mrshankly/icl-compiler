import java.util.List;
import java.util.ArrayList;

public class ASTLet implements ASTNode {
    private List<String> names;
    private List<ASTNode> initExprs;
    private List<IType> initTypes;
    private ASTNode body;
    private IType type;

    public ASTLet(List<String> names, List<ASTNode> initExprs, List<IType> initTypes, ASTNode body) {
        this.names = names;
        this.initExprs = initExprs;
        this.initTypes = initTypes;
        this.body = body;
        this.type = null;
    }

    public IValue eval(Environment<IValue> env) {
        Environment<IValue> bodyEnv = env.beginScope();

        for (String name : names) {
            bodyEnv.assoc(name, VUndefined.getInstance());
        }
        for (int i = 0; i < names.size(); i++) {
            IValue v = initExprs.get(i).eval(bodyEnv);
            bodyEnv.smash(names.get(i), v);
        }

        IValue value = body.eval(bodyEnv);
        bodyEnv.endScope();

        return value;
    }

    public IType typecheck(Environment<IType> env) throws TypeException {
        Environment<IType> bodyEnv = env.beginScope();

        try {
            for (int i = 0; i < names.size(); i++) {
                bodyEnv.assoc(names.get(i), initTypes.get(i));
            }
        } catch (NameAlreadyDefinedException e) {
            throw new TypeException(e.getMessage());
        }
        // Check that the types of the init expressions match the types given.
        for (int i = 0; i < initExprs.size(); i++) {
            IType expected = initTypes.get(i);
            IType actual = initExprs.get(i).typecheck(bodyEnv);
            if (!(actual.equals(expected))) {
                throw new TypeException(expected, actual);
            }
        }

        type = body.typecheck(bodyEnv);
        bodyEnv.endScope();

        return type;
    }

    public IType getType() {
        return type;
    }

    public void compile() {
    }
}
