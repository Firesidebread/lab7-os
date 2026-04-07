import java.util.Random;

/**
 * Lab 7: Thread Barrier
 *
 * Main class
 */
public class Main {

    private static int threadCount = 10;
    private static final int barrierSize = 5; // feel free to modify for testing purposes
    private static final int sleepTime = 500;

    /**
     * Main program
     *    Create a barrier
     *    Create multiple instances of Process and run them in threads.
     *    Modify the code such that threadCount is provided as command line argument when you run Main
     * @param args
     */
    public static void main(String[] args) {

        if (args.length > 0) {
            threadCount = Integer.parseInt(args[0]);
        }

        if (threadCount <= barrierSize) {
            System.out.println("Error: threadCount must be greater than barrierSize.");
            return;
        }

        // Create a random source for randomly setting the sleep time of the
        // process instances
        Random r = new Random();

        // Print out the number of threads
        System.out.println("Number of threads = " + threadCount);

        // Create the barrier
        Barrier barrier = new Barrier(barrierSize, threadCount);

        // Create and start the process threads
        // There are threadCount threads
        for (int i = 1; i <= threadCount; i++) {
            int randomSleep = r.nextInt(sleepTime);
            Process p = new Process(barrier, i, randomSleep);
            Thread t = new Thread(p);
            t.start();
        }
    }
}