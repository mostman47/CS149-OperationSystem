
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author mr.nam
 */
public class Algorithm {

    private String currentAlgorithm;
    private final String firstComeFirstServed = "FCFS";
    private final String shortestJobFirst = "SJF";
    private final String shortestRemainingTime = "SRT";
    private final String roundRobin = "RR";
    private final String HighestPriorityFirst_NonPree = "HPF_NP";
    private final String HighestPriorityFirst_Pree = "HPF_P";

    public Algorithm() {
        currentAlgorithm = firstComeFirstServed;
    }

    public String getCurrentAlgorithm() {
        return currentAlgorithm;
    }

    public void setCurrentAlgorithm(String currentAlgorithm) {
        this.currentAlgorithm = currentAlgorithm;
    }

    public String getFirstComeFirstServed() {
        return firstComeFirstServed;
    }

    public String getShortestJobFirst() {
        return shortestJobFirst;
    }

    public String getShortestRemainingTime() {
        return shortestRemainingTime;
    }

    public String getRoundRobin() {
        return roundRobin;
    }

    public String getHighestPriorityFirst_NonPree() {
        return HighestPriorityFirst_NonPree;
    }

    public String getHighestPriorityFirst_Pree() {
        return HighestPriorityFirst_Pree;
    }

    public void checkGenerate(ArrayList<Process> prcList, int maxTL) {
        sortByArrivalTime(prcList);
        int size = prcList.size();
        int time = 0;
        int i = 0;
        int count = 0;
        Process prc = null;
        Queue<Process> queue = new LinkedList<>();
        while (i < size || !queue.isEmpty()) {
//            if(time%10 == 0)
//            {
//                System.out.println("time: " + time + " i: " + i);
//            }
            if (time == 100) {
                //System.out.println("i: " + i);

                if (count > 0) {
                    queue.poll();
                }
                while (!queue.isEmpty()) {
                    prcList.remove(queue.poll());
                    //         System.out.println("remove");
                }
                break;
            }
            while (i < size && (int) prcList.get(i).getArrivalTime() == time) {
                queue.add(prcList.get(i));
                i++;
            }
            if (!queue.isEmpty()) {
                prc = queue.peek();
                count++;
                if (count == prc.getRunTime()) {
                    queue.poll();
                    count = 0;
                }
            }
            time++;
        }
    }

    public String SJF(ArrayList<Process> prcList) {
        String s = "";
        //
        List<Process> queue = new ArrayList<>();
        Process prc = null;
        int time = 0;
        int i = 0;
        int size = prcList.size();
        while (i < size || !queue.isEmpty()) {
            while (i < size && (int) prcList.get(i).getArrivalTime() == time) {
                prc = prcList.get(i);
                Process tmp = new Process(prc.getName(), prc.getArrivalTime(), prc.getRunTime(), prc.getPriority());
                queue.add(tmp);
                
                i++;
            }
            if (!queue.isEmpty()) {
                prc = queue.get(0);
                s += prc.getName();
                prc.setRunTime(prc.getRunTime() - 1);
                if (prc.getRunTime() == 0) {
                    queue.remove(0);
                    sortByRunTime(queue);                    
                }
            } else {
                s += "_";
            }
            time++;
        }
        //
        return s;
    }

    public String FCFS(ArrayList<Process> prcList) {
        String s = "";
        int size = prcList.size();
        int time = 0;
        int i = 0;
        int count = 0;
        Process prc = null;
        Queue<Process> queue = new LinkedList<>();
        while (i < size || !queue.isEmpty()) {
            while (i < size && (int) prcList.get(i).getArrivalTime() == time) {
                queue.add(prcList.get(i));
                i++;
            }
            if (!queue.isEmpty()) {
                prc = queue.peek();
                s += prc.getName();
                count++;
                if (count == prc.getRunTime()) {
                    queue.poll();
                    count = 0;
                }
            } else {
                s += "_";
            }
            time++;
        }
        return s;
    }

    public void sortByArrivalTime(List<Process> prcList) {
        Process min;
        Process tmp;
        int length = prcList.size();
        for (int i = 0; i < length; i++) {
            min = prcList.get(i);
            for (int j = i + 1; j < length; j++) {
                tmp = prcList.get(j);
                if (tmp.getArrivalTime() < min.getArrivalTime()) {
                    prcList.set(i, tmp);
                    prcList.set(j, min);
                    min = tmp;
                }
            }
        }
    }

    public void sortByRunTime(List<Process> prcList) {
        Process min;
        Process tmp;
        int length = prcList.size();
        for (int i = 0; i < length; i++) {
            min = prcList.get(i);
            for (int j = i + 1; j < length; j++) {
                tmp = prcList.get(j);
                if (tmp.getRunTime() < min.getRunTime()) {
                    prcList.set(i, tmp);
                    prcList.set(j, min);
                    min = tmp;
                }
            }
        }
    }

