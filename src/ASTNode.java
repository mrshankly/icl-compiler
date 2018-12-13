public interface ASTNode {
    IValue eval(Environment<IValue> env);

    IType typecheck(Environment<IType> env) throws TypeException;

    void compile(Environment<Integer> env);

    IType getType();
}
