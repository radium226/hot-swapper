package radium.hot_swapper.agent;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.WatchEvent;
import java.util.HashMap;

/*import adrien.io.InputOutputHandler;
 import adrien.os.OS;
 import adrien.os.handler.RedirectOutputHandler;
 import adrien.os.shell.Cygwin; */
import com.sun.jdi.connect.IllegalConnectorArgumentsException;

import radium.jdi.ClassRedefiner;
import radium.os.WatchEventHandler;
import radium.os.Watcher;
import static java.nio.file.StandardWatchEventKinds.*;

public class WatchFolder {

    public static void main(String[] arguments) throws IOException {
        Thread watchForClassFilesThread = watchForClassFiles();
        Thread watchForSourceFilesThread = watchForSourceFiles();

        watchForClassFilesThread.start();
        watchForSourceFilesThread.start();

        try {
            watchForClassFilesThread.join();
            watchForSourceFilesThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace(System.err);
        }

    }

    public static Thread watchForClassFiles() {
        return new Thread(new Runnable() {

            public void run() {
                String path = "C:\\Projects\\Others\\HotSwapVM\\webapp\\target\\webapp\\WEB-INF\\classes"; //arguments[0];

                try {
                    Watcher.watch(Paths.get(path)).with(new WatchEventHandler() {

                        @Override
                        public void handleEvent(WatchEvent<Path> event) {
                            if (event.kind().equals(ENTRY_MODIFY)) {
                                File file = event.context().toFile();
                                try {
                                    Thread.sleep(1000);
                                    ClassRedefiner.redefineClassInVMFromClassFile(file);
                                } catch (IOException e) {
                                    e.printStackTrace(System.err);
                                } catch (IllegalConnectorArgumentsException e) {
                                    e.printStackTrace(System.err);
                                } catch (InterruptedException e) {
                                    e.printStackTrace(System.err);
                                }
                            }
                        }
                    });
                } catch (IOException e) {
                    e.printStackTrace(System.err);
                }
            }
        });
    }

    public static void runMaven() {
        /*try {
         Cygwin.newShell()
         .cd(new File("C:\\Projects\\Others\\HotSwapVM"))
         .run("mvn", new String[] {"-f", "./webapp/pom.xml", "war:exploded"}, RedirectOutputHandler.STANDARD_OUTPUT);
         } catch (IOException e) {
         e.printStackTrace(System.err);
         } catch (InterruptedException e) {
         e.printStackTrace(System.err);
         }*/
    }

    public static Thread watchForSourceFiles() {
        return new Thread(new Runnable() {

            public void run() {
                try {
                    String path = "C:\\Projects\\Others\\HotSwapVM\\webapp\\src\\main\\java"; //arguments[0];

                    Watcher.watch(Paths.get(path)).with(new WatchEventHandler() {

                        @Override
                        public void handleEvent(WatchEvent<Path> event) {
                            runMaven();
                        }
                    });
                } catch (Throwable e) {
                    e.printStackTrace(System.err);
                }
            }
        });
    }

}
