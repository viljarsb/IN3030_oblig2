package com.company;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class Main {

    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]);
        runTests(n, 103, Oblig2Precode.Mode.PARA_NOT_TRANSPOSED);
        runTests(n, 103, Oblig2Precode.Mode.PARA_A_TRANSPOSED);
        runTests(n, 103, Oblig2Precode.Mode.PARA_B_TRANSPOSED);
        runTests(n, 103, Oblig2Precode.Mode.SEQ_NOT_TRANSPOSED);
        runTests(n, 103, Oblig2Precode.Mode.SEQ_A_TRANSPOSED);
        runTests(n, 103, Oblig2Precode.Mode.SEQ_B_TRANSPOSED);
    }

    private static void runTests(int n, int seed, Oblig2Precode.Mode modeOfOperation)
    {
        Oblig2Precode manager = new Oblig2Precode();
        double[][] matrixA = manager.generateMatrixA(seed, n);
        double[][] matrixB = manager.generateMatrixB(seed, n);

        ArrayList<Long> timing = new ArrayList<Long>();

        for(int i = 0; i < 7; i++)
        {
            long start = System.nanoTime();
            double[][] resultMatrix = matrixMultiplier.multiplyMatrixes(matrixA, matrixB, modeOfOperation);
            long end = System.nanoTime();
            timing.add((end-start)/100000);
            System.out.println(modeOfOperation + " run: " + (i + 1) + " used: " + (end-start)/100000 + " ms");
        }
        System.out.println(modeOfOperation + " had a median run time of: " + calculateMedian(timing) + " milliseconds over: " + 7 + " executions");
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
