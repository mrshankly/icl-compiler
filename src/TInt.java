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
    public String showSimple() {
        return show();
    }

    @Override
    public String getJVMType() {
        return "I";
    }

    @Override
    public String getJVMTypePrefix() {
        return "i";
    }

    @Override
    public String getJVMReferenceClass() {
        return "ref_int";
    }

    @Override
    public boolean equals(Object obj) {
        return (obj instanceof TInt);
    }
}
