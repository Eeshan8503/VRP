package src;

public class CostPerUnitMatrix {
    long n;
    CostPerUnitCell[][] matrix;
    double []supply, demand;

    public CostPerUnitMatrix(long n, CostPerUnitCell[][] matrix, double[] supply, double[] demand) {
        this.n = n;
        this.matrix = matrix;
        this.supply = supply;
        this.demand = demand;
    }
}
