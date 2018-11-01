public interface ASTNode {
    public IValue eval(Environment env) throws InvalidTypeException, NameNotDefinedException, NameAlreadyDefinedException;
}
