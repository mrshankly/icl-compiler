import java.util.List;
import java.util.ArrayList;

public class ASTLet implements ASTNode {
    private List<String> names;
    private List<ASTNode> initExprs;
    private List<IType> initTypes;
    private ASTNode body;

    public ASTLet(List<String> names, List<ASTNode> initExprs, List<IType> initTypes, ASTNode body) {
        this.names = names;
        this.initExprs = initExprs;
        this.initTypes = initTypes;
        this.body = body;
    }

    public IValue eval(Environment<IValue> env) {
        IValue value = null;
        Environment<IValue> bodyEnv = (body == null) ? env : env.beginScope();

        for (String name : names) {
            bodyEnv.assoc(name, VUndefined.getInstance());
        }
        for (int i = 0; i < names.size(); i++) {
            value = initExprs.get(i).eval(bodyEnv);
            bodyEnv.smash(names.get(i), value);
        }
        if (body != null) {
            value = body.eval(bodyEnv);
            bodyEnv.endScope();
        }

        if (value == null) {
            throw new RuntimeException("No return value for let expression.");
        }
        return value;
    }

    public IType typecheck(Environment<IType> env) throws TypeException {
        IType type = null;
        Environment<IType> bodyEnv = (body == null) ? env : env.beginScope();

        // Create a temporary environment to check that the types in initTypes
        // match the types of the ASTNodes in initExprs. We need this temporary
        // environment so we don't polute the top level environment (when body
        // is null) in case there is a type error.
        Environment<IType> tempEnv = bodyEnv.beginScope();

        try {
            for (int i = 0; i < initTypes.size(); i++) {
                IType expected = initTypes.get(i);
                tempEnv.assoc(names.get(i), expected);

                IType actual = initExprs.get(i).typecheck(tempEnv);
                if (!(actual.equals(expected))) {
                    throw new TypeException(expected, actual);
                }
            }
        } catch (NameAlreadyDefinedException e) {
            throw new TypeException(e.getMessage());
        } finally {
            tempEnv.endScope();
            tempEnv = null;
        }

        // The ASTNodes in initExprs typecheck to the types in initTypes, we can
        // safely do the assoc operation in bodyEnv for each name.
        for (int i = 0; i < initTypes.size(); i++) {
            type = initTypes.get(i);
            bodyEnv.assoc(names.get(i), type);
        }
        if (body != null) {
            type = body.typecheck(bodyEnv);
            bodyEnv.endScope();
        }

        if (type == null) {
            throw new RuntimeException("No type for let expression.");
        }
        return type;
    }

    public void compile() {
    }
}
