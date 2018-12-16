import java.util.List;
import java.util.stream.Collectors;

public class TClosure implements IType {
    private List<IType> paramsTypes;
    private IType ret;

    public TClosure(List<IType> paramsTypes, IType ret) {
        this.paramsTypes = paramsTypes;
        this.ret = ret;
    }

    public List<IType> getParamsTypes() {
        return paramsTypes;
    }

    public IType getReturnType() {
        return ret;
    }

    @Override
    public String show() {
        String paramsString = paramsTypes.stream()
                                         .map(type -> type.show())
                                         .collect(Collectors.joining(", "));

        return String.format("(%s)%s", paramsString, ret.show());
    }

    @Override
    public String showSimple() {
        return "closure";
    }

    @Override
    public String getJVMType() {
        return "Ljava/lang/Object;";
    }

    @Override
    public String getJVMTypePrefix() {
        return "a";
    }

    @Override
    public String getJVMReferenceClass() {
        return "ref_obj";
    }

    public String getJVMInterfaceName() {
        String paramsString = paramsTypes.stream()
                                         .map(t -> t.showSimple())
                                         .collect(Collectors.joining("_"));

        return String.format("closure_interface_%s_%s", paramsString, ret.showSimple());
    }

    public String getJVMCallSignature() {
        String paramsString = paramsTypes.stream()
                                         .map(t -> t.getJVMType())
                                         .collect(Collectors.joining(""));

        return "(" + paramsString + ")" + ret.getJVMType();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof TClosure)) {
            return false;
        }
        TClosure t = (TClosure) obj;
        return paramsTypes.equals(t.paramsTypes) && ret.equals(t.ret);
    }
}
