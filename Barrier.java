/**
 * Lab 7: Thread Barrier
 * Barrier Class
 *
 * Processes join the barrier and are held until barrierSize processes have
 * joined.
 *
 */
public class Barrier {

    /**
     * Size of the barrier, which is the minimum number of processes to proceed.
     */
    private int barrierSize;

    /**
     * Number of threads that have arrived at the barrier so far.
     */
    private int waitingCount = 0;

    /**
     * Whether the barrier has opened.
     */
    private boolean barrierOpen = false;

    /**
     * Create a barrier of a given size
     *
     * @param size
     */
    public Barrier(int size, int threadCount) {
        barrierSize = size;
        System.out.println("Barrier size = " + barrierSize);
    }

    /**
     * Processes join at barrier and either wait or are allowed past.
     *
     * @param p The process joining
     */
    public synchronized void joinBarrier(Process p) {
        System.out.println(p.getName() + " waiting on barrier");

        waitingCount++;

        if (!barrierOpen) {
            if (waitingCount > barrierSize) {
                barrierOpen = true;
                notifyAll();
            } else {
                while (!barrierOpen) {
                    try {
                        wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        System.out.println(p.getName() + " passed the barrier");
    }
}