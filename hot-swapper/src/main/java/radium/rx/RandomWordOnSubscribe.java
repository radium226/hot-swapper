package radium.rx;

import java.util.Random;
import java.util.concurrent.TimeUnit;

import radium.event.Event;
import rx.Observable.OnSubscribe;
import rx.Subscriber;

public abstract class RandomWordOnSubscribe implements OnSubscribe<String> {

    final public static Random RANDOM = new Random();

    final public static int MAX_DURATION = 1000;
    final public static int MIN_DURATION = 200;
    final public static TimeUnit DURATION_UNIT = TimeUnit.MILLISECONDS;

    public RandomWordOnSubscribe() {
        super();
    }

    @Override
    public void call(final Subscriber<? super String> subscriber) {
        /*new Thread(new Runnable() {

         @Override
         public void run() {*/
        System.out.println("HEEEEEEEELLLOOOOOO NOUNOU");
        for (int index = 0; index < 50 && !subscriber.isUnsubscribed(); index++) {
            int duration = randomizeDuration();
            sleep(duration, DURATION_UNIT);
            String word = randomizeWord();
            subscriber.onNext(word);
        }
        subscriber.onCompleted();
        /*}
			
         }).start();*/
    }

    public static int randomizeInteger(int min, int max) {
        return RANDOM.nextInt(max - min) + min;
    }

    public int randomizeDuration() {
        return randomizeInteger(MIN_DURATION, MAX_DURATION + 1);
    }

    public String randomizeWord() {
        int index = randomizeInteger(0, getWords().length);
        String word = getWords()[index];
        return word;
    }

    protected void sleep(int duration, TimeUnit unit) {
        try {
            Thread.sleep(unit.toMillis(duration));
        } catch (InterruptedException e) {

        }
    }

    public abstract String[] getWords();

}
