package radium.schnitzel;

import java.io.IOException;
import radium.maven.Maven;

public class TryMaven {

    public static void main(String[] arguments) throws Throwable {
        Thread otherThread = new Thread(new Runnable() {

            @Override
            public void run() {
//                try {
//                    Process process = new ProcessBuilder("sleep", "10").inheritIO().start();
//                    process.waitFor();
//                } catch (IOException|InterruptedException e) {
//                    e.printStackTrace(System.err);
//                }
                Maven.forPOM(TrySchitzel.POM).run("compile").waitForTermination();
            }
            
        });
        
        
        otherThread.start();
        
        otherThread.join();
    }
    
    
}
