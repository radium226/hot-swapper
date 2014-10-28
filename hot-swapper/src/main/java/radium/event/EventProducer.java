package radium.event;

public interface EventProducer<E> {

    <C extends EventConsumer<E>> C produceEvents(C eventConsumer);

}
