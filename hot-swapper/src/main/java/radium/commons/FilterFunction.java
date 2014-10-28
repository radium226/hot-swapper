package radium.commons;

import java.util.List;

import com.google.common.base.Function;
import com.google.common.base.Predicate;
import static com.google.common.collect.Collections2.filter;
import com.google.common.collect.Lists;

public class FilterFunction<T> implements Function<List<T>, List<T>> {

    private Predicate<T> predicate;

    public FilterFunction(Predicate<T> predicate) {
        super();

        this.predicate = predicate;
    }

    @Override
    public List<T> apply(List<T> input) {
        List<T> output = Lists.newArrayList(filter(input, predicate));
        return output;
    }

}
