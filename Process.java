/**
 * Lab 7: Thread Barrier.
 * Process tries to join the barrier.
 *
 */
public class Process implements Runnable {

    /**
     * The barrier which the process joins.
     */
    private Barrier barrier;

    /**
     * The process name: 'Thread n', where n is the number of the thread.
     */
    private String name;

    /**
     * The sleep time: representing the work done before joining the barrier.
     */
    private int sleepTime;

    /**
     * Create a process.
     *
     * @param b the barrier.
     * @param n the thread number.
     * @param s the length of time to sleep before joining the barrier.
     */
    public Process(Barrier b, int n, int s) {
        barrier = b;
        name = "Thread " + n;
        sleepTime = s;
    }

    /**
     * Get name of process.
     *
     * @return
     */
    public String getName() {
        return name;
    }

    /**
     * A process sleeps for a while and then joins the barrier.
     *
     */
    @Override
    public void run() {
        try {
            Thread.sleep(sleepTime);
            barrier.joinBarrier(this);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}