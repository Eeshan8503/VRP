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

    public static double computeCost(CostPerUnitCell[][] matrix){
        double result=0;
        for(CostPerUnitCell[] i :matrix){
            for(CostPerUnitCell j:i){
                result+=j.cpu*j.allocation;
            }
        }
        return result;
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
        // Initialize the u and v arrays
        double[] u = new double[n];
        double[] v = new double[n];
        u[0] = 0; // Set the first element of u to 0

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

        // If there are no more cells with positive opportunity costs, we are done
        if (maxI == -1 && maxJ == -1) {
            FinalCost finalCost = new FinalCost(computeCost(matrix));
            System.out.println("Cost after MODI: "+finalCost.getFinalCost());
            return true;
        }

        // Find the cycle starting from the cell with the largest opportunity cost
        List<int[]> cycle = new ArrayList<>();
        cycle.add(new int[]{maxI, maxJ});
        boolean[] visitedRows = new boolean[n];
        boolean[] visitedCols = new boolean[n];
        visitedRows[maxI] = true;
        visitedCols[maxJ] = true;
        boolean foundCycle = false;
        while (!foundCycle) {
            int[] lastCell = cycle.get(cycle.size() - 1);
            int row = lastCell[0];
            int col = lastCell[1];
            for (int j = 0; j < n; j++) {
                if (j != col && matrix[row][j].allocation > 0 && !visitedCols[j]) {
                    cycle.add(new int[]{row, j});
                    visitedCols[j] = true;
                    break;
                }
            }
            lastCell = cycle.get(cycle.size() - 1);
            row = lastCell[0];
            col = lastCell[1];
            for (int i = 0; i < n; i++) {
                if (i != row && matrix[i][col].allocation > 0 && !visitedRows[i]) {
                    cycle.add(new int[]{i, col});
                    visitedRows[i] = true;
                    break;
                }
            }
            if (cycle.get(0)[0] == cycle.get(cycle.size() - 1)[0] && cycle.get(0)[1] == cycle.get(cycle.size() - 2)[1]) {
                foundCycle = true;
            }
        }

        // Find the minimum allocation in the cycle
        double minAllocation = Double.MAX_VALUE;
        for (int i = 0; i < cycle.size(); i++) {
            int[] cell = cycle.get(i);
            if (i % 2 == 0) {
                minAllocation = Math.min(minAllocation, matrix[cell[0]][cell[1]].allocation);
            } else {
                minAllocation = Math.min(minAllocation, matrix[cell[0]][cell[1]].allocation - minAllocation);
            }
        }

        // Update the allocations in the cycle
        for (int i = 0; i < cycle.size(); i++) {
            int[] cell = cycle.get(i);
            if (i % 2 == 0) {
                matrix[cell[0]][cell[1]].allocation -= minAllocation;
            } else {
                matrix[cell[0]][cell[1]].allocation += minAllocation;
            }
        }

        // Recurse to find the next cycle
        return compute();
    } catch (Exception e) {
        return false;
    }
}
}
