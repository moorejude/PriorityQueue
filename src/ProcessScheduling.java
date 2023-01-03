import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.PriorityQueue;
import java.util.Scanner;

public class ProcessScheduling {

    // Variable maxWaitTime is always 30 for this program, and both the main method and UpdateQueue utilize it.
    public static int maxWaitTime = 30;

    //Main method for the program - runs the simulation
    public static void main(String[] args) throws FileNotFoundException {

        //ArrayList D is used to store all the processes taken from the .txt file before they enter the priority queue.
        // int index is used to stop the while loop that reads the file.
        ArrayList<Process> D = new ArrayList<>();

        double totalWaitTime = 0;
        int array_size;

        //Data is gathered from the .txt file using File and Scanner
        File queue_file = new File("src/process_scheduling_input.txt");
        Scanner ReadFile = new Scanner(queue_file);

        //Loop that gathers data from .txt file and added to the ArrayList D and creates new objects: Process
        while (ReadFile.hasNextLine()) {
            String line = ReadFile.nextLine();
            //Separate each line by the space in-between the numbers
            String[] queue_data = line.split(" ");

            //Add each new Process to D, separating the data out according to the Process
            D.add(new Process(Integer.parseInt(queue_data[0]), Integer.parseInt(queue_data[1]),
                    Integer.parseInt(queue_data[2]), Integer.parseInt(queue_data[3])));
        }
        //Close the input file after data is gathered.
        ReadFile.close();

        //Create an output file. Everything gets printed out to this file.
        PrintWriter out = new PrintWriter("src/process_scheduling_output.txt");

        //Once all the data is gathered, the variable array_size is updated for methods to utilize.
        array_size = D.size();

        //This for loop prints out all the processes being stored in D.
        for (Process process : D) {
            out.println(process);
        }
        out.println();
        out.println("Maximum wait time = " + maxWaitTime + "\n");

        // currentTime is the logical time that keeps track of the simulation process
        int currentTime = 0;
        boolean running = false;

        //Creates a new sort method for the PriorityQueue (sorts by lowest priority number).
        ProcessSort sort = new ProcessSort();

        //Empty PriorityQueue called Q that takes objects of the Process class
        PriorityQueue<Process> Q = new PriorityQueue<>(10, sort);

        //Keeps track of new the new "process" that is being added to Q, and keeps track of the current process that is
        // running - "runningProcess"
        Process process;
        Process runningProcess;

        //endTime is used by the program to track when the current running process is going to finish running.
        // When the currentTime matches the endTime, the process will be done running. The waitTime updates with every
        // loop in order to lower the priority of the processes waiting in queue.
        int endTime = 0;
        int waitTime;

        //This while loop goes until the ArrayList that holds all process' are empty
        while (!D.isEmpty()) {
            int first = D.get(0).getArrivalTime();
            int firstIndex = 0;

            //This loop finds the process with the lowest arrival time.
            for (int i = 0; i < D.size(); i++) {
                int temp = D.get(i).getArrivalTime(); //Find the process with the lowest arrival time.
                if (temp < first) { //Switch it with the first in the array
                    first = temp;
                    firstIndex = i; //return it
                }
            }

            //This loop finds if teh process with the lowest priority time matches the current time.
            process = D.get(firstIndex);
            if (process.getArrivalTime() <= currentTime) {
                Q.add(process); //add to PriorityQueue
                D.remove(firstIndex); //remove from Array
            }

            //If the Q is not empty and a process is not running, the process with the lowest priority is pulled
            // from the queue.
            if (!Q.isEmpty() && !running) {
                runningProcess = Q.poll();
                waitTime = currentTime - runningProcess.getArrivalTime();
                totalWaitTime += waitTime; //add to the total cumulative wait time
                endTime = currentTime + runningProcess.getDuration(); //find out how long the running process will run
                //Print to the .txt document what queue was removed and its information
                out.println("Process removed from queue is: id = " + runningProcess.getId() + ", at time " +
                        currentTime + ", wait time = " + waitTime + ", Total Wait time = " + totalWaitTime);
                out.println("Process id = " + runningProcess.getId() + "\n\tPriority = " +
                        runningProcess.getPriority() + "\n\tArrival = " + runningProcess.getArrivalTime() +
                        "\n\tDuration = " + runningProcess.getDuration());
                //Print to the .txt document when running process finishes.
                out.println("Process " + runningProcess.getId() + " finished at time " + endTime + "\n");
                running = true; //flip the boolean that designates that a process is running.
            }

            //Once D is empty, the time it empties will print on screen.
            if (D.isEmpty()) {
                out.println("D becomes empty at " + currentTime + "\n");
            }

            //For each loop, the currentTime will go up by 1.
            currentTime += 1;

            //If a process is running and the endTime is equal to the program's currentTime
            if (running && endTime == currentTime) {

                //If a process is no longer running, the boolean will switch to false.
                running = false;

                //Call the helper method to update the PriorityQueue
                UpdateQueue(Q, currentTime, out);

                //This prints a line in the process_scheduling_output.txt for easier reading.
                out.println();
            }
        }

        //D is empty, but if the Q still has processes to run, they run in this while loop
        while (!Q.isEmpty()) {
            //If a process is not running
            if (!running) {
                runningProcess = Q.poll();
                waitTime = currentTime - runningProcess.getArrivalTime();
                totalWaitTime += waitTime; //add to the total cumulative wait time
                endTime = currentTime + runningProcess.getDuration(); //find out how long the running process will run
                //Print to the .txt document what queue was removed and its information
                out.println("Process removed from queue is: id = " + runningProcess.getId() + ", at time " +
                        currentTime + ", wait time = " + waitTime + ", Total Wait time = " + totalWaitTime);
                out.println("Process id = " + runningProcess.getId() + "\n\tPriority = " +
                        runningProcess.getPriority() + "\n\tArrival = " + runningProcess.getArrivalTime() +
                        "\n\tDuration = " + runningProcess.getDuration());
                //Print to the .txt document when running process finishes.
                out.println("Process " + runningProcess.getId() + " finished at time " + endTime + "\n");
                running = true; //flip the boolean that designates that a process is running.
            }

            //For each loop, the currentTime will go up by 1.
            currentTime += 1;

            //Once a process finishes running (the endTime of a process = the currentTime of the program
            if (endTime == currentTime) {
                //If a process is no longer running, the boolean will switch to false.
                running = false;

                //Call on helper method to update the PriorityQueue
                UpdateQueue(Q, currentTime, out);
                //This prints a line in the process_scheduling_output.txt for easier reading.
                out.println();
            }
        }

        //Prints the total wait time. Calculates and prints the average wait time for the amount of processes.
        out.println("Total wait time = " + totalWaitTime);
        double average = totalWaitTime / array_size;
        out.println("Average wait time = " + average);

        //Close the output file once everything is printed to it.
        out.close();
    }

