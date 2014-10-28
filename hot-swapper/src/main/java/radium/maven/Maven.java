package radium.maven;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Maven {

    public static class Run {

        private Process process;

        public Run(Process process) {
            super();

            this.process = process;
        }

        public int waitForTermination() {
            try {
                return process.waitFor();
            } catch (InterruptedException e) {
                e.printStackTrace(System.err);
                return -1;
            }
        }

        public boolean isTerminated() {
            try {
                process.exitValue();
                return true;
            } catch (java.lang.IllegalThreadStateException e) {
                return false;
            }
        }

        public int terminate() {
            process.destroy();
            return waitForTermination();
        }

    }

    private String pomPath;

    protected Maven(String pomPath) {
        super();
        this.pomPath = pomPath;
    }

    public static Maven forPOM(String pomPath) {
        return new Maven(pomPath);
    }

    public Maven.Run run(String goal) {
        Process mavenProcess;
        try {
            mavenProcess = new ProcessBuilder("bash", "-c", "for i in 1 2 3 4 5 6 7 8 9 10; do echo ${i} ; sleep 1; done ; mvn -f '" + this.pomPath + "' 'clean' '" + goal + "'")
                    .inheritIO()
                    .start();
            return new Maven.Run(mavenProcess);
        } catch (IOException ex) {
            ex.printStackTrace(System.err);
            return null;
        }

    }

}
