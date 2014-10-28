package radium.event;

import java.util.Random;
import java.util.concurrent.TimeUnit;

public class EventRandomizer implements EventProducer<String> {

    final public static Random RANDOM = new Random();

    final public static int MAX_DURATION = 1000;
    final public static int MIN_DURATION = 200;
    final public static TimeUnit DURATION_UNIT = TimeUnit.MILLISECONDS;

    final public static String[] CONTEXTS = new String[]{
        "I", "Need", "More", "Fucking", "Money", "! "
    };

    public EventRandomizer() {
        super();
    }

    public <C extends EventConsumer<String>> C produceEvents(final C eventConsumer) {
        new Thread(new Runnable() {

            public void run() {

            }

        }).start();

        return eventConsumer;
    }

    public static int randomizeInteger(int min, int max) {
        return RANDOM.nextInt(max - min) + min;
    }

    public static int randomizeDuration() {
        return randomizeInteger(MIN_DURATION, MAX_DURATION + 1);
    }

    public static Event<String> randomizeEvent() {
        int index = randomizeInteger(0, CONTEXTS.length);
        String context = CONTEXTS[index];
        Event<String> event = new Event<String>(context);
        return event;
    }

    private static void sleep(int duration, TimeUnit unit) {
        try {
            Thread.sleep(unit.toMillis(duration));
        } catch (InterruptedException e) {

        }
    }

}
