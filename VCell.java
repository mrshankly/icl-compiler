public class VCell implements IValue {
    public static final String TYPE = "reference";

    private IValue value;

    public VCell(IValue value) {
        this.value = value;
    }

    public IValue get() {
        return value;
    }

    public void set(IValue value) {
        this.value = value;
    }

    @Override
    public String show() {
        return String.format("Ref[%s]", value.show());
    }

    @Override
    public String showType() {
        return TYPE;
    }

    @Override
    public boolean equals(Object obj) {
        return obj == this;
    }
}
