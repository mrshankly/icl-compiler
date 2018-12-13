import java.util.List;
import java.util.ArrayList;

public class Environment<T> {

    private class Binding<T> {
        public final String name;
        public T value;

        public Binding(String name, T value) {
            this.name = name;
            this.value = value;
        }
    }

    public class FindResult<T> {
        public final T value;
        public final int level;

        public FindResult(T value, int level) {
            this.value = value;
            this.level = level;
        }
    }

    private static long frameCounter;

    private long frameId;
    private List<Binding<T>> bindings;
    private Environment<T> parent;

    public Environment() {
        this(null, -1);
    }

    public Environment(Environment<T> parent) {
        this.bindings = new ArrayList<Binding<T>>(10);
        this.parent = parent;
        frameId = ++frameCounter;
    }

    public Environment(Environment<T> parent, long startingId) {
        this.bindings = new ArrayList<Binding<T>>(10);
        this.parent = parent;
        frameId = frameCounter = startingId;
    }

    public Environment<T> beginScope() {
        return new Environment<T>(this);
    }

    public Environment<T> endScope() {
        return parent;
    }

    public String getFrameName() {
        if (frameId == -1) {
            return "java/lang/Object";
        }
        return "frame_" + frameId;
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

    public FindResult<T> find(String name) {
        Environment<T> env = this;
        int level = 0;

        while (env != null) {
            Binding<T> bind = env.getBinding(name);
            if (bind != null) {
                return new FindResult<T>(bind.value, level);
            }
            env = env.parent;
            level++;
        }
        throw new NameNotDeclaredException("Missing declaration for name '" + name + "'.");
    }
}
