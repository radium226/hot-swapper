package radium.event;

public class AggregateEvents {

    public static void main(String[] arguments) {

        EventRandomizer eventRandomizer = new EventRandomizer();
        EventToStandardOutputPrinter eventToStandardOutputPrinter = new EventToStandardOutputPrinter();
        eventRandomizer.produceEvents(new EventAccumulator<String, String>(new WordAccumulator())).produceEvents(eventToStandardOutputPrinter);

    }

}
