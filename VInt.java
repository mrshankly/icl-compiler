public class VInt implements IValue {
    private int value;

    public VInt(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public String show() {
        return String.format("%d", value);
    }
  
    @Override
    public boolean equals(Object obj){
      if(!(obj instanceof VInt))
        return false;
      return value == ((VInt)obj).value;
    }
}
