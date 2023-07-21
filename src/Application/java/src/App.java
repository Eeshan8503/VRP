package src;

import java.lang.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class App {
    static int[][] twoDArray;
    public static void setDistanceMatrix(double[][] distanceMatrix) {
        App.distanceMatrix = distanceMatrix;
    }

    static double[][] distanceMatrix;
    double finalCost;
    static double[] supply ;
    static double[] demand ;
    public static void setDemand(double[] demand) {
        App.demand = demand;
    }

    public static void setSupply(double[] supply) {
        App.supply = supply;
    }

    public void setCost(double cost){
        this.finalCost = cost;
    }
    public static double compute(CostPerUnitCell[][] matrix){
        double result=0;
        for(CostPerUnitCell[] i :matrix){
            for(CostPerUnitCell j:i){
                result+=j.cpu*j.allocation;
            }
        }
        return result;
    }
    public double computeScalar(double petrolPrice, double initial_mileage, double extraLoad){
        double mileage = initial_mileage - ((extraLoad/45.359)*0.033);
        return petrolPrice/mileage;
    }
    public static double runner(){
        // Visualization code goes here -> receive the distance matrix from the frontend

//        long[][] distanceMatrix = {
//                {0, 548, 776, 696, 582, 274, 502, 194, 308, 194, 536, 502, 388, 354, 468, 776, 662},
//                {548, 0, 684, 308, 194, 502, 730, 354, 696, 742, 1084, 594, 480, 674, 1016, 868, 1210},
//                {776, 684, 0, 992, 878, 502, 274, 810, 468, 742, 400, 1278, 1164, 1130, 788, 1552, 754},
//                {696, 308, 992, 0, 114, 650, 878, 502, 844, 890, 1232, 514, 628, 822, 1164, 560, 1358},
//                {582, 194, 878, 114, 0, 536, 764, 388, 730, 776, 1118, 400, 514, 708, 1050, 674, 1244},
//                {274, 502, 502, 650, 536, 0, 228, 308, 194, 240, 582, 776, 662, 628, 514, 1050, 708},
//                {502, 730, 274, 878, 764, 228, 0, 536, 194, 468, 354, 1004, 890, 856, 514, 1278, 480},
//                {194, 354, 810, 502, 388, 308, 536, 0, 342, 388, 730, 468, 354, 320, 662, 742, 856},
//                {308, 696, 468, 844, 730, 194, 194, 342, 0, 274, 388, 810, 696, 662, 320, 1084, 514},
//                {194, 742, 742, 890, 776, 240, 468, 388, 274, 0, 342, 536, 422, 388, 274, 810, 468},
//                {536, 1084, 400, 1232, 1118, 582, 354, 730, 388, 342, 0, 878, 764, 730, 388, 1152, 354},
//                {502, 594, 1278, 514, 400, 776, 1004, 468, 810, 536, 878, 0, 114, 308, 650, 274, 844},
//                {388, 480, 1164, 628, 514, 662, 890, 354, 696, 422, 764, 114, 0, 194, 536, 388, 730},
//                {354, 674, 1130, 822, 708, 628, 856, 320, 662, 388, 730, 308, 194, 0, 342, 422, 536},
//                {468, 1016, 788, 1164, 1050, 514, 514, 662, 320, 274, 388, 650, 536, 342, 0, 764, 194},
//                {776, 868, 1552, 560, 674, 1050, 1278, 742, 1084, 810, 1152, 274, 388, 422, 764, 0, 798},
//                {662, 1210, 754, 1358, 1244, 708, 480, 856, 514, 468, 354, 844, 730, 536, 194, 798, 0},
//        };

//        double[][] distanceMatrix = {
//                {0,3,4,5,7,8},
//                {3,0,6,4,4,6},
//                {4,6,0,6,7,7},
//                {5,4,6,0,5,9},
//                {5,4,7,5,0,7},
//                {8,6,7,9,7,0}
//        };
//        double[] supply = {70,30,50,0,0,0};
//        double[] demand = {0,0,0,65,42,43};



        demand = Arrays.copyOf(demand, 6);
        double[] supply1 = {70,30,50,0,0,0};
        double[] demand1 = {0,0,0,65,42,43};
        int n = distanceMatrix.length;
        // Compute the cost per unit matrix
        CostPerUnitCell[][] matrix = new CostPerUnitCell[n][n];

        for(int i=0;i<n;i++){
            for(int j=0;j<n;j++){
                // Calculate CPU using distance matrix and the scalar
                matrix[i][j] = new CostPerUnitCell(0,1 * distanceMatrix[i][j]);
            }
        }
        CostPerUnitMatrix costPerUnitMatrix = new CostPerUnitMatrix(n, matrix, supply, demand);
        if(n > 7){
            // Northwest Corner method multiple times and compare resutls ( bruteforce )
            Northwest nw = new Northwest(n, costPerUnitMatrix.matrix, supply, demand);
            nw.compute();
            FinalCost finalCost = new FinalCost(compute(costPerUnitMatrix.matrix));
            System.out.println("Cost: "+finalCost.getFinalCost());
            System.out.println("======================================================================");
            NorthwestPnC nwPnC = new NorthwestPnC();
            System.out.println("Original Matrix:");
            nwPnC.printMatrix(matrix, supply1, demand1);

            System.out.println("\nPerforming rotations in all directions:");
            nwPnC.performRotations(matrix, supply, demand);
        }
        else{
            // Northwest Corner method followed by (n-1)*(n-1) times MODI method [ or if lowest cost found before that ]
            Northwest nw = new Northwest(n, costPerUnitMatrix.matrix, supply, demand);
            nw.compute();
            FinalCost finalCost = new FinalCost(compute(costPerUnitMatrix.matrix));
            System.out.println("Cost after NW: "+finalCost.getFinalCost());
            System.out.println("======================================================================");
            List<List<Integer>> routes = new ArrayList<>();
            // traverese through the costPerUnitMatrix and for every row create a route arrayList and if allocation of a column is greater than 0 add column number to the list
            for(int i=0;i<n;i++){
                List<Integer> temp = new ArrayList<>();
                temp.add(i);
                for(int j=0;j<n;j++){
                    if(costPerUnitMatrix.matrix[i][j].allocation > 0){
                        if(j == i){
//                            empty the temp list
//                            temp.add(100);
                            continue;
                        }
                        temp.add(j);
                    }

                }
                if(temp.size()<=1){
                    temp.clear();
                }
                if(!temp.isEmpty())
                    routes.add(temp);

            }
            System.out.println("Routes"+routes);
           twoDArray = new int[routes.size()][];
            for (int i = 0; i < routes.size(); i++) {
                List<Integer> row = routes.get(i);
                twoDArray[i] = new int[row.size()];
                for (int j = 0; j < row.size(); j++) {
                    twoDArray[i][j] = row.get(j);
                }
            }
//            getRoutes(twoDArray);






            // MODI method
//            Modi modi = new Modi(n, costPerUnitMatrix.matrix, supply, demand);
//            modi.compute();
//            finalCost = new FinalCost(compute(modi.getMatrix()));
//            System.out.println("Cost after MODI: "+finalCost.getFinalCost());

            // VAM
//            VAM vam = new VAM(costPerUnitMatrix.matrix, supply, demand, n);
//            vam.solve();
            return finalCost.getFinalCost();
        }

        return 2.5;
    }
    public static int[][] getRoutes(){
        return twoDArray;
    }
}