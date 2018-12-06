public class TInt implements IType {
    private static TInt instance = null;

    private TInt() {}

    public static TInt getInstance() {
        if (instance == null) {
            instance = new TInt();
        }
        return instance;
    }

    @Override
    public String show() {
        return "int";
    }

    @Override
    public boolean equals(Object obj) {
        return (obj instanceof TInt);
    }
}
