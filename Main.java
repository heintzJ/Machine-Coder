import javax.swing.*;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import java.util.Collections;

public class Main {
    public static void main(String[] args) throws FileNotFoundException{
        String line = JOptionPane.showInputDialog(null, "Enter");
        Scanner scanner = new Scanner(line);
        // Result
        ArrayList<String> machine_code = new ArrayList<String>();
        // List of instructions
        ArrayList<String> ARC_code = new ArrayList<String>();
        // Instruction format variables
        String op;
        String rd;
        String op3;
        String rs1;
        String i;
        int simm13;
        String zeros = "00000000";
        String rs2;
        String registerNumber;

        int numberOfInputs = 0;

        // Finding number of instructions
        while (scanner.hasNext()){
            ARC_code.add(scanner.next());
            numberOfInputs++;
        }

        HashMap<String,String> binaryValues = fileReader("binary_values.txt");

        if (numberOfInputs == 3){
            String instruction = ARC_code.get(0);
            if (instruction.equals("ld") || instruction.equals("st")){
                op = opcode("ld");
                machine_code.add(op);   
            }
            // Extract key: value pairs from text file
            for (var entry: binaryValues.entrySet()){
                if (entry.getKey().equals(instruction)){
                    op3 = entry.getValue();
                    machine_code.add(op3);
                }
            }
            if (instruction.equals("ld")){
                try { 
                    simm13 = (Integer.parseInt(ARC_code.get(1)));
                    String initialResult = Integer.toBinaryString(simm13);
                    String finalResult = String.format("%13s", initialResult).replaceAll(" ", "0");
                    machine_code.add(finalResult);
                }
                // if the second element is not an int, then it is a register
                catch (NumberFormatException ex){
                    registerNumber = ARC_code.get(1);
                    int numberOfRegister = Integer.parseInt(registerNumber.substring(1, registerNumber.length()));
                    String initial_rs1 = Integer.toBinaryString(numberOfRegister);
                    rs1 = String.format("%5s", initial_rs1).replaceAll(" ", "0");
                    machine_code.add(rs1);
                    machine_code.add(zeros);
                    i = "0";
                    machine_code.add(3, i);
                }
                registerNumber = ARC_code.get(2);
                int numberOfRegister = Integer.parseInt(registerNumber.substring(1, registerNumber.length()));
                String initial_rd = Integer.toBinaryString(numberOfRegister);
                rd = String.format("%5s", initial_rd).replaceAll(" ", "0");
                rs1 = "00000";
                i = "1";
                machine_code.add(1, rd);
                machine_code.add(2, rs1);
                // Check if i is already set
                if (machine_code.get(4) != "0"){
                    machine_code.add(4, i);
                }
                // Fixing order
                Collections.swap(machine_code, 2, 3);
            }
        }
       
        JOptionPane.showMessageDialog(null, machine_code);
        
        System.out.println(line);

        scanner.close();
    }

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

    public static HashMap<String, String> fileReader(String file) throws FileNotFoundException{
        Scanner binaryScanner = new Scanner(new File(file));
        HashMap<String, String> hm = new HashMap<String, String>();
        while(binaryScanner.hasNextLine()){
            String[] columns = binaryScanner.nextLine().split(" ");
            hm.put(columns[0], columns[1]);
        }
        return hm;
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
