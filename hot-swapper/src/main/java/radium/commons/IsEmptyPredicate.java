package radium.commons;

import java.util.List;

import com.google.common.base.Predicate;

public class IsEmptyPredicate<T> implements Predicate<List<T>> {

    public IsEmptyPredicate() {
        super();
    }

    @Override
    public boolean apply(List<T> input) {
        return input.isEmpty();
    }

}
