package radium.schnitzel.rx;

import rx.functions.Func1;

public class ToStringFunc1<T> implements Func1<T, String> {

    public ToStringFunc1() {
        super();
    }

    @Override
    public String call(T object) {
        return object == null ? null : object.toString();
    }

}
