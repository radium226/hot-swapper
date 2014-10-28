package radium.event;

import java.util.ArrayList;
import java.util.List;

public class EventAccumulator<I, O> implements EventConsumer<I>, EventProducer<O> {

    private Accumulator<I, O> accumulator;
    private List<Event<I>> events = new ArrayList<Event<I>>();
    private EventConsumer<O> eventConsumer;

    public EventAccumulator(Accumulator<I, O> accumulator) {
        super();

        this.accumulator = accumulator;
    }

    public <C extends EventConsumer<O>> C produceEvents(C eventConsumer) {
        this.eventConsumer = eventConsumer;
        return eventConsumer;
    }

    public void consumeEvent(Event<I> event) {
        events.add(event);
        if (events.size() >= 3) {
            List<I> inputContexts = new ArrayList<I>();
            for (Event<I> inputEvent : events) {
                inputContexts.add(inputEvent.getContext());
            }
            O outputContext = accumulator.accumulate(inputContexts);
            eventConsumer.consumeEvent(new Event(outputContext));
            events.clear();
        }
    }

}
