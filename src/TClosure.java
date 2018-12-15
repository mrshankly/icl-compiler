import java.util.List;
import java.util.stream.Collectors;

public class TClosure implements IType {
    private List<IType> paramsTypes;
    private IType ret;
    private String interfaceName;
    private String callSignature;

    public TClosure(List<IType> paramsTypes, IType ret) {
        this.paramsTypes = paramsTypes;
        this.ret = ret;
        this.interfaceName = null;
        this.callSignature = null;
    }

    public List<IType> getParamsTypes() {
        return paramsTypes;
    }

    public IType getReturnType() {
        return ret;
    }

    public String getInterfaceName() {
        if (interfaceName == null) {
            interfaceName = "closure_interface";
            for (IType t : paramsTypes) {
                interfaceName += "_" + t.showSimple();
            }
            interfaceName += "_" + ret.showSimple();
        }
        return interfaceName;
    }

    public String getCallSignature() {
        if (callSignature == null) {
            callSignature = "(";
            for (IType t : paramsTypes) {
                callSignature += Code.getJVMType(t);
            }
            callSignature += ")" + Code.getJVMType(ret);
        }
        return callSignature;
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
