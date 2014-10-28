package radium.event;

public interface EventConsumer<E> {

    void consumeEvent(Event<E> event);

}
