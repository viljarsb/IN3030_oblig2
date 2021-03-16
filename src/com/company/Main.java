package com.company;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class Main {
    static int trials = 1;
    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]);
        System.out.println("Program starting execution with N: " + n);
        System.out.println("Starting tests now..\n");
        runTests(n, trials, 7035, Oblig2Precode.Mode.PARA_NOT_TRANSPOSED);
        runTests(n, trials, 7035, Oblig2Precode.Mode.SEQ_NOT_TRANSPOSED);
        runTests(n, trials, 7035, Oblig2Precode.Mode.PARA_A_TRANSPOSED);
        runTests(n, trials, 7035, Oblig2Precode.Mode.SEQ_A_TRANSPOSED);
        runTests(n, trials, 7035, Oblig2Precode.Mode.PARA_B_TRANSPOSED);
        runTests(n, trials, 7035, Oblig2Precode.Mode.SEQ_B_TRANSPOSED);
        System.out.println("\nTerminating program.");
    }

    /*
        A simple function to run tests of the 6 algorithms implemented in matrixMultiplier.
        @Param  int size, int seed, and mode of operation.
    */
    private static void runTests(int n, int trials, int seed, Oblig2Precode.Mode modeOfOperation)
    {
        System.out.println("Starting test of: " + modeOfOperation + " and will run 7 executions. ");
        Oblig2Precode manager = new Oblig2Precode();
        double[][] matrixA = Oblig2Precode.generateMatrixA(seed, n);
        double[][] matrixB = Oblig2Precode.generateMatrixB(seed, n);

        ArrayList<Long> timing = new ArrayList<Long>();

        for(int i = 0; i < trials; i++)
        {
            long start = System.nanoTime();
            double[][] resultMatrix = matrixMultiplier.multiplyMatrixes(matrixA, matrixB, modeOfOperation);
            System.out.println(Arrays.deepToString(resultMatrix));
            long end = System.nanoTime();
            timing.add((end-start)/1000000);
            System.out.println(modeOfOperation + " run: " + (i + 1) + " used: " + (end-start)/1000000 + " ms");
        }
        System.out.println(modeOfOperation + " had a median run time of: " + calculateMedian(timing) + " milliseconds over: " + 7 + " executions\n");
    }

    private static long calculateMedian(ArrayList<Long> timing)
    {
        Collections.sort(timing);
        if(timing.size() % 2 == 1)
            return  timing.get((timing.size() + 1)/ 2 - 1);
        else
            return (timing.get(timing.size() / 2 - 1) + timing.get(timing.size() / 2)) / 2;
    }

}
