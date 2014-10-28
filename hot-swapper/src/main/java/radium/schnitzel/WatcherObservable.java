package radium.schnitzel;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.WatchEvent;

import radium.os.WatchEventHandler;
import radium.os.Watcher;
import rx.Observable;
import rx.Subscriber;

public class WatcherObservable extends Observable<WatchEvent<Path>> {

    protected WatcherObservable(Observable.OnSubscribe<WatchEvent<Path>> onSubscribe) {
        super(onSubscribe);
    }

    public static WatcherObservable create(final String path) {
        return create(Paths.get(path));
    }

    public static WatcherObservable create(final Path path) {
        return new WatcherObservable(new Observable.OnSubscribe<WatchEvent<Path>>() {

            @Override
            public void call(final Subscriber<? super WatchEvent<Path>> subscriber) {
                try {
                    Watcher.watch(path).with(new WatchEventHandler() {

                        @Override
                        public void handleEvent(WatchEvent<Path> event) {
                            subscriber.onNext(event);
                        }

                    });
                } catch (IOException e) {
                    e.printStackTrace(System.err);
                }
            }

        });
    }

}
