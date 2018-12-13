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
        Code code = Code.getInstance();
        String frameName = "frame_TODO"; 

        if(code.startCode(frameName + ".j")){
            code.emit(".class " + frameName);
            code.emit(".super java/lang/Object");
            code.emit(".field public sl Lancestor_TODO");
            for (int i = 0 ; i < names.size() ; i++) {
                String jvmType = Code.getJVMType(initTypes.get(i));
                code.emit(".field public " + names.get(i) + " " + jvmType);
            }
            code.emit(".method public <init>()V");
            code.emit("aload_0");
            code.emit("invokenonvirtual java/lang/Object/<init>()V");
            code.emit("return");
            code.emit(".end method");
            code.endCode();
        }
        
        code.emit("new " + frameName);
        code.emit("dup");
        code.emit("invokespecial " + frameName + "/<init>()V");
        code.emit("dup");
        code.emit("aload SL");
        code.emit("putfield " + frameName + "/sl L" + frameName);//TODO
        code.emit("dup");
        code.emit("astore SL");
        for (int i = 0 ; i < initExprs.size() ; i++){
            String jvmType = Code.getJVMType(initTypes.get(i));
            code.emit("dup");
            initExprs.get(i).compile();
            code.emit("putfield " + frameName + "/x" + i + " " + jvmType);
        }
        body.compile();
        code.emit("aload SL");
        code.emit("getfield " + frameName + "/sl L" + frameName);//TODO
        code.emit("astore SL");
    }
}
