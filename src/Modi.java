package src;

import java.util.ArrayList;
import java.util.List;

public class Modi {
    int n;
    CostPerUnitCell[][] matrix;
    double[] supply;
    double[] demand;
    public Modi(int n, CostPerUnitCell[][] matrix, double[] supply, double[] demand) {
        this.n = n;
        this.matrix = matrix;
        this.supply = supply;
        this.demand = demand;
    }
    public CostPerUnitCell[][] getMatrix() {
        return matrix;
    }
//    public boolean compute(){
//        try{
//            return true;
//        }
//        catch(Exception e){
//            return false;
//        }
//    }
public boolean compute() {
    try {
        System.out.println("INSIDE COMPUTE FUNCTION");
        // Initialize the u and v arrays
        double[] u = new double[n];
        double[] v = new double[n];
        u[0] = 0; // Set the first value of u to 0

        // Compute the u and v arrays
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (matrix[i][j].allocation > 0) {
                    if (u[i] == 0 && v[j] == 0) {
                        u[i] = matrix[i][j].cpu;
                    } else if (u[i] != 0 && v[j] == 0) {
                        v[j] = matrix[i][j].cpu - u[i];
                    } else if (u[i] == 0 && v[j] != 0) {
                        u[i] = matrix[i][j].cpu - v[j];
                    }
                }
            }
        }

        // Compute the opportunity costs
        double[][] opportunityCosts = new double[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (matrix[i][j].allocation == 0) {
                    opportunityCosts[i][j] = matrix[i][j].cpu - u[i] - v[j];
                }
            }
        }

        // Find the cell with the largest opportunity cost
        int maxI = -1;
        int maxJ = -1;
        double maxOpportunityCost = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (opportunityCosts[i][j] > maxOpportunityCost) {
                    maxI = i;
                    maxJ = j;
                    maxOpportunityCost = opportunityCosts[i][j];
                }
            }
        }

        // If there are no cells with positive opportunity costs, we're done
        if (maxOpportunityCost <= 0) {
            System.out.println("maxOpportunityCost <= 0");
            return true;
        }

        // Compute the cycle
        List<int[]> cycle = new ArrayList<>();
        cycle.add(new int[]{maxI, maxJ});
        boolean[] visitedRows = new boolean[n];
        boolean[] visitedCols = new boolean[n];
        visitedRows[maxI] = true;
        visitedCols[maxJ] = true;
        boolean foundCycle = false;
        while (!foundCycle) {
            // Find the row in the cycle that matches the current column
            int row = -1;
            for (int i = 0; i < cycle.size(); i++) {
                if (cycle.get(i)[1] == maxJ) {
                    row = cycle.get(i)[0];
                    break;
                }
            }

            // Add the next cell to the cycle
            if (row != -1) {
                for (int j = 0; j < n; j++) {
                    if (matrix[row][j].allocation > 0 && !visitedCols[j]) {
                        cycle.add(new int[]{row, j});
                        visitedCols[j] = true;
                        break;
                    }
                }
            } else {
                // Find the column in the cycle that matches the current row
                int col = -1;
                for (int i = 0; i < cycle.size(); i++) {
                    if (cycle.get(i)[0] == maxI) {
                        col = cycle.get(i)[1];
                        break;
                    }
                }

                // Add the next cell to the cycle
                for (int i = 0; i < n; i++) {
                    if (matrix[i][col].allocation > 0 && !visitedRows[i]) {
                        cycle.add(new int[]{i, col});
                        visitedRows[i] = true;
                        break;
                    }
                }
            }

            // Check if we've closed the cycle
            if (cycle.get(0)[0] == cycle.get(cycle.size() - 1)[0] && cycle.get(0)[1] == cycle.get(cycle.size() - 2)[1]) {
                System.out.println("Cycle: " + cycle);
                foundCycle = true;
            }
        }

        // Compute the minimum allocation in the cycle
        double minAllocation = Double.MAX_VALUE;
        for (int i = 0; i < cycle.size(); i++) {
            if (matrix[cycle.get(i)[0]][cycle.get(i)[1]].allocation < minAllocation) {
                minAllocation = matrix[cycle.get(i)[0]][cycle.get(i)[1]].allocation;
            }
        }

        // Update the allocations in the cycle
        for (int i = 0; i < cycle.size(); i++) {
            int row = cycle.get(i)[0];
            int col = cycle.get(i)[1];
            if (i % 2 == 0) {
                matrix[row][col].allocation += minAllocation;
            } else {
                matrix[row][col].allocation -= minAllocation;
            }
        }

        // Recurse to find the next cycle
        return compute();
    } catch (Exception e) {
        return false;
    }
}

}
