package radium.schnitzel.rx;

import java.util.concurrent.TimeUnit;

public class BufferOperators {

    public static <T> BufferUntilTimeoutOperator<T> untilTimeout(int duration, TimeUnit unit) {
        return new BufferUntilTimeoutOperator<T>(duration, unit);
    }

}
