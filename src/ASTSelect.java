public class ASTSelect implements ASTNode {
    private ASTNode node;
    private String name;
    private TRecord recordType;
    private IType type;

    public ASTSelect(ASTNode node, String name) {
        this.node = node;
        this.name = name;
        this.type = null;
    }

    public IValue eval(Environment<IValue> env) {
        VRecord record = (VRecord) node.eval(env);
        return record.get(name);
    }

    public IType typecheck(Environment<IType> env) throws TypeException {
        IType t = node.typecheck(env);

        if (!(t instanceof TRecord)) {
            throw new TypeException(
                String.format(
                    "Expected an expression of type 'record' but found and expression of type '%s' instead.", t.show()
                )
            );
        }

        recordType = (TRecord) t;
        IType fieldType = recordType.get(name);

        if (fieldType == null) {
            throw new TypeException(
                String.format(
                    "The field '%s' dos not belong to the record %s.", name, recordType.show()
                )
            );
        }
        type = fieldType;
        return type;
    }

    public IType getType() {
        return type;
    }

    public void compile(Environment<Integer> env) {
        Code code = Code.getInstance();
        String classname = recordType.getJVMRecordClass();

        node.compile(env);
        code.emit("checkcast " + classname);
        code.emit("getfield " + classname + "/" + name + " " + type.getJVMType());
    }
}
