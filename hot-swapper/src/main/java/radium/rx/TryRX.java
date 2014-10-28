package radium.rx;

import java.util.List;
import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

public class TryRX {

    public static void main(String[] arguments) {
        Observable<String> observable = Observable.merge(
                Observable.create(RandomWords.INSULTS).subscribeOn(Schedulers.newThread()),
                Observable.create(RandomWords.NAMES).subscribeOn(Schedulers.newThread())
        );

        observable.buffer(10, TimeUnit.SECONDS, 3).map(new Func1<List<String>, String>() {

            @Override
            public String call(List<String> words) {
                StringBuilder sentence = new StringBuilder("--> ");
                for (String word : words) {
                    sentence.append(word).append(" ");
                }
                return sentence.toString();
            }

        }).subscribeOn(Schedulers.io()).subscribe(PrintlnSubscriber.STANDARD_OUTPUT);
        System.out.println(" ----> ");
        try {
            Thread.sleep(100000);
        } catch (InterruptedException e) {

        }
    }

}
