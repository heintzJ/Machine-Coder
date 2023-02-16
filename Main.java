import javax.swing.*;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import java.util.Collections;

public class Main {
    // Instruction format variables
    public static String i;
    public static String op;
    public static String rd;
    public static String op3;
    public static String rs1;
    public static int simm13;
    public static final String zeros = "00000000";
    public static String rs2;
    public static String registerNumber;

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
            // jmpl is always the same, so hard-code output
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

            if (numberOfInputs == 3) {
                String instruction = ARC_code.get(0);
                if (instruction.equals("ld") || instruction.equals("st")) {
                    op = opcode(ARC_code.get(0));
                    machine_code.add(op);
                }
                // Extract key: value pairs from text file
                machine_code.add(getKeyValue(binaryValues, instruction));
                try {
                    if (instruction.equals("ld")) {
                        simm13 = Integer.parseInt(ARC_code.get(1));
                    } else if (instruction.equals("st")) {
                        simm13 = Integer.parseInt(ARC_code.get(2));
                    }
                    String initialResult = Integer.toBinaryString(simm13);
                    String finalResult = String.format("%13s", initialResult).replaceAll(" ", "0");
                    machine_code.add(finalResult);
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

                if (instruction.equals("ld")) {
                    rd = getBinaryValue(ARC_code.get(2));
                } else if (instruction.equals("st")) {
                    rd = getBinaryValue(ARC_code.get(1));
                }
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

            if (numberOfInputs == 4) {
                op = opcode(ARC_code.get(0));
                machine_code.add(op);

                machine_code.add(getKeyValue(binaryValues, ARC_code.get(0)));

                rd = getBinaryValue(ARC_code.get(3));
                machine_code.add(1, rd);

                try {
                    simm13 = Integer.parseInt(ARC_code.get(2));
                    String initialResult = Integer.toBinaryString(simm13);
                    String finalResult = String.format("%13s", initialResult).replaceAll(" ", "0");
                    machine_code.add(finalResult);
                    i = "1";
                } catch (NumberFormatException ex) {
                    machine_code.add(getBinaryValue(ARC_code.get(1)));
                    machine_code.add(zeros);
                    i = "0";
                }
                rs1 = getBinaryValue(ARC_code.get(1));
                machine_code.add(rs1);
                machine_code.add(4,i);
                Collections.swap(machine_code, 3, 5);
            }

            JOptionPane.showMessageDialog(null, machine_code);

            System.out.println(line);

            scanner.close();
            loopBool = false;
        }
    }

    // Find the opcode segment of the machine code
    public static String opcode(String input) {
        if (input.equals("ld") || input.equals("st")) {
            return "11";
        }
        if (input.equals("addcc") || input.equals("orcc") || input.equals("orncc") 
            || input.equals("srl")|| input.equals("jmpl") || input.equals("subcc")
            || input.equals("andcc")) {
            return "10";
        }
        return "";
    }

    public static HashMap<String, String> fileReader(String file) throws FileNotFoundException {
        Scanner binaryScanner = new Scanner(new File(file));
        HashMap<String, String> hm = new HashMap<String, String>();
        while (binaryScanner.hasNextLine()) {
            String[] columns = binaryScanner.nextLine().split(" ");
            hm.put(columns[0], columns[1]);
        }
        return hm;
    }

    public static String getKeyValue(HashMap<String, String> hm, String input) {
        for (var entry : hm.entrySet()) {
            if (entry.getKey().equals(input)) {
                op3 = entry.getValue();
            }
        }
        return op3;
    }

    public static String getBinaryValue(String register) {
        int numberOfRegister = Integer.parseInt(register.substring(1, register.length()));
        String initial_rd = Integer.toBinaryString(numberOfRegister);
        String binaryValue = String.format("%5s", initial_rd).replaceAll(" ", "0");
        return binaryValue;
    }
}
