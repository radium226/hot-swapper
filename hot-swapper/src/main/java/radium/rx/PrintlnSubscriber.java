package radium.rx;

import java.io.PrintStream;

import rx.Subscriber;
import rx.functions.Action1;

public class PrintlnSubscriber extends Subscriber<String> {

    final public static PrintlnSubscriber STANDARD_OUTPUT = new PrintlnSubscriber(System.out);

    private String prefix;
    private PrintStream printStream;

    private int count = 0;

    public PrintlnSubscriber(PrintStream printStream) {
        this(printStream, "");
    }

    public PrintlnSubscriber(PrintStream printStream, String prefix) {
        super();
        this.printStream = printStream;
        this.prefix = prefix;
    }

    @Override
    public void onCompleted() {
        printStream.println("(" + threadID() + ") </>");
    }

    @Override
    public void onError(Throwable throwable) {
        throwable.printStackTrace(printStream);
    }

    @Override
    public void onNext(String text) {
        count++;
        printStream.println(prefix + " (" + threadID() + ") " + text);

		//if (count >= 2) {
        //	unsubscribe();
        //}
    }

    public String threadID() {
        String threadID = Long.toString(Thread.currentThread().getId());
        return threadID;
    }

    public PrintlnSubscriber prefix(String prefix) {
        return new PrintlnSubscriber(this.printStream, prefix);
    }

}