    /**
     * This method takes all processes' in the PriorityQueue, adds them to an ArrayList (processArrayList) updates them
     * according to how long each process has been waiting, and if they have been waiting longer than the max amount of
     * time, and then returns them to the PriorityQueue.
     *
     * Information in this method is also printed out to a file that is designated as "out."
     *
     * @param Q PriorityQueue
     * @param currentTime the current logical time that the simulation is in.
     * @param out a PrintWriter reference to what the method will write to or print out to.
     */
    public static void UpdateQueue(PriorityQueue<Process> Q, int currentTime, PrintWriter out) {
        //Create a new ArrayList of processes to store and update all process in PQ
        ArrayList<Process> processArrayList = new ArrayList<>();

        out.println("Update Priority: ");
        //Copy all the processes from PQ to ArrayList - processArrayList
        while (!Q.isEmpty()) {
            processArrayList.add(Q.poll());
        }

        for (Process a_process : processArrayList) {
            //calculates how long process has been waiting
            int a_waitTime = currentTime - a_process.getArrivalTime();
            //If the process has been waiting longer then 30, it updates the priority and re-adds it back to the
            //queue.
            if (a_waitTime >= maxWaitTime) {
                if (a_process.getPriority() > 0) {
                    //Prints out the process that is going to be updated.
                    out.println("PID = " + a_process.getId() + ", wait time = " + a_waitTime + ", " +
                            "current priority = " + a_process.getPriority());
                    //Gets the current priority and subtracts 1
                    int new_priority = a_process.getPriority() - 1;
                    //Sets the process' priority to the new priority
                    a_process.setPriority(new_priority);
                    // Prints out the process and its new priority
                    out.println("PID = " + a_process.getId() + ", new priority = " +
                            a_process.getPriority());
                }
                Q.add(a_process);
                //Even if the process doesn't need to be updated, it is still returned to the PriorityQueue.
            } else {
                Q.add(a_process);
            }
        }
    }
}
