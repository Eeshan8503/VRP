package src;

public class Northwest {
    long n;
    CostPerUnitCell[][] matrix;
    double []supply, demand;

    public Northwest(long n, CostPerUnitCell[][] matrix, double[] supply, double[] demand) {
        this.n = n;
        this.matrix = matrix;
        this.supply = supply;
        this.demand = demand;
    }


    public boolean compute(){
        try{
//            locate northwest corner
//            find least of supply and demand
//            update cpu boundaries
//            reiterate until cpu boundaries go out of range

            int i=0,j=0;
//            while(i<n && j<n){
//                if(supply[i])
//            }





            return true;
        }
        catch (Exception e){
            System.out.println(e.getMessage());
            return false;
        }
    }
}
