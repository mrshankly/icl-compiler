public class VBool implements IValue {
    private boolean value;

    public VBool(boolean value) {
        this.value = value;
    }

    public boolean getValue() {
        return value;
    }

    public String show() {
        return String.format("%s", value ? "true" : "false");
    }
  
    @Override
    public boolean equals(Object obj){
        if(!(obj instanceof VBool))
            return false;
        return value == ((VBool)obj).value;
    }
}
