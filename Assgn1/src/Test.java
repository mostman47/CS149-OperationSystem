
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author mr.nam
 */
public class Test {

    private static float[][] finalStatis;
    private static final int algorithmNb = 6;

    public static void main(String[] args) {
        int simulationNb = 5;
        finalStatis = new float[algorithmNb][3];
        for(int i = 0; i < finalStatis.length; i++)
            for(int j = 0; j < finalStatis[0].length; j++)
            {
                finalStatis[i][j] = 0;
            }
        for (int i = 1; i <= simulationNb; i++) {
            System.out.println("****************");
            System.out.println("*Simulation # " + i + "*");
            System.out.println("****************");

            generateSimulation();
        }
        printFinalStatis(simulationNb);

    }

    private static void generateSimulation() {
        Algorithm alg = new Algorithm();
        Simulator sltor = new Simulator(alg);
        int timeSlice = 1;

        sltor.generatePrc(100);
        sltor.printProcessList();
        //FCFS
        sltor.applyAlg(0);
        printAlgorithm(sltor, "FCFS:");
        collectStatis(sltor, 0);
        //SJF
        alg.setCurrentAlgorithm(alg.getShortestJobFirst());
        sltor.applyAlg(0);
        printAlgorithm(sltor, "SJF:");
        collectStatis(sltor, 1);

        //SRT
        alg.setCurrentAlgorithm(alg.getShortestRemainingTime());
        sltor.applyAlg(0);
        printAlgorithm(sltor, "SRT:");
        collectStatis(sltor, 2);

        //HPF_NP
        alg.setCurrentAlgorithm(alg.getHighestPriorityFirst_NonPree());
        sltor.applyAlg(0);
        printAlgorithm(sltor, "HPF_NP:");
        collectStatis(sltor, 3);

        //HPF_P
        alg.setCurrentAlgorithm(alg.getHighestPriorityFirst_Pree());
        sltor.applyAlg(0);
        printAlgorithm(sltor, "HPF_P:");
        collectStatis(sltor, 4);

        //RR
        timeSlice = 1;
        alg.setCurrentAlgorithm(alg.getRoundRobin());
        sltor.applyAlg(timeSlice);
        printAlgorithm(sltor, "RR with timeSlice = " + timeSlice);
        collectStatis(sltor, 5);


    }

    private static void printAlgorithm(Simulator sltor, String name) {
        System.out.println("===Algorithm: " + name + "===");
        System.out.println("|(time chart)\t\t" + sltor.getChart());
        System.out.println("|AVE(turnaround)\t" + sltor.getAverageTurnAround());
        System.out.println("|RA(waiting time)\t" + sltor.getAverageWaitingTime());
        System.out.println("|GE(response time)\t" + sltor.getAverageResponseTime());
        System.out.println("===End Algorithm: " + name + "===\n");
    }

    private static void collectStatis(Simulator sltor, int code) {
        finalStatis[code][0] += sltor.getAverageTurnAround();
        finalStatis[code][1] += sltor.getAverageWaitingTime();
        finalStatis[code][2] += sltor.getAverageResponseTime();
        
    }

    private static void printFinalStatis(int simulationNb) {
        int count = 0;
        System.out.println("\t*****Final Statistics in " + simulationNb + (simulationNb>1?" simulations":" simulation")
                +"**********");
        printAlgorithmFinal(finalStatis[count++],"FCFS", simulationNb);
        printAlgorithmFinal(finalStatis[count++],"SJF", simulationNb);
        printAlgorithmFinal(finalStatis[count++],"SRT", simulationNb);
        printAlgorithmFinal(finalStatis[count++],"HPF_NP", simulationNb);
        printAlgorithmFinal(finalStatis[count++],"HPF_P", simulationNb);
        printAlgorithmFinal(finalStatis[count++],"RR", simulationNb);
        System.out.println("\t*****End Final Statistics in " + simulationNb + (simulationNb>1?" simulations":" simulation")
                +"**********");
    }

    private static void printAlgorithmFinal(float[] f, String name, int simulationNb) {
        System.out.println("\t===Algorithm: " + name + "===");
        System.out.println("\t|AVE(turnaround)\t" + f[0]/simulationNb);
        System.out.println("\t|RA(waiting time)\t" + f[1]/simulationNb);
        System.out.println("\t|GE(response time)\t" + f[2]/simulationNb);
        System.out.println("\t===End Algorithm: " + name + "===\n");
    }
}
