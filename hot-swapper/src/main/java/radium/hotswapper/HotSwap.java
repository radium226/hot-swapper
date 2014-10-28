package radium.hotswapper;

import java.io.File;

public class HotSwap {

    final public static String PATH = "C:\\Projects\\Others\\HotSwapVM\\example\\target\\classes\\radium\\example\\impl\\OneByOneLooper.class";

    public static void main(String[] arguments) throws Throwable {
        HotSwapper hotSwapper = new HotSwapper();
        hotSwapper.connect("localhost", "8000");
        hotSwapper.replace(new File(arguments[0]), "radium.example.impl.OneByOneLooper");
        hotSwapper.disconnect();
    }

}
