package radium.event;

public class Event<T> {

    private T context;

    public Event(T context) {
        this.context = context;
    }

    public T getContext() {
        return context;
    }

}
