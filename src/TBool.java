public class TBool implements IType {
    private static TBool instance = null;

    private TBool() {}

    public static TBool getInstance() {
        if (instance == null) {
            instance = new TBool();
        }
        return instance;
    }

    @Override
    public String show() {
        return "bool";
    }

    @Override
    public String showSimple() {
        return show();
    }

    @Override
    public boolean equals(Object obj) {
        return (obj instanceof TBool);
    }
}
