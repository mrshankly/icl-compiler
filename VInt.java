public class VInt implements IValue {
    public static final String TYPE = "int";

    private int value;

    public VInt(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    @Override
    public String show() {
        return String.format("%d", value);
    }

    @Override
    public String showType() {
        return TYPE;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof VInt)) {
            return false;
        }
        return value == ((VInt) obj).value;
    }
}
