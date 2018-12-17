public class TString implements IType {
    private static TString instance = null;

    private TString() {}

    public static TString getInstance() {
        if (instance == null) {
            instance = new TString();
        }
        return instance;
    }

    @Override
    public String show() {
        return "string";
    }

    @Override
    public String showSimple() {
        return show();
    }

    @Override
    public String getJVMType() {
        return "Ljava/lang/String;";
    }

    @Override
    public String getJVMTypePrefix() {
        return "a";
    }

    @Override
    public String getJVMReferenceClass() {
        return "ref_string";
    }

    @Override
    public boolean equals(Object obj) {
        return (obj instanceof TString);
    }
}