    public String applyAlg(ArrayList<Process> prcList, int timeSlice) {
        sortByArrivalTime(prcList);

        if (currentAlgorithm == firstComeFirstServed) {
            return FCFS(prcList);
        } else if (currentAlgorithm == shortestJobFirst) {
            return SJF(prcList);
        } else if (currentAlgorithm == HighestPriorityFirst_NonPree) {
            return HPF_NP(prcList);
        } else if (currentAlgorithm == roundRobin) {
            return RR(prcList, timeSlice);
        } else if (currentAlgorithm == HighestPriorityFirst_Pree)
        {
            return HPF_P(prcList);
        } else if (currentAlgorithm == shortestRemainingTime)
        {
            return SRT(prcList);

        }
        return "";
    }

    private String HPF_NP(ArrayList<Process> prcList) {
        String s = "";
        //
        List<Process> queue = new ArrayList<>();
        Process prc = null;
        int time = 0;
        int i = 0;
        int size = prcList.size();
        while (i < size || !queue.isEmpty()) {
            while (i < size && (int) prcList.get(i).getArrivalTime() == time) {
                prc = prcList.get(i);
                Process tmp = new Process(prc.getName(), prc.getArrivalTime(), prc.getRunTime(), prc.getPriority());
                queue.add(tmp);
                i++;
            }
            if (!queue.isEmpty()) {
                prc = queue.get(0);
                s += prc.getName();
                prc.setRunTime(prc.getRunTime() - 1);
                if (prc.getRunTime() == 0) {
                    queue.remove(0);
                    sortByPriority(queue);
                }
            } else {
                s += "_";
            }
            time++;
        }
        //
        //System.out.println(s.length());
        return s;
    }

    private void sortByPriority(List<Process> prcList) {
        Process min;
        Process tmp;
        int length = prcList.size();
        for (int i = 0; i < length; i++) {
            min = prcList.get(i);
            for (int j = i + 1; j < length; j++) {
                tmp = prcList.get(j);
                if (tmp.getPriority() < min.getPriority()) {
                    prcList.set(i, tmp);
                    prcList.set(j, min);
                    min = tmp;
                }
            }
        }
    }

    private String RR(ArrayList<Process> prcList, int timeSlice) {
        String s = "";
        //
        Queue<Process> queue = new LinkedList<>();
        Process prc = null;
        int time = 0;
        int i = 0;
        int size = prcList.size();
        while (i < size || !queue.isEmpty()) {
            while (i < size && (int) prcList.get(i).getArrivalTime() <= time) {
                prc = prcList.get(i);
                Process tmp = new Process(prc.getName(), prc.getArrivalTime(), prc.getRunTime(), prc.getPriority());
                queue.add(tmp);
                i++;
            }
            if (!queue.isEmpty()) {
                prc = queue.poll();
                for (int j = 0; j < timeSlice; j++) {

                    if (prc.getRunTime() > 0) {
                        time++;
                        s += prc.getName();
                        prc.setRunTime(prc.getRunTime() - 1);
                    } else {
                        break;
                    }
                }
                time--;
                if (prc.getRunTime() > 0) {
                    queue.add(prc);
                }
            } else {
                s += "_";
            }
            time++;
        }
        //
        //System.out.println(s.length());
        return s;
    }

    private String HPF_P(ArrayList<Process> prcList) {
       String s = "";
        //
        List<Process> queue = new ArrayList<>();
        Process prc = null;
        int time = 0;
        int i = 0;
        int size = prcList.size();
        while (i < size || !queue.isEmpty()) {
            while (i < size && (int) prcList.get(i).getArrivalTime() == time) {
                prc = prcList.get(i);
                Process tmp = new Process(prc.getName(), prc.getArrivalTime(), prc.getRunTime(), prc.getPriority());
                queue.add(tmp);
                sortByPriority(queue);
                i++;
            }
            if (!queue.isEmpty()) {
                prc = queue.get(0);
                s += prc.getName();
                prc.setRunTime(prc.getRunTime() - 1);
                if (prc.getRunTime() == 0) {
                    queue.remove(0);
                }
            } else {
                s += "_";
            }
            time++;
        }
        //
        //System.out.println(s.length());
        return s;
    }

    private String SRT(ArrayList<Process> prcList) {
         String s = "";
        //
        List<Process> queue = new ArrayList<>();
        Process prc = null;
        int time = 0;
        int i = 0;
        int size = prcList.size();
        while (i < size || !queue.isEmpty()) {
            while (i < size && (int) prcList.get(i).getArrivalTime() == time) {
                prc = prcList.get(i);
                Process tmp = new Process(prc.getName(), prc.getArrivalTime(), prc.getRunTime(), prc.getPriority());
                queue.add(tmp);
                sortByRunTime(queue);
                i++;
            }
            if (!queue.isEmpty()) {
                prc = queue.get(0);
                s += prc.getName();
                prc.setRunTime(prc.getRunTime() - 1);
                if (prc.getRunTime() == 0) {
                    queue.remove(0);
                }
            } else {
                s += "_";
            }
            time++;
        }
        //
        return s;
    }
}
