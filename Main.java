import javax.swing.*;
import org.apache.commons.lang3.StringUtils;

public class Main {
    public static void main(String[] args) {
        String label = JOptionPane.showInputDialog(null, "Enter");
        String machineCode = "00000000000000000000000000000000";
        String op = machineCode.substring(0,2);
        String rd = machineCode.substring(2,7);
        String op3 = machineCode.substring(7, 13);
        String rs1 = machineCode.substring(13,18);
        String i = machineCode.substring(18,19);
        String simm13 = machineCode.substring(19,31);
        String zeros = machineCode.substring(19,27);
        String rs2 = machineCode.substring(27,31);
        if (label.equals("ld")){
            machineCode = StringUtils.replaceOnce(op, "00", "11") + remainingDigits(machineCode, op);
            machineCode = StringUtils.replaceOnce(op3, "000000", Instruction_sets.load()) + remainingDigits(machineCode, op3);
        }
        if (label.equals("st")){
            machineCode = StringUtils.replaceOnce(op, "00", "11") + remainingDigits(machineCode, op);
            machineCode = StringUtils.replaceOnce(op3, "000000", Instruction_sets.store()) + remainingDigits(machineCode, op);
        }
        JOptionPane.showMessageDialog(null, machineCode);
        System.out.println(label);
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
