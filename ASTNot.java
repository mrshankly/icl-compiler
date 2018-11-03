public class ASTNot implements ASTNode {
    private ASTNode node;

    public ASTNot(ASTNode node) {
        this.node = node;
    }

    public IValue eval(Environment env) throws ArgumentsNumberMismatchException,
                                               InvalidTypeException,
                                               NameAlreadyDefinedException,
                                               NameNotDefinedException
    {
        IValue value = node.eval(env);

        if (!(value instanceof VBool)) {
            throw new InvalidTypeException(VBool.TYPE, value.showType());
        }
        return new VBool(!((VBool) value).getValue());
    }
}
