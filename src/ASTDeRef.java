public class ASTDeRef implements ASTNode {
    private ASTNode expression;

    public ASTDeRef(ASTNode expression) {
        this.expression = expression;
    }

    public IValue eval(Environment<IValue> env) {
        VCell reference = (VCell) expression.eval(env);
        return reference.get();
    }

    public IType typecheck(Environment<IType> env) throws TypeException {
        IType type = expression.typecheck(env);

        if (!(type instanceof TRef)) {
            throw new TypeException(
                String.format(
                    "Expected an expression of type 'ref' but found and expression of type '%s' instead.", type.show()
                )
            );
        }
        return ((TRef) type).getType();
    }
}
