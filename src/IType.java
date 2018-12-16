public interface IType {
    public String show();
    public String showSimple();

    // Helper methods for JVM assembly code.
    public String getJVMType();
    public String getJVMTypePrefix();
    public String getJVMReferenceClass();
}
