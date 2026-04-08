import java.util.ArrayList;
import java.util.Collections;

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
     * Number of threads that have arrived at the barrier.
     */
    private int waitingCount = 0;

    /**
     * Whether the barrier has opened.
     */
    private boolean barrierOpen = false;

    /**
     * Random exit order queue for the challenge.
     * The values are arrival ticket numbers.
     */
    private ArrayList<Integer> queue;

    /**
     * Index into the queue showing whose turn it is to leave next.
     * Starts at 0 because queue.get(0) is the first allowed ticket.
     */
    private int nextExitIndex = 0;

    /**
     * Create a barrier of a given size
     *
     * @param size
     */
    public Barrier(int size, int threadCount) {
        barrierSize = size;
        queue = initQueue(threadCount);
        if (queue != null) {
            System.out.println("Exit order: " + queue);
        }
        System.out.println("Barrier size = " + barrierSize);
    }

    /**
     * Processes join at barrier and either wait or are allowed past.
     *
     * @param p The process joining
     */
    public synchronized void joinBarrier(Process p) {

        waitingCount++;
        p.setTicket(waitingCount);

        System.out.println(p.getName() + " waiting on barrier with Ticket " + p.getTicket());

        if (!barrierOpen && waitingCount > barrierSize) {
            barrierOpen = true;
            notifyAll();
        }

        while (!barrierOpen || !isMyTurn(p)) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        System.out.println(p.getName() + " passed the barrier");

        nextExitIndex++;
        notifyAll();
    }

    /**
     * Returns true if it is this process's turn to leave according to the queue.
     */
    private boolean isMyTurn(Process p) {
        return queue.get(nextExitIndex) == p.getTicket();
    }

    /**
     * Creates the queue to determine the order the processes can leave the barrier in.
     * Used for the challenge part only.
     * You are not expected to modify this method.
     *
     * @param size
     * @return queue
     */
    private ArrayList<Integer> initQueue(int size) {
        ArrayList<Integer> queue = new ArrayList<>();
        for (int i = 1; i <= size; i++) {
            queue.add(i);
        }
        Collections.shuffle(queue);
        return queue;
    }
}