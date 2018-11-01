import java.util.List;
import java.util.ArrayList;

public class Environment {

    private class Binding {
        public final String name;
        public final int value;

        public Binding(String name, int value) {
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

    public void assoc(String name, int value) throws NameAlreadyDefinedException {
        if (getBinding(name) == null) {
            throw new NameAlreadyDefinedException();
        }
        bindings.add(new Binding(name, value));
    }

    public int find(String name) throws NameNotDefinedException {
        Environment env = this;

        while (env != null) {
            Binding bind = env.getBinding(name);
            if (bind != null) {
                return bind.value;
            }
            env = env.parent;
        }
        throw new NameNotDefinedException();
    }
}
