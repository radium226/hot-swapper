package radium.rx;

import rx.functions.Func1;

public class IntegerToStringFunc1 implements Func1<Integer, String> {

    final public static IntegerToStringFunc1 INSTANCE = new IntegerToStringFunc1();

    public String call(Integer integer) {
        return integer.toString();
    }

}
