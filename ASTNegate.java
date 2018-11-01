public class ASTNegate implements ASTNode {
  private ASTNode node;

    public ASTNegate(ASTNode node) {
        this.node = node;
    }

    public IValue eval(Environment env) throws ArgumentsNumberMismatchException, InvalidTypeException, NameNotDefinedException, NameAlreadyDefinedException {
        IValue v1 = node.eval(env);

        if (!(v1 instanceof VInt)) {
            throw new InvalidTypeException();
        }
        return new VInt(-1*((VInt) v1).getValue());
    }
}
