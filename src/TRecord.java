import java.util.Map;
import java.util.HashMap;
import java.util.List;

public class TRecord implements IType {
    private Map<String, IType> fields;

    public TRecord(List<String> fieldNames, List<IType> types) {
        fields = new HashMap<String,IType>(fieldNames.size());
        for(int i = 0; i < fieldNames.size() ; i++){         
            fields.put(fieldNames.get(i),types.get(i));
        }
    }

    @Override
    public String show() {
        String content = "[";
        for(Map.Entry<String,IType> field : fields.entrySet()){
            content += "; " + field.getKey() + ":" + field.getValue();
        }
        return (content + "]");
    }

    @Override
    public String showSimple() {
        return "record";
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
        return "ref_obj";//TODO: ver isto
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof TRecord)) {
            return false;
        }

        TRecord record = (TRecord) obj;

        if (record.fields.size() == this.fields.size()){
            for(Map.Entry<String,IType> entry : record.fields.entrySet()){
                IType type = this.fields.get(entry.getKey());
                if (type == null || !type.equals(entry.getValue()))
                    return false;
            }
            return true;
        }

        return false;
    }
}