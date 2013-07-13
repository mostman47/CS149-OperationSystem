/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author mr.nam
 */
public class Process {
    private  String name;
    private  float arrival;
    private  int run;
    private  int priority;
    public Process(String name, float arrival, int run, int priority)
    {
        this.name = name;
        this.arrival = arrival;
        this.run = run;
        this.priority = priority;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getArrivalTime() {
        return arrival;
    }

    public void setArrivalTime(float arrival) {
        this.arrival = arrival;
    }

    public int getRunTime() {
        return run;
    }

    public void setRunTime(int run) {
        this.run = run;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    
}
