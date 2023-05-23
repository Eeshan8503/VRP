package src;

public class Debugger {
    public void Cpu_printer(CostPerUnitCell[][] matrix){
        for(CostPerUnitCell[] i:matrix){
            for(CostPerUnitCell j:i){
                System.out.print(j.allocation+"|"+j.cpu+"\t");
            }
            System.out.println();
        }

    }
}
