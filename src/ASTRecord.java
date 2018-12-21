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
        for (ASTNode expression : initExprs) {
            initValues.add(expression.eval(env));
        }
        return new VRecord(fields, initValues);
    }

    public IType typecheck(Environment<IType> env) throws TypeException {
        List<IType> fieldTypes = new LinkedList<IType>();

        for (ASTNode node : initExprs) {
            fieldTypes.add(node.typecheck(env));
        }
        type = new TRecord(fields, fieldTypes);
        return type;
    }

    public IType getType() {
        return type;
    }

    public void compile(Environment<Integer> env){
        Code code = Code.getInstance();
        TRecord recordType = (TRecord) type;
        String classname = recordType.getJVMRecordClass();

        // Create record class if it doesn't already exist.
        if (code.startCode(classname + ".j")) {
            code.emit(".class public " + classname);
            code.emit(".super java/lang/Object");

            for (int i = 0; i < fields.size(); i++) {
                String name = fields.get(i);
                String jvmType = recordType.get(name).getJVMType();
                code.emit(".field public " + name + " " + jvmType);
            }

            code.emit(".method public <init>()V");
            code.emit("aload_0");
            code.emit("invokenonvirtual java/lang/Object/<init>()V");
            code.emit("return");
            code.emit(".end method");
            code.endCode();
        }

        code.emit("new " + classname);
        code.emit("dup");
        code.emit("invokespecial " + classname + "/<init>()V");

        for (int i = 0; i < fields.size(); i++) {
            String name = fields.get(i);
            String jvmType = recordType.get(name).getJVMType();
            ASTNode expression = initExprs.get(i);

            code.emit("dup");
            expression.compile(env);
            code.emit("putfield " + classname + "/" + name + " " + jvmType);
        }
    }
}
