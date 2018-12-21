import java.util.List;
import java.util.LinkedList;
import java.util.ArrayList;

public class ASTRecord implements ASTNode {
    private List<String> fields;
    private List<ASTNode> initExprs;
    private IType type;

    public ASTRecord(List<String> fields, List<ASTNode> initExprs) {
        this.fields = fields;
        this.initExprs = initExprs;
        this.type = null;
    }

    public IValue eval(Environment<IValue> env) {
        List<IValue> initValues = new LinkedList<IValue>();
        for (ASTNode expression : initExprs){
            initValues.add(expression.eval(env));
        }
        return new VRecord(fields,initValues);
    }

    public IType typecheck(Environment<IType> env) throws TypeException {

        List<IType> fieldTypes = new LinkedList<IType>();
        for (int i = 0; i < initExprs.size(); i++) {
            IType t = initExprs.get(i).typecheck(env);
        }

        for(ASTNode node : initExprs){
            fieldTypes.add(node.typecheck(env));
        }
        type = new TRecord(fields,fieldTypes);
        return type;
    }

    public IType getType() {
        return type;
    }

    public void compile(Environment<Integer> env){

    }
}