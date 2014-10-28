package radium.event;

import java.util.List;

public class WordAccumulator implements Accumulator<String, String> {

    public WordAccumulator() {
        super();
    }

    public String accumulate(List<String> terms) {
        StringBuilder accumulation = new StringBuilder();
        for (String term : terms) {
            accumulation.append(term).append(" ");
        }
        return accumulation.toString();
    }

}
