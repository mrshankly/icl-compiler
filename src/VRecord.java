import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class VRecord implements IValue {
    private Map<String, IValue> fields;

    public VRecord(List<String> fieldNames, List<IValue> values) {
        fields = new HashMap<String, IValue>(fieldNames.size());
        for (int i = 0; i < fieldNames.size(); i++) {
            fields.put(fieldNames.get(i), values.get(i));
        }
    }

    public IValue get(String fieldName) {
        return fields.get(fieldName);
    }

    @Override
    public String show() {
        String content = fields.entrySet().stream()
                                          .map(e -> e.getKey() + " = " + e.getValue().show())
                                          .collect(Collectors.joining(", "));
        return "[" + content + "]";
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof VRecord)) {
            return false;
        }

        VRecord record = (VRecord) obj;

        if (record.fields.size() == this.fields.size()) {
            for (Map.Entry<String,IValue> entry : record.fields.entrySet()) {
                IValue value = this.fields.get(entry.getKey());
                if (value == null || !value.equals(entry.getValue()))
                    return false;
            }
            return true;
        }
        return false;
    }
}
