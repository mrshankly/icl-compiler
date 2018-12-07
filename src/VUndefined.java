public class VUndefined implements IValue {
    private static VUndefined instance = null;

    private VUndefined() {}

    public static VUndefined getInstance() {
        if (instance == null) {
            instance = new VUndefined();
        }
        return instance;
    }

    @Override
    public String show() {
        return "undefined";
    }

    @Override
    public boolean equals(Object obj) {
        return false;
    }
}
