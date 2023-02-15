import javax.swing.*;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        String instruction = JOptionPane.showInputDialog(null, "Enter");
        Scanner scanner = new Scanner(instruction);
        ArrayList<String> machine_code = new ArrayList<String>();
        String op;
        String rd;
        String op3;
        String rs1;
        String i;
        int simm13;
        String zeros;
        String rs2;

        int count = 0;

        // int numberOfInputs = countInputs(instruction);
        // System.out.println(numberOfInputs);

        while (scanner.hasNext()){
            // if (numberOfInputs == 3){
                if (count == 0){
                    if (scanner.next().equals("ld")){
                        op = opcode("ld");
                        machine_code.add(op);
                        op3 = Instruction_sets.load();
                        machine_code.add(op3);
                    }
                        
                    else if (scanner.next().equals("st")){
                        op = opcode("ld");
                        op3 = Instruction_sets.store();
                        machine_code.add(op);
                        machine_code.add(op3);
                    }
                    count++;
                }
                if (count == 1){
                    if(scanner.hasNextInt()){
                        simm13 = scanner.nextInt();
                        String initialResult = Integer.toBinaryString(simm13);
                        String finalResult = String.format("%13s", initialResult).replaceAll(" ", "0");
                        machine_code.add(finalResult);
                    }
                    count++;
                }
                if (count == 2){
                    int num = scanner.next().charAt(1);
                    rd = Integer.toBinaryString(num);
                    machine_code.add(1, rd);
                    System.out.println(num);
                    System.out.println(rd);
                }
            }

        JOptionPane.showMessageDialog(null, machine_code);
        
        System.out.println(instruction);

        scanner.close();
    }
// }

    /**
     * @param toUse Specify which string to use
     * @param start Where the concatenation should start
     * @return A substring starting at start, until the of of toUse
     */
    public static String remainingDigits(String toUse, String start){
        return toUse.substring(start.length()+1, toUse.length()-1);
    }

    // Find the opcode segment of the machine code
    public static String opcode(String input){
        if (input.equals("ld") || input.equals("st")){
            return "11";
        }
        return "";
    }

    // Count how many instructions are in the input CURRENTLY BREAKS EVERYTHING
    // public static int countInputs(String input){
    //     Scanner in = new Scanner(input);
    //     int inputs = 0;
    //     while(in.hasNext()){
    //         inputs++;
    //     }
    //     in.close();
    //     return inputs;
    // }
}
