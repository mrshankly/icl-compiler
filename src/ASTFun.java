import java.util.List;
import java.util.ArrayList;

public class ASTFun implements ASTNode {
    private static long functionCounter;

    private List<String> params;
    private List<IType> paramsTypes;
    private ASTNode body;
    private TClosure type;
    private long functionId;

    public ASTFun(List<String> params, List<IType> paramsTypes, ASTNode body) {
        this.params = params;
        this.paramsTypes = paramsTypes;
        this.body = body;
        this.type = null;
        functionId = functionCounter++;
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
        Environment<Integer> bodyEnv = env.beginScope();
        Code code = Code.getInstance();

        String interfaceName = type.getInterfaceName();
        String callSignature = type.getCallSignature();

        // Create interface if it doesn't exist.
        if (code.startCode(interfaceName + ".j")) {
            code.emit(".interface public " + interfaceName);
            code.emit(".super java/lang/Object");
            code.emit(".method abstract call" + callSignature);
            code.emit(".end method");
            code.endCode();
        }

        String functionFrame = bodyEnv.getFrameName();
        String parentFrame = env.getFrameName();

        // Create function frame.
        if (code.startCode(functionFrame + ".j")) {
            code.emit(".class public " + functionFrame);
            code.emit(".super java/lang/Object");
            code.emit(".field public sl L" + parentFrame + ";");

            // Create a field for each function argument.
            for (int i = 0; i < params.size(); i++) {
                bodyEnv.assoc(params.get(i), i);
                code.emit(".field public v" + i + " " + Code.getJVMType(paramsTypes.get(i)));
            }

            code.emit(".method public <init>()V");
            code.emit("aload_0");
            code.emit("invokenonvirtual java/lang/Object/<init>()V");
            code.emit("return");
            code.emit(".end method");
            code.endCode();
        } else {
            throw new RuntimeException("'" + functionFrame + ".j' already exists.");
        }

        String functionClass = "closure_" + functionId;

        // Create function class.
        if (code.startCode(functionClass + ".j")) {
            code.emit(".class public " + functionClass);
            code.emit(".super java/lang/Object");
            code.emit(".implements " + interfaceName);
            code.emit(".field public sl L" + parentFrame + ";");

            code.emit(".method public <init>()V");
            code.emit("aload_0");
            code.emit("invokenonvirtual java/lang/Object/<init>()V");
            code.emit("return");
            code.emit(".end method");

            code.emit(".method public call" + callSignature);
            code.emit("\t.limit locals " + (params.size() + 1));

            code.emit("new " + functionFrame);
            code.emit("dup");
            code.emit("aload_0");
            code.emit("getfield " + functionClass + "/sl L" + parentFrame + ";");
            code.emit("putfield " + functionFrame + "/sl L" + parentFrame + ";");

            for (int i = 0; i < paramsTypes.size(); i++) {
                code.emit("dup");
                code.emit("aload " + (i + 1));
                code.emit("putfield " + functionFrame + "/v" + i + " " + Code.getJVMType(paramsTypes.get(i)));
            }

            code.emit("astore_3");
            body.compile(bodyEnv);
            code.emit("return");
            code.emit(".end method");

            code.endCode();
        } else {
            throw new RuntimeException("'" + functionClass + ".j' already exists.");
        }
        bodyEnv.endScope();

        code.emit("new " + functionClass);
        code.emit("dup");
        code.emit("aload_3");
        code.emit("putfield " + functionClass + "/sl L" + parentFrame + ";");
    }
}
