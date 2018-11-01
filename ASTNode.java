public interface ASTNode {
    public IValue eval(Environment env) throws ArgumentsNumberMismatchException, InvalidTypeException, NameNotDefinedException, NameAlreadyDefinedException;
}
