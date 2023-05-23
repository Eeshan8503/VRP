package src;

import java.util.Arrays;

public class Northwest {
    long n;
    CostPerUnitCell[][] matrix;
    long []supply, demand;

    public Northwest(long n, CostPerUnitCell[][] matrix, long[] supply, long[] demand) {
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
            Debugger debug=new Debugger();
            int i=0,j=0;
            System.out.println(Arrays.toString(supply));
            System.out.println(Arrays.toString(demand));
            while(i<n && j<n){
                debug.Cpu_printer(matrix);
                System.out.println("supply: "+supply[i]+" demand: "+demand[j]);
                if(demand[j]<supply[i]){
                    matrix[i][j].setAllocation(demand[j]);
                    supply[j]=supply[i]-demand[j];
                    supply[i]=0;
                    j++;

                }
                else{
                    matrix[i][j].setAllocation(supply[i]);
                    demand[j]=demand[j]-supply[i];

                    i++;
                }
            }
            System.out.println(Arrays.toString(supply));
            System.out.println(Arrays.toString(demand));
            return true;
        }
        catch (Exception e){
            System.out.println(e.getMessage());
            return false;
        }
    }

}
