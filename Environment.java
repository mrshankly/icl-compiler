import java.util.List;
import java.util.ArrayList;

public class Environment<T> {

    private class Binding<T> {
        public String name;
        public T value;

        public Binding(String name, T value) {
            this.name = name;
            this.value = value;
        }
    }

    private List<Binding<T>> bindings;
    private Environment<T> parent;

    public Environment() {
        this(null);
    }

    public Environment(Environment<T> parent) {
        this.bindings = new ArrayList<Binding<T>>(10);
        this.parent = parent;
    }

    public Environment<T> beginScope() {
        return new Environment<T>(this);
    }

    public Environment<T> endScope() {
        return parent;
    }

    private Binding<T> getBinding(String name) {
        for (Binding<T> bind : bindings) {
            if (bind.name.equals(name)) {
                return bind;
            }
        }
        return null;
    }

    public void assoc(String name, T value) {
        if (getBinding(name) != null) {
            throw new NameAlreadyDefinedException("Name '" + name + "' is already defined.");
        }
        bindings.add(new Binding<T>(name, value));
    }

    public void smash(String name, T value) {
        Binding<T> bind = getBinding(name);

        if (bind == null) {
            throw new NameNotDeclaredException("Missing declaration for name '" + name + "'.");
        }
        bind.value = value;
    }

    public T find(String name) {
        Environment<T> env = this;

        while (env != null) {
            Binding<T> bind = env.getBinding(name);
            if (bind != null) {
                return bind.value;
            }
            env = env.parent;
        }
        throw new NameNotDeclaredException("Missing declaration for name '" + name + "'.");
    }
}
