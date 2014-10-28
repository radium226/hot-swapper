package radium.rx;

import rx.functions.Func1;

public class KeepEvenIntegerFunc1 implements Func1<Integer, Boolean> {

    public static KeepEvenIntegerFunc1 INSTANCE = new KeepEvenIntegerFunc1();

    @Override
    public Boolean call(Integer integer) {
        return integer % 2 == 0;
    }

}
