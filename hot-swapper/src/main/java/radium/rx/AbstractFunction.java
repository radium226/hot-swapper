package radium.rx;

import rx.functions.Func1;

import com.google.common.base.Function;

public abstract class AbstractFunction<I, O> implements Function<I, O>, Func1<I, O> {

    @Override
    public O call(I input) {
        return apply(input);
    }

    @Override
    public abstract O apply(I input);

}
