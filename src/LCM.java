package src;

public class LCM {
    CostPerUnitCell[][] matrix;
    double[] supply;
    double[] demand;
    int n;

    public LCM(CostPerUnitCell[][] matrix, double[] supply, double[] demand) {
        this.matrix = matrix;
        this.supply = supply;
        this.demand = demand;
        this.n = matrix.length;
    }
}
