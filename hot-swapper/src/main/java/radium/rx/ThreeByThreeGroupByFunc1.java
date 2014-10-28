package radium.rx;

import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.List;

import rx.functions.Func1;

public class ThreeByThreeGroupByFunc1 implements Func1<Integer, String> {

    final public static int THREE = 3;

    private IntBuffer buffer = IntBuffer.allocate(THREE);
    private int keyIndex = 0;

    public ThreeByThreeGroupByFunc1() {
        super();
    }

    @Override
    public String call(Integer integer) {
        buffer.put(integer);
        String key = "Key #" + keyIndex;
        if (buffer.remaining() == 0) {
            buffer.clear();
            keyIndex++;
        }
        return key;
    }

}
