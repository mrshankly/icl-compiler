public interface ASTNode {
    IValue eval(Environment<IValue> env);

    IType typecheck(Environment<IType> env) throws TypeException;

    IType getType();

    void compile();

}
