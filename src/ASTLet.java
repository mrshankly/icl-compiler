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

    public void compile(Environment<Integer> env) {
        Environment<Integer> bodyEnv = env.beginScope();
        Code code = Code.getInstance();

        String currentFrame = bodyEnv.getFrameName();
        String parentFrame = env.getFrameName();

        if (code.startCode(currentFrame + ".j")) {
            code.emit(".class public " + currentFrame);
            code.emit(".super java/lang/Object");
            code.emit(".field public sl L" + parentFrame + ";");

            // Create a field for each name declared.
            for (int i = 0; i < names.size(); i++) {
                bodyEnv.assoc(names.get(i), i);
                code.emit(".field public v" + i + " " + Code.getJVMType(initTypes.get(i)));
            }

            code.emit(".method public <init>()V");
            code.emit("aload_0");
            code.emit("invokenonvirtual java/lang/Object/<init>()V");
            code.emit("return");
            code.emit(".end method");
            code.endCode();
        } else {
            // A frame class creation should never fail, if it does it's because
            // that frame class already exists, it's a bug if that ever happens.
            throw new RuntimeException("'" + currentFrame + ".j' already exists.");
        }

        code.emit("new " + currentFrame);
        code.emit("dup");
        code.emit("invokespecial " + currentFrame + "/<init>()V");
        code.emit("dup");
        code.emit("aload_3");
        code.emit("putfield " + currentFrame + "/sl L" + parentFrame + ";");
        code.emit("dup");
        code.emit("astore_3");

        // Initialize the declared names with the corresponding expression.
        for (int i = 0; i < initExprs.size(); i++){
            code.emit("dup");
            initExprs.get(i).compile(bodyEnv);
            code.emit("putfield " + currentFrame + "/v" + i + " " + Code.getJVMType(initTypes.get(i)));
        }
        // Remove the leftover frame from the stack.
        code.emit("pop");

        body.compile(bodyEnv);
        bodyEnv.endScope();

        // Restore parent frame.
        code.emit("aload_3");
        code.emit("getfield " + currentFrame + "/sl L" + parentFrame + ";");
        code.emit("astore_3");
    }
}
