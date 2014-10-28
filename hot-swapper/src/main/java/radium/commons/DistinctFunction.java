package radium.commons;

import java.util.List;

import com.google.common.base.Function;
import com.google.common.collect.ImmutableSet;

public class DistinctFunction<T> implements Function<List<T>, List<T>> {

    public DistinctFunction() {
        super();
    }

    @Override
    public List<T> apply(List<T> input) {
        List<T> output = ImmutableSet.copyOf(input).asList();
        return output;
    }

}
