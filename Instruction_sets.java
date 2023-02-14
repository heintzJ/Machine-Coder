public class Instruction_sets {
    public static String load(){
        return "000000";
    }
    public static String store(){
        return "000100";
    }
    public static String branch(String branch_type){
        if (branch_type.equals("be")) return "0001";
        if (branch_type.equals("bcs")) return "0101";
        if (branch_type.equals("bneg")) return "0110";
        if (branch_type.equals("bvs")) return "0111";
        if (branch_type.equals("ba")) return "1000";
        return "";
    }
    public static String arithmetic(String ari_type){
        if (ari_type.equals("addcc")) return "010000";
        if (ari_type.equals("andcc")) return "010001";
        if (ari_type.equals("orcc")) return "010010";
        if (ari_type.equals("orncc")) return "010110";
        if (ari_type.equals("srl")) return "100110";
        if (ari_type.equals("jmpl")) return "111000";
        return "";
    }

}
