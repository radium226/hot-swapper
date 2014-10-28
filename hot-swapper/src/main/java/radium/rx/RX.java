package radium.rx;

import com.google.common.base.Function;
import com.google.common.base.Predicate;

import rx.functions.Func1;

public class RX {

    public static <I, O> Func1<I, O> rx(final Function<I, O> guava) {
        Func1<I, O> rx = new Func1<I, O>() {

            @Override
            public O call(I input) {
                return guava.apply(input);
            }

        };

        return rx;
    }

    public static <T> Func1<T, Boolean> rx(final Predicate<T> guava) {
        Func1<T, Boolean> rx = new Func1<T, Boolean>() {

            @Override
            public Boolean call(T input) {
                return guava.apply(input);
            }

        };

        return rx;
    }

}
