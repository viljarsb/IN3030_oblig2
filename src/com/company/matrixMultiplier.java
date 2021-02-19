package com.company;

import java.util.Arrays;
import java.util.concurrent.CyclicBarrier;

public class matrixMultiplier {

    private static double[][] matrixA, matrixB;
    private static double resultMatrix[][];
    private static CyclicBarrier barrier;

    public static double[][] multiplyMatrixes(double[][] a, double[][] b, Oblig2Precode.Mode modeOfOperation)
    {

        if(modeOfOperation.equals(Oblig2Precode.Mode.SEQ_NOT_TRANSPOSED)){
            matrixA = a;
            matrixB = b;
            resultMatrix = new double[matrixA.length][matrixB[0].length];
            return classicAlgorithm();
        }

        else if(modeOfOperation.equals(Oblig2Precode.Mode.SEQ_A_TRANSPOSED)){
            matrixA = transposeMatrix(a);
            matrixB = b;
            resultMatrix = new double[matrixA.length][matrixB[0].length];
            return classicAlgorithm();
        }

        else if(modeOfOperation.equals(Oblig2Precode.Mode.SEQ_B_TRANSPOSED)){
            matrixA = a;
            matrixB = transposeMatrix(b);
            resultMatrix = new double[matrixA.length][matrixB[0].length];
            return classicAlgorithm();
        }

        else if(modeOfOperation.equals(Oblig2Precode.Mode.PARA_NOT_TRANSPOSED))
        {
            matrixA = a;
            matrixB = b;
            resultMatrix = new double[matrixA.length][matrixB[0].length];
            return parallelAlgorithm();
        }

        else if(modeOfOperation.equals(Oblig2Precode.Mode.PARA_B_TRANSPOSED));

        else if(modeOfOperation.equals(Oblig2Precode.Mode.PARA_A_TRANSPOSED));

        else{
            System.out.println("No mode of operation supplied.");
            System.out.println("Supported modes of operation are: " + Arrays.asList(Oblig2Precode.Mode.values()));
            System.exit(-1);
        }

        return null;
    }

    private static double[][] classicAlgorithm(){
        int a_col = matrixA[0].length;
        int b_row = matrixB.length;

        //Can these matrixes be multiplied?
        if(a_col != b_row){
            System.out.println("Can not multiply these matrixes");
            System.exit(-1);
        }

        for(int i = 0; i < resultMatrix.length; i++){
            for(int j = 0; j < resultMatrix[i].length; j++){
                multiplyCells(i, j);
            }
        }

        return resultMatrix;
    }

    private static double[][] parallelAlgorithm() {
        int numberOfProcessors = 8;//Runtime.getRuntime().availableProcessors();
        barrier = new CyclicBarrier(numberOfProcessors + 1);

        //amount of cells each thread has to work on.
        int partitonSize = (matrixA.length * matrixB[0].length) / numberOfProcessors;
        int modRes = (matrixA.length * matrixB[0].length) % numberOfProcessors;

        int currentCol = 0;
        int endCol = currentCol + partitonSize;
        int currentRow = 0;
        int endRow = 0;

        for(int i = 0; i < numberOfProcessors; i++){

            while(endCol > resultMatrix[0].length)
            {
                endRow = endRow + 1;
                endCol = endCol - resultMatrix[0].length;
            }

            System.out.println(" IM WORKING FROM INDEX: " + currentCol + " ON ROW " + currentRow + " to INDEX " + endCol + " ON ROW " + endRow);
            new Thread(new Worker(i, currentCol, currentRow, endCol, endRow)).start();


            currentCol = endCol;
            currentRow = endRow;
            endCol = endCol + partitonSize;
        }




        try{
            barrier.await();
        }
        catch (Exception e) {
            System.out.println(e);
        }

        return resultMatrix;
    }






    private static class Worker implements Runnable {
        int id;
        int startCol, startRow, endCol, endRow;

        public Worker(int id, int startCol, int startRow, int endCol, int endRow) {
            this.id = id;
            this.startCol = startCol;
            this.endCol = endCol;
            this.startRow = startRow;
            this.endRow = endRow;

        }

        @Override
        public void run() {
           // System.out.println("HEY IM THREAD: " + id + " IM WORKING FROM INDEX: " + startCol + " ON ROW " + startRow + " to INDEX " + endCol + " ON ROW " + endRow);

            int toCol;
            if(startRow != endRow)
                toCol = resultMatrix[0].length;
            else
                toCol = endCol;

            for (int i = startRow; i <= endRow; i++) {
                if(i == endRow)
                    toCol = endCol;

                for (int j = startCol; j < toCol; j++) {
                    multiplyCells(i, j);
                }
            }



            try{
                barrier.await();
            }
            catch (Exception e) {
                System.out.println(e);
            }
        }
    }

    private static void multiplyCells(int row, int col) {
        double cellResult = 0;
        for(int j = 0; j < matrixB.length; j++){
            cellResult = cellResult + matrixA[row][col] * matrixB[row][col];
        }
        resultMatrix[row][col] = cellResult;
    }


    private static double[][] transposeMatrix(double[][] matrix){
        int i = matrix.length;
        int j = matrix[0].length;

        double[][] transposedMatrix = new double[j][i];
        for(int x = 0; x < j; x++){
            for(int y = 0; y < j; y++)
            {
                transposedMatrix[x][y] = matrix[y][x];
            }
        }

        return transposedMatrix;
    }
}
