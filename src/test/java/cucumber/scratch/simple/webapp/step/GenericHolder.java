package cucumber.scratch.simple.webapp.step;

public class GenericHolder<T> {

    private T value;

    public void set(T value) {
        this.value = value;
    }

    public T get() {
        return value;
    }
}
