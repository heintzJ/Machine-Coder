import javax.swing.*;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import java.util.Collections;

public class Main {
    // Instruction format variables
    public static String i, op, rd, op3, rs1, simm13, rs2, registerNumber, cond, disp22;
    // public static String op;
    // public static String rd;
    // public static String op3;
    // public static String rs1;
    // public static String simm13;
    public static final String zeros = "00000000";
    // public static String rs2;
    // public static String registerNumber;

    public static void main(String[] args) throws FileNotFoundException {
        String line = JOptionPane.showInputDialog(null, "Enter");
        Scanner scanner = new Scanner(line);
        // Result
        ArrayList<String> machine_code = new ArrayList<String>();
        // List of instructions
        ArrayList<String> ARC_code = new ArrayList<String>();
        boolean loopBool = true;
        int numberOfInputs = 0;

        // Finding number of instructions
        while (scanner.hasNext()) {
            ARC_code.add(scanner.next());
            numberOfInputs++;
        }

        HashMap<String, String> binaryValues = fileReader("binary_values.txt");
        while (loopBool){
            // jmpl is (so far) always the same, so hard-code output
            if (ARC_code.get(0).equals("jmpl")){
                machine_code.add("10");
                machine_code.add("00000");
                machine_code.add("111000");
                machine_code.add("01111");
                machine_code.add("1");
                machine_code.add("0000000000100");
                JOptionPane.showMessageDialog(null, machine_code);
                break;
            }

            // branch instruction contain 2 elements
            if (numberOfInputs == 2){
                op = opcode(ARC_code.get(0));
                machine_code.add(op);
                // extra 0 for branching
                machine_code.add("0");
                // cond is pretty much op3 for branching
                cond = getKeyValue(binaryValues, ARC_code.get(0));
                machine_code.add(cond);
                // op2 = 010 for branching
                machine_code.add("010");
                int number = Integer.parseInt(ARC_code.get(1));
                String initialResult = Integer.toBinaryString(number);
                // padding zeros to make 22 bits
                String disp22 = String.format("%22s", initialResult).replaceAll(" ", "0");
                machine_code.add(disp22);
            }

            // ld and st are 3 input instructions
            if (numberOfInputs == 3) {
                String instruction = ARC_code.get(0);
                if (instruction.equals("ld") || instruction.equals("st")) {
                    op = opcode(ARC_code.get(0));
                    machine_code.add(op);
                }
                // Extract key: value pairs from text file
                machine_code.add(getKeyValue(binaryValues, instruction));

                // try to get the integer value from the second element
                try {
                    if (instruction.equals("ld")) {
                        simm13 = getSimm(ARC_code.get(1));
                    // for store, integer would be in element 2
                    } else if (instruction.equals("st")) {
                        simm13 = getSimm(ARC_code.get(2));
                    }
                    machine_code.add(simm13);
                }
                // if the second element is not an int, then it is a register
                catch (NumberFormatException ex) {
                    if (instruction.equals("ld")) {
                        rs1 = getBinaryValue(ARC_code.get(1));
                    } else if (instruction.equals("st")) {
                        rs1 = getBinaryValue(ARC_code.get(2));
                    }
                    machine_code.add(zeros);
                    machine_code.add(rs1);
                    i = "0";
                    machine_code.add(2, i);
                }

                // get the destination register
                if (instruction.equals("ld")) {
                    rd = getBinaryValue(ARC_code.get(2));
                } else if (instruction.equals("st")) {
                    rd = getBinaryValue(ARC_code.get(1));
                }
                // rs1 is always 00000 for ld and st
                rs1 = "00000";
                machine_code.add(1, rd);
                machine_code.add(2, rs1);
                // Check if i is already set
                if (i != "0") {
                    i = "1";
                    machine_code.add(4, i);
                    // Fixing order
                    Collections.swap(machine_code, 2, 3);
                }
            }

            // arithmetic is 4 instructions
            if (numberOfInputs == 4) {
                op = opcode(ARC_code.get(0));
                machine_code.add(op);
                // op3 code
                machine_code.add(getKeyValue(binaryValues, ARC_code.get(0)));

                // destination register
                rd = getBinaryValue(ARC_code.get(3));
                machine_code.add(1, rd);

                // try to get integer value from 2nd element, then i = 1
                try {
                    simm13 = getSimm(ARC_code.get(2));
                    machine_code.add(simm13);
                    i = "1";
                // if not an integer, then get the register number, and i = 0
                } catch (NumberFormatException ex) {
                    machine_code.add(getBinaryValue(ARC_code.get(1)));
                    machine_code.add(zeros);
                    i = "0";
                }
                // rs1 is the first element (index 1)
                rs1 = getBinaryValue(ARC_code.get(1));
                machine_code.add(rs1);
                machine_code.add(4,i);
                Collections.swap(machine_code, 3, 5);
            }
            // output
            JOptionPane.showMessageDialog(null, machine_code);

            System.out.println(line);

            scanner.close();
            loopBool = false;
        }
    }

    /**
     * @param input String for which to get opcode
     * @return Opcode of the string
     */
    public static String opcode(String input) {
        if (input.equals("ld") || input.equals("st")) {
            return "11";
        }
        if (input.equals("addcc") || input.equals("orcc") || input.equals("orncc") 
            || input.equals("srl")|| input.equals("jmpl") || input.equals("subcc")
            || input.equals("andcc")) {
            return "10";
        }
        if (input.equals("be") || input.equals("bcs") || input.equals("bneg") 
            || input.equals("bvs") || input.equals("ba")){
                return "00";
            }
        return "";
    }

    /**
     * @param file File to get key: value pairs from
     * @return HashMap of op3 codes for each instruction
     * @throws FileNotFoundException
     */
    public static HashMap<String, String> fileReader(String file) throws FileNotFoundException {
        Scanner binaryScanner = new Scanner(new File(file));
        HashMap<String, String> hm = new HashMap<String, String>();
        while (binaryScanner.hasNextLine()) {
            String[] columns = binaryScanner.nextLine().split(" ");
            hm.put(columns[0], columns[1]);
        }
        return hm;
    }

    /**
     * @param hm HashMap input
     * @param input The instruction, such as "addcc"
     * @return op3 code for the instruction input
     */
    public static String getKeyValue(HashMap<String, String> hm, String input) {
        for (var entry : hm.entrySet()) {
            if (entry.getKey().equals(input)) {
                op3 = entry.getValue();
            }
        }
        return op3;
    }

    /**
     * @param register The register from which to get the binary value from
     * @return The binary value of the number of the register
     */
    public static String getBinaryValue(String register) {
        int numberOfRegister = Integer.parseInt(register.substring(1, register.length()));
        String initial_rd = Integer.toBinaryString(numberOfRegister);
        // padding zeros to make 5 bits
        String binaryValue = String.format("%5s", initial_rd).replaceAll(" ", "0");
        return binaryValue;
    }

    /**
     * @param integer A string of an integer to assign to simm13
     * @return Binary value of the integer
     */
    public static String getSimm(String integer){
        int number = Integer.parseInt(integer);
        String initialResult = Integer.toBinaryString(number);
        // padding zeros to make 13 bits
        String finalResult = String.format("%13s", initialResult).replaceAll(" ", "0");
        return finalResult;
    }
}
