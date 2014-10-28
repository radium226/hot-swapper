package radium.schnitzel.rx;

import java.nio.file.WatchEvent;

import rx.functions.Func1;

public class WatchEventContextFunc1<T> implements Func1<WatchEvent<T>, T> {

    public WatchEventContextFunc1() {
        super();
    }

    @Override
    public T call(WatchEvent<T> event) {
        T context = event.context();
        return context;
    }

}
