import javax.swing.*;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws FileNotFoundException{
        String line = JOptionPane.showInputDialog(null, "Enter");
        Scanner scanner = new Scanner(line);
        ArrayList<String> machine_code = new ArrayList<String>();
        ArrayList<String> ARC_code = new ArrayList<String>();
        String op;
        String rd;
        String op3;
        String rs1;
        String i;
        int simm13;
        String zeros;
        String rs2;

        int numberOfInputs = 0;

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
            for (var entry: binaryValues.entrySet()){
                if (entry.getKey().equals(instruction)){
                    op3 = entry.getValue();
                }
            }
            try { 
                simm13 = (Integer.parseInt(ARC_code.get(1)));
                String initialResult = Integer.toBinaryString(simm13);
                String finalResult = String.format("%13s", initialResult).replaceAll(" ", "0");
                machine_code.add(finalResult);
            }
            catch (NumberFormatException ex){
                machine_code.add("");
            }
            rd = Integer.toBinaryString(ARC_code.get(0).charAt(1));
            machine_code.add(1, rd);
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
