import java.util.List;
import java.util.ArrayList;

public class Environment {

    private class Binding {
        public final String name;
        public final IValue value;

        public Binding(String name, IValue value) {
            this.name = name;
            this.value = value;
        }
    }

    private List<Binding> bindings;
    private Environment parent;

    public Environment() {
        this(null);
    }

    public Environment(Environment parent) {
        this.bindings = new ArrayList<Binding>(10);
        this.parent = parent;
    }

    public Environment beginScope() {
        return new Environment(this);
    }

    public Environment endScope() {
        return parent;
    }

    private Binding getBinding(String name) {
        for (Binding bind : bindings) {
            if (bind.name.equals(name)) {
                return bind;
            }
        }
        return null;
    }

    public void assoc(String name, IValue value) throws NameAlreadyDefinedException {
        if (getBinding(name) != null) {
            throw new NameAlreadyDefinedException("Error: Name '" + name + "' is already defined.");
        }
        bindings.add(new Binding(name, value));
    }

    public IValue find(String name) throws NameNotDefinedException {
        Environment env = this;

        while (env != null) {
            Binding bind = env.getBinding(name);
            if (bind != null) {
                return bind.value;
            }
            env = env.parent;
        }
        throw new NameNotDefinedException("Error: Name '" + name + "' is undefined.");
    }
}
