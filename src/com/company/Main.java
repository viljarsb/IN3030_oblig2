package com.company;

import java.util.Arrays;

public class Main {

    public static void main(String[] args) {

        Oblig2Precode manager = new Oblig2Precode();

        double[][] a = manager.generateMatrixA(100, 10);
        double[][] b = manager.generateMatrixB(100, 10);

        double[][] c = matrixMultiplier.multiplyMatrixes(a, b, Oblig2Precode.Mode.PARA_NOT_TRANSPOSED);
        double[][] d = matrixMultiplier.multiplyMatrixes(a, b, Oblig2Precode.Mode.SEQ_NOT_TRANSPOSED);

        System.out.println("PARALELL");
        System.out.println(Arrays.deepToString(c));
        System.out.println("SEQ");
        System.out.println(Arrays.deepToString(d));


        for(int i = 0; i < 10; i++)
        {
            for(int j = 0; j < 10; j++)
            {
                if(c[i][j] != d[i][j])
                    System.out.println("hva faen");
            }
        }
    }
}
