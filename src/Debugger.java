package src;

import java.util.Arrays;

public class Debugger {


//    public  void performRotations(CostPerUnitCell[][] matrix) {
//        System.out.println("\nClockwise Rotations:");
//        performClockwiseRotations(matrix);
//
//        System.out.println("\nCounter-clockwise Rotations:");
//        performCounterClockwiseRotations(matrix);
//
//        System.out.println("\nRow-wise Rotations:");
//        performRowRotations(matrix);
//
//        System.out.println("\nColumn-wise Rotations:");
//        performColumnRotations(matrix);
//    }
//
//    public  void performClockwiseRotations(CostPerUnitCell[][] matrix) {
//        int n = matrix.length;
//
//        for (int i = 0; i < 4; i++) {
//            System.out.println("\nRotation " + (i+1) + ":");
//            printMatrix(matrix);
//            matrix = rotateMatrixClockwise(matrix, n);
//        }
//    }
//
//    public  void performCounterClockwiseRotations(CostPerUnitCell[][] matrix) {
//        int n = matrix.length;
//
//        for (int i = 0; i < 4; i++) {
//            System.out.println("\nRotation " + (i+1) + ":");
//            printMatrix(matrix);
//            matrix = rotateMatrixCounterClockwise(matrix, n);
//        }
//    }
//
//    public  void performRowRotations(CostPerUnitCell[][] matrix) {
//        int n = matrix.length;
//
//        for (int i = 0; i < n; i++) {
//            System.out.println("\nRow Rotation " + (i+1) + ":");
//            printMatrix(matrix);
//            matrix = rotateMatrixRow(matrix, i);
//        }
//    }
//
//    public  void performColumnRotations(CostPerUnitCell[][] matrix) {
//        int n = matrix.length;
//
//        for (int i = 0; i < n; i++) {
//            System.out.println("\nColumn Rotation " + (i+1) + ":");
//            printMatrix(matrix);
//            matrix = rotateMatrixColumn(matrix, i);
//        }
//    }
//
//    public  CostPerUnitCell[][] rotateMatrixClockwise(CostPerUnitCell[][] matrix, int n) {
//        CostPerUnitCell[][] rotatedMatrix = new CostPerUnitCell[n][n];
//
//        for (int i = 0; i < n; i++) {
//            for (int j = 0; j < n; j++) {
//                rotatedMatrix[j][n - 1 - i] = matrix[i][j];
//            }
//        }
//
//        return rotatedMatrix;
//    }
//
//    public  CostPerUnitCell[][] rotateMatrixCounterClockwise(CostPerUnitCell[][] matrix, int n) {
//        CostPerUnitCell[][] rotatedMatrix = new CostPerUnitCell[n][n];
//
//        for (int i = 0; i < n; i++) {
//            for (int j = 0; j < n; j++) {
//                rotatedMatrix[n - 1 - j][i] = matrix[i][j];
//            }
//        }
//
//        return rotatedMatrix;
//    }
//
//    public  CostPerUnitCell[][] rotateMatrixRow(CostPerUnitCell[][] matrix, int rowIndex) {
//        int n = matrix.length;
//        CostPerUnitCell[][] rotatedMatrix = new CostPerUnitCell[n][n];
//
//        for (int i = 0; i < n; i++) {
//            rotatedMatrix[i] = matrix[(i + rowIndex) % n];
//        }
//
//        return rotatedMatrix;
//    }
//
//    public  CostPerUnitCell[][] rotateMatrixColumn(CostPerUnitCell[][] matrix, int colIndex) {
//        int n = matrix.length;
//        CostPerUnitCell[][] rotatedMatrix = new CostPerUnitCell[n][n];
//
//        for (int i = 0; i < n; i++) {
//            for (int j = 0; j < n; j++) {
//                rotatedMatrix[i][j] = matrix[i][(j + colIndex) % n];
//            }
//        }
//
//        return rotatedMatrix;
//    }
//    public  void printMatrix(CostPerUnitCell[][] matrix) {
//        for(CostPerUnitCell[] i:matrix){
//            for(CostPerUnitCell j:i){
//                System.out.print(j.allocation+"|"+j.cpu+"\t");
//            }
//            System.out.println();
//        }
//    }
    public void Cpu_printer(CostPerUnitCell[][] matrix){
        for(CostPerUnitCell[] i:matrix){
            for(CostPerUnitCell j:i){
                System.out.print(j.allocation+"|"+j.cpu+"\t");
            }
            System.out.println();
        }

    }
}
