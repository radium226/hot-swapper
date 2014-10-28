package radium.rx;

import rx.Observable;
import rx.Subscriber;

public class CountOnSubscribe implements Observable.OnSubscribe<Integer> {

    private int counter = 0;

    public CountOnSubscribe() {
        super();
    }

    @Override
    public void call(Subscriber<? super Integer> subscriber) {
        while (true) {
            System.out.println("<Calling onNext(" + counter + ") / " + subscriber.isUnsubscribed());
            subscriber.onNext(Integer.valueOf(counter++));
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
            }
        }
    }

}
