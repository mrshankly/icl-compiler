public class VString implements IValue {
    private String value;

    public VString(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    @Override
    public String show() {
        return "\"" + value + "\"";
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof VString)) {
            return false;
        }
        return value.equals(((VString) obj).value);
    }
}