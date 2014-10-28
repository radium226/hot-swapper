package radium.hotswapper;

import javassist.ClassPool;
import javassist.CtClass;
import javassist.util.HotSwapper;

public class JavassistHotSwapper {

    public static void main() throws Throwable {
        ClassPool pool = ClassPool.getDefault();
        pool.appendClassPath((String) null);
        CtClass ctClass = pool.get((String) null);
        System.out.println(ctClass);

        HotSwapper hotSwapper = new HotSwapper(8000);
        hotSwapper.reload(null, ctClass.toBytecode());
    }

}
