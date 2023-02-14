import javax.swing.*;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        String instruction = JOptionPane.showInputDialog(null, "Enter");
        Scanner scanner = new Scanner(instruction);
        ArrayList<String> temp = new ArrayList<String>();
        String op;
        String rd;
        String op3;
        String rs1;
        String i;
        String simm13;
        String zeros;
        String rs2;

        int count = 0;

        while (scanner.hasNext()){
            if (count == 0){
                if (scanner.equals("ld")){
                    op = "11";
                    op3 = Instruction_sets.load();
                    temp.add(op);
                    temp.add(op3);
                }
                if (scanner.equals("st")){
                    op = "11";
                    op3 = Instruction_sets.store();
                    temp.add(op);
                    temp.add(op3);
                }
            }
        }

        JOptionPane.showMessageDialog(null, temp.toString());
        
        System.out.println(instruction);
    }

    /**
     * @param toUse Specify which string to use
     * @param start Where the concatenation should start
     * @return A substring starting at start, until the of of toUse
     */
    public static String remainingDigits(String toUse, String start){
        return toUse.substring(start.length()+1, toUse.length()-1);
    }
}
