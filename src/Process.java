
/**
 * This class creates an object process for the priority queue to utilize. Also implements get and set methods
 * for a process, has a toString method, and a compareTo method.
 */
public class Process {
    //Each process has a priority, an id number, the time it will arrive into the queue, and the amount of time
    // the process will take to execute.
    int priority;
    int id;
    int arrivalTime;
    int duration;

    /**
     * @param id process id
     * @param priority priority of the process
     * @param duration execution of the process takes this amount of time
     * @param arrivalTime the time when the process arrives at the system
     */
    public Process(int id, int priority, int duration, int arrivalTime) {
        this.priority = priority;
        this.id = id;
        this.arrivalTime = arrivalTime;
        this.duration = duration;
    }

    /**
     * A getter method that returns the arrival time.
     * @return the time that the process arrived in queue
     */
    public int getArrivalTime() {
        return this.arrivalTime;
    }

    /**
     * A getter method from the priority of the process.
     * @return priority number of the process
     */
    public int getPriority() {
        return this.priority;
    }

    /**
     * A getter method for the duration of the process
     * @return The duration of the process/how long it takes to run in the Priority Queue.
     */
    public int getDuration() {
        return this.duration;
    }

    /**
     * A getter method that returns the process id.
     * @return the id of the process.
     */
    public int getId() {
        return this.id;
    }

    /**
     * A setter method for the priority of the process. Takes a new priority number and sets the current priority
     * as the new one.
     * @param new_priority the updated priority for the process.
     */
    public void setPriority(int new_priority) {
        this.priority = new_priority;
    }

    /**
     * This method prints a process out on screen when a string method is called.
     * @return all of the information of the process in a line as a string
     */
    @Override
    public String toString() {
        return "\tId = " + this.id +
                "\tpriority = " + this.priority +
                "\tduration = " + this.duration +
                "\tarrival time = " + this.arrivalTime;
    }
}
