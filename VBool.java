public class VBool implements IValue {
    public static final String TYPE = "bool";

    private boolean value;

    public VBool(boolean value) {
        this.value = value;
    }

    public boolean getValue() {
        return value;
    }

    @Override
    public String show() {
        return String.format("%s", value ? "true" : "false");
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
        if (!(obj instanceof VBool)) {
            return false;
        }
        return value == ((VBool) obj).value;
    }
}
