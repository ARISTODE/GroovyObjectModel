package utils;
class Formatter {
    public static String wrapClass(String sourceStr, String className) {
        String header = "class " + className + " {\n\t";
        return header + sourceStr + "}\n";
    }

    public static String wrapExpressions(String sourceStr) {
        String main_function_signiture = "public static void main(String[] args) { \n";
        String tail = "\t}\n";
        String wholeScript = main_function_signiture + sourceStr + tail;
        return wholeScript;
    }

    public static String wrapFunctions(String main_function,String sourceStr) {
        // concat function expressions after main_function
        return main_function += sourceStr;
    }
}
