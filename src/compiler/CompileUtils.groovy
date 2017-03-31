package compiler

class CompileUtils {

    public static String generateResultExpression(String leftVal, String operation, String rightVal) {
        return "${leftVal}.callmethod(\"${operation}\",${rightVal})";
    }

    public static String getAssignOprText(String opr) {
        String ret_text;
        switch(opr) {
            case "+=":
                ret_text = "addEqualAssign";
                break;
            case "-=":
                ret_text = "minEqualAssign";
                break;
            case "*=":
                ret_text = "mulEqualAssign";
                break;
            case "/=":
                ret_text = "divEqualAssign";
                break;
            case "%=":
                ret_text = "modEqualAssign";
                break;
            default:
                ret_text = " = ";
                break;
        }

        return ret_text;
    }

    public static String getCompareText(String opr) {
        String ret_text = null;

        switch(opr) {
            case ">":
                ret_text = " > 0";
                break;
            case "<":
                ret_text = " < 0";
                break;
            case ">=":
                ret_text = " >= 0";
                break;
            case "<=":
                ret_text = " <= 0";
                break;
            case "==":
                ret_text = " == 0";
                break;
        }

        return ret_text;
    }

    public static String getOprText(String opr) {
        String ret_text = null;
        switch(opr) {
            case "+" :
                ret_text = "add";
                break;
            case "-" :
                ret_text = "minus";
                break;
            case "*" :
                ret_text = "multiply";
                break;
            case "/" :
                ret_text = "div";
                break;
            case "%" :
                ret_text = "module";
                break;
        }

        return ret_text;
    }
}
