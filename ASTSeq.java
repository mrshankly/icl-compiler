public class ASTSeq implements ASTNode {
    private ASTNode expression1, expression2;

    public ASTSeq(ASTNode expression1, ASTNode expression2) {
        this.expression1 = expression1;
        this.expression2 = expression2;
    }

    public IValue eval() throws InvalidTypeException {
        expression1.eval();
        return expression2.eval();
    }
}