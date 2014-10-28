package radium.event;

import java.util.List;

public interface Accumulator<I, O> {

    O accumulate(List<I> terms);

}
