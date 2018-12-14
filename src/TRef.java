public class TRef implements IType {
    private IType type;

    public TRef(IType type) {
        this.type = type;
    }

    public IType getType() {
        return type;
    }

    @Override
    public String show() {
        return String.format("ref %s", type.show());
    }

    @Override
    public String showSimple() {
        return "ref";
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof TRef)) {
            return false;
        }
        return type.equals(((TRef) obj).type);
    }
}
