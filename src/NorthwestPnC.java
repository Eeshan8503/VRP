package src;

public class NorthwestPnC {
    double cost;

    public NorthwestPnC() {
        this.cost = 0;
    }

    public void performRotations(CostPerUnitCell[][] matrix, double[] supply, double[] demand) {
        System.out.println("\nClockwise Rotations:");
        performClockwiseRotations(matrix, supply, demand);

        System.out.println("\nCounter-clockwise Rotations:");
        performCounterClockwiseRotations(matrix, supply, demand);

        System.out.println("\nRow-wise Rotations:");
        performRowRotations(matrix, supply, demand);

        System.out.println("\nColumn-wise Rotations:");
        performColumnRotations(matrix, supply, demand);
    }

    public void performClockwiseRotations(CostPerUnitCell[][] matrix, double[] supply, double[] demand) {
        int n = matrix.length;

        for (int i = 0; i < 4; i++) {
            System.out.println("\nRotation " + (i+1) + ":");
            printMatrix(matrix, supply, demand);
            matrix = rotateMatrixClockwise(matrix, n);
            supply = rotateArrayClockwise(supply, n);
            demand = rotateArrayClockwise(demand, n);
        }
    }

    public void performCounterClockwiseRotations(CostPerUnitCell[][] matrix, double[] supply, double[] demand) {
        int n = matrix.length;

        for (int i = 0; i < 4; i++) {
            System.out.println("\nRotation " + (i+1) + ":");
            printMatrix(matrix, supply, demand);
            matrix = rotateMatrixCounterClockwise(matrix, n);
            supply = rotateArrayCounterClockwise(supply, n);
            demand = rotateArrayCounterClockwise(demand, n);
        }
    }

    public void performRowRotations(CostPerUnitCell[][] matrix, double[] supply, double[] demand) {
        int n = matrix.length;

        for (int i = 0; i < n; i++) {
            System.out.println("\nRow Rotation " + (i+1) + ":");
            printMatrix(matrix, supply, demand);
            matrix = rotateMatrixRow(matrix, i);
            supply = rotateArrayRow(supply, i);
        }
    }

    public void performColumnRotations(CostPerUnitCell[][] matrix, double[] supply, double[] demand) {
        int n = matrix.length;

        for (int i = 0; i < n; i++) {
            System.out.println("\nColumn Rotation " + (i+1) + ":");
            printMatrix(matrix, supply, demand);
            matrix = rotateMatrixColumn(matrix, i);
            demand = rotateArrayColumn(demand, i);
        }
    }

    public CostPerUnitCell[][] rotateMatrixClockwise(CostPerUnitCell[][] matrix, int n) {
        CostPerUnitCell[][] rotatedMatrix = new CostPerUnitCell[n][n];

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                rotatedMatrix[j][n - 1 - i] = matrix[i][j];
            }
        }

        return rotatedMatrix;
    }

    public CostPerUnitCell[][] rotateMatrixCounterClockwise(CostPerUnitCell[][] matrix, int n) {
        CostPerUnitCell[][] rotatedMatrix = new CostPerUnitCell[n][n];

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                rotatedMatrix[n - 1 - j][i] = matrix[i][j];
            }
        }

        return rotatedMatrix;
    }

    public CostPerUnitCell[][] rotateMatrixRow(CostPerUnitCell[][] matrix, int rowIndex) {
        int n = matrix.length;
        CostPerUnitCell[][] rotatedMatrix = new CostPerUnitCell[n][n];

        for (int i = 0; i < n; i++) {
            rotatedMatrix[i] = matrix[(i + rowIndex) % n];
        }

        return rotatedMatrix;
    }

    public CostPerUnitCell[][] rotateMatrixColumn(CostPerUnitCell[][] matrix, int colIndex) {
        int n = matrix.length;
        CostPerUnitCell[][] rotatedMatrix = new CostPerUnitCell[n][n];

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                rotatedMatrix[i][j] = matrix[i][(j + colIndex) % n];
            }
        }

        return rotatedMatrix;
    }

    public double[] rotateArrayClockwise(double[] array, int n) {
        double[] rotatedArray = new double[n];

        for (int i = 0; i < n; i++) {
            rotatedArray[(i + 1) % n] = array[i];
        }

        return rotatedArray;
    }

    public double[] rotateArrayCounterClockwise(double[] array, int n) {
        double[] rotatedArray = new double[n];

        for (int i = 0; i < n; i++) {
            rotatedArray[i] = array[(i + 1) % n];
        }

        return rotatedArray;
    }

    public double[] rotateArrayRow(double[] array, int rowIndex) {
        int n = array.length;
        double[] rotatedArray = new double[n];

        for (int i = 0; i < n; i++) {
            rotatedArray[i] = array[(i + rowIndex) % n];
        }

        return rotatedArray;
    }

    public double[] rotateArrayColumn(double[] array, int colIndex) {
        int n = array.length;
        double[] rotatedArray = new double[n];

        for (int i = 0; i < n; i++) {
            rotatedArray[i] = array[i];
        }

        double temp = rotatedArray[n - 1];
        for (int i = n - 1; i > colIndex; i--) {
            rotatedArray[i] = rotatedArray[i - 1];
        }
        rotatedArray[colIndex] = temp;

        return rotatedArray;
    }

    public void printMatrix(CostPerUnitCell[][] matrix, double[] supply, double[] demand) {
        int n = matrix.length;

        System.out.print("Supply: ");
        for (int i = 0; i < n; i++) {
            System.out.print(supply[i] + "\t");
        }
        System.out.println();

        System.out.print("Demand: ");
        for (int i = 0; i < n; i++) {
            System.out.print(demand[i] + "\t");
        }
        System.out.println();

        for(int i =0;i<n;i++){
            for (int j = 0; j < n; j++) {
                System.out.print(matrix[i][j].allocation + "|" + matrix[i][j].cpu + "\t");
            }
            System.out.println();
        }
    }
}



//package src;
//
//public class NorthwestPnC {
//    double cost;
//
//    public NorthwestPnC() {
//        this.cost = 0;
//    }
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
//
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
//}



