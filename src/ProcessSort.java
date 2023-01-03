import java.util.Comparator;

/**
 * This class implements Comparator and is used to sort the priority queue by the priority number for each
 * process. This is so the priority queue will execute the process with the lowest priority number, and will
 * update when the priority numbers change or are updated.
 */
class ProcessSort implements Comparator<Process> {

    /**
     * Compares o1 and o2, which are both processes, based on their priority number.
     *
     * @param o1 the first object to be compared.
     * @param o2 the second object to be compared.
     * @return 1, -1, or 0 depending on if o1's priority number is bigger, smaller, or the same as o2.
     */
    @Override
    public int compare(Process o1, Process o2) {
        if (o1.getPriority() > o2.getPriority())
            return 1; // if o1's priority is bigger than o2's
        else if (o1.getPriority() < o2.getPriority())
            return -1; //if o1's priority is smaller than o2's
        return 0; //if the priorities are the same
    }
}