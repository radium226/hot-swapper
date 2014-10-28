package radium.event;

public class EventToStandardOutputPrinter implements EventConsumer<String> {

    public EventToStandardOutputPrinter() {
        super();
    }

    public void consumeEvent(Event<String> event) {
        String context = event.getContext();
        System.out.println("context = " + context);
    }

}
