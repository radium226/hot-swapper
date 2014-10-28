package radium.commons;

import java.nio.file.Path;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.google.common.base.Predicate;

import rx.functions.Func1;

public class MatchFileNamePredicate implements Predicate<Path> {

    private String pattern;

    public MatchFileNamePredicate(String pattern) {
        super();

        this.pattern = pattern;
    }

    @Override
    public boolean apply(Path path) {
        Path name = path.getFileName();
        return Pattern.matches(pattern, name.toString());
    }

}
