
import java.util.ArrayList;
import java.util.Random;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author mr.nam
 */
public class Simulator {

    private Algorithm agl;
    private ArrayList<Process> prcList;

    public Simulator(Algorithm agl) {
        this.agl = agl;
        prcList = new ArrayList<>();
    }

    public Algorithm getAgl() {
        return agl;
    }

    public void setAgl(Algorithm agl) {
        this.agl = agl;
    }

    public ArrayList<Process> getPrcList() {
        return prcList;
    }

    public void setPrcList(ArrayList<Process> prcList) {
        this.prcList = prcList;
    }

    public void generatePrc(int maxTL) {
        Random random = new Random();
        int min = 0;
        int count = 0;
        while (min < maxTL) {
            float arrivalT = random.nextFloat() * 100 % 100;
            int runT = 1 + Math.abs(random.nextInt() % 10);
            int priority = 1 + Math.abs(random.nextInt() % 4);
            Process prc = new Process((char) (65 + count) + "", arrivalT, runT, priority);
            prcList.add(prc);
            min += runT;
            count++;
        }
        agl.checkGenerate(prcList, maxTL);
    }

    public void printProcessList() {
        int length = prcList.size();
        System.out.println("---Processes List---\n");
        System.out.println("\t\tArr_T\t\t\tRun_T\t\tPriority");
        System.out.println("\t\t-----\t\t\t-----\t\t--------");
        for (int i = 0; i < length; i++) {
            System.out.println((i + 1) + ")\t" + prcList.get(i).getName() + "\t" + prcList.get(i).getArrivalTime() + "\t\t" + prcList.get(i).getRunTime() + "\t\t"
                    + prcList.get(i).getPriority());
        }
        System.out.println("---End Processes List---\n");

    }
    private String chart = "";

    public void applyAlg(int timeSlice) {
        String chart = agl.applyAlg(prcList, timeSlice);
        //System.out.println(chart);
        this.chart = chart;
    }

    public String getChart() {
        return chart;
    }

    public void setChart(String chart) {
        this.chart = chart;
    }

    public float getAverageTurnAround() {
        float n = 0;
        int size = prcList.size();
        Process prc;
        for (int i = 0; i < size; i++) {
            prc = prcList.get(i);
            int lastIndex = chart.lastIndexOf(prc.getName());
            float tmp = lastIndex - prc.getArrivalTime() + 1;
            n += tmp;
//            System.out.println("indx: " + index + 
//                    " arr: " + prc.getArrivalTime() + " = " + (index - prc.getArrivalTime()));
        }
        return n/size;
    }

    public float getAverageWaitingTime() {
        float n = 0;
        int size = prcList.size();
        Process prc;

        for (int i = 0; i < size; i++) {
            prc = prcList.get(i);

            int lastIndex = chart.lastIndexOf(prc.getName());
            float tmp = lastIndex - prc.getArrivalTime() - prc.getRunTime() + 1;
            n += (tmp<0?0:tmp);
//            System.out.println("indx: " + lastIndex
//                    + " arr: " + prc.getArrivalTime() + " run: " + prc.getRunTime() + " = " +  tmp);
//            System.out.println(n);
        }
        return n/size;
    }
    public float getAverageResponseTime() {
        float n = 0;
        int size = prcList.size();
        Process prc;

        for (int i = 0; i < size; i++) {
            prc = prcList.get(i);

            int firstIndex = chart.indexOf(prc.getName());
            float tmp = firstIndex - prc.getArrivalTime()+ 1;
            n += (tmp<0?0:tmp);
//            System.out.println("indx: " + firstIndex
//                    + " arr: " + prc.getArrivalTime() + " = " +  tmp);
//            System.out.println(n);
        }
        return n/size;
    }
}
