public class ASTNegate implements ASTNode {
  private ASTNode node;

    public ASTNegate(ASTNode node) {
        this.node = node;
    }

    public IValue eval(Environment env) throws ArgumentsNumberMismatchException,
                                               InvalidTypeException,
                                               NameAlreadyDefinedException,
                                               NameNotDefinedException
    {
        IValue value = node.eval(env);

        if (!(value instanceof VInt)) {
            throw new InvalidTypeException(VInt.TYPE, value.showType());
        }
        return new VInt(-((VInt) value).getValue());
    }
}
