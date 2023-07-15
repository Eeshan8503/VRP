package src;

import java.util.Arrays;

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
        CostPerUnitMatrix cpu = new CostPerUnitMatrix(n, matrix, supply, demand);
        Debugger debug = new Debugger();
        try{
//            locate northwest corner
//            find least of supply and demand
//            update cpu boundaries
//            reiterate until cpu boundaries go out of range

            int i=0,j=0;
//            System.out.println(Arrays.toString(supply));
//            System.out.println(Arrays.toString(demand));
            while(i < n && j < n){
                if(demand[j] == 0){
                    ++j;
                    continue;
                }
                if(supply[i] == 0){
                    ++i;
                    continue;
                }
                if(supply[i] > demand[j]){
                    double balance = supply[i] - demand[j];
                    cpu.matrix[i][j].allocation = demand[j];
//                    System.out.println("demand: " + cpu.matrix[i][j].allocation);
                    if(j >= i){
                        supply[j]+= balance;
                        supply[i] = 0;
                    }
                    else{
                        supply[i]-= demand[j];
                    }
                    demand[j] = 0;
                    ++j;
//                    System.out.println(Arrays.toString(supply));
//                    System.out.println(Arrays.toString(demand));
//                    debug.Cpu_printer(matrix);
//                    System.out.println("=====================================");
                }
                else if(demand[j] > supply[i]){
                    cpu.matrix[i][j].allocation = supply[i];
                    demand[j]-= supply[i];
                    supply[i] = 0;
                    ++i;
//                    System.out.println(Arrays.toString(supply));
//                    System.out.println(Arrays.toString(demand));
//                    debug.Cpu_printer(matrix);
//                    System.out.println("=====================================");
                }
                else{
                    cpu.matrix[i][j].allocation = supply[i];
                    supply[i] = 0;
                    demand[j] = 0;
                    ++j;
//                    System.out.println(Arrays.toString(supply));
//                    System.out.println(Arrays.toString(demand));
//                    debug.Cpu_printer(matrix);
//                    System.out.println("=====================================");
                }
            }
//            System.out.println(Arrays.toString(supply));
//            System.out.println(Arrays.toString(demand));
//            debug.Cpu_printer(matrix);
            return true;


        }
        catch (Exception e){
            System.out.println(e.getMessage());
            return false;
        }
    }


}
