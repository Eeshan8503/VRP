package src;

import java.util.Arrays;

public class VAM {
    CostPerUnitCell[][] matrix;
    double[] supply;
    double[] demand;
    int n;

    public VAM(CostPerUnitCell[][] matrix, double[] supply, double[] demand, int n) {
        this.matrix = matrix;
        this.supply = supply;
        this.demand = demand;
        this.n = n;
        solve(matrix, supply, demand, n);
    }

    public double compute(CostPerUnitCell[][] matrix) {
        double result = 0;
        for (CostPerUnitCell[] i : matrix) {
            for (CostPerUnitCell j : i) {
                result += j.cpu * j.allocation;
            }
        }
        return result;
    }

    public void solve(CostPerUnitCell[][] matrix, double[] supply, double[] demand, int n) {
        // Initialize variables
        double[] u = new double[n];
        double[] v = new double[n];
        boolean[] rowCovered = new boolean[n];
        boolean[] colCovered = new boolean[n];
        int numAllocated = 0;

        // Compute initial feasible solution
        while (numAllocated < n) {
            // Find uncovered cell with minimum cost
            double minCost = Double.POSITIVE_INFINITY;
            int minRow = -1;
            int minCol = -1;
            for (int i = 0; i < n; i++) {
                if (!rowCovered[i]) {
                    for (int j = 0; j < n; j++) {
                        if (!colCovered[j]) {
                            double cost = matrix[i][j].cpu;
                            if (cost < minCost) {
                                minCost = cost;
                                minRow = i;
                                minCol = j;
                            }
                        }
                    }
                }
            }

            // Allocate as much as possible to the uncovered cell
            double allocation = Math.min(supply[minRow], demand[minCol]);
            matrix[minRow][minCol].allocation = allocation;
            supply[minRow] -= allocation;
            demand[minCol] -= allocation;
            if (supply[minRow] == 0) {
                rowCovered[minRow] = true;
            }
            if (demand[minCol] == 0) {
                colCovered[minCol] = true;
            }
            if (rowCovered[minRow] && !colCovered[minCol]) {
                colCovered[minCol] = true;
                numAllocated++;
            } else if (!rowCovered[minRow] && colCovered[minCol]) {
                rowCovered[minRow] = true;
                numAllocated++;
            }
        }

        // Improve solution using VAM
        while (true) {
            // Compute dual variables
            Arrays.fill(u, Double.POSITIVE_INFINITY);
            Arrays.fill(v, Double.POSITIVE_INFINITY);
            u[0] = 0;
            for (int i = 0; i < n; i++) {
                if (rowCovered[i]) {
                    for (int j = 0; j < n; j++) {
                        if (!colCovered[j]) {
                            double reducedCost = matrix[i][j].cpu - u[i] - v[j];
                            if (reducedCost < v[j]) {
                                v[j] = reducedCost;
                            }
                        }
                    }
                }
            }
            for (int j = 0; j < n; j++) {
                if (colCovered[j]) {
                    for (int i = 0; i < n; i++) {
                        if (!rowCovered[i]) {
                            double reducedCost = matrix[i][j].cpu - u[i] - v[j];
                            if (reducedCost < u[i]) {
                                u[i] = reducedCost;
                            }
                        }
                    }
                }
            }

            // Find uncovered cell with maximum reduced cost
            double maxReducedCost = Double.NEGATIVE_INFINITY;
            int maxRow = -1;
            int maxCol = -1;
            for (int i = 0; i < n; i++) {
                if (!rowCovered[i]) {
                    for (int j = 0; j < n; j++) {
                        if (!colCovered[j]) {
                            double reducedCost = matrix[i][j].cpu - u[i] - v[j];
                            if (reducedCost > maxReducedCost) {
                                maxReducedCost = reducedCost;
                                maxRow = i;
                                maxCol = j;
                            }
                        }
                    }
                }
            }

            // If all reduced costs are non-positive, we have an optimal solution
            if (maxReducedCost <= 0) {
                break;
            }

            // Allocate as much as possible to the uncovered cell
            double allocation = Math.min(supply[maxRow], demand[maxCol]);
            matrix[maxRow][maxCol].allocation += allocation;
            if (maxRow != -1 && maxCol != -1) {
                supply[maxRow] -= allocation;
                demand[maxCol] -= allocation;
                if (supply[maxRow] == 0) {
                    rowCovered[maxRow] = true;
                }
                if (demand[maxCol] == 0) {
                    colCovered[maxCol] = true;
                }
            }
        }
        FinalCost finalCost = new FinalCost(compute(matrix));
        System.out.println("Cost after VAM "+finalCost.getFinalCost());
    }
}
