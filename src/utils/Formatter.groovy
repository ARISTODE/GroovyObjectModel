package utils;
class Formatter {
    public static String wrapClass(String sourceStr, String className) {
String res = """
class ${className} {
    ${sourceStr}
}
"""
        return res;
    }

    public static String wrapExpressions(String sourceStr) {
    String wholeScript = """public static void main(String[] argvs) {
        ClassManager class_manager = new ClassManager();
        Instance main = new Instance(class_manager.getCls(\"TopObject\"));
        Instance current_obj = main;
        ${sourceStr}
    }
    """
        return wholeScript;
    }

    public static String wrapFunctions(String main_function,String sourceStr) {
        // concat function expressions after main_function
        return "${main_function}\n${sourceStr}";
    }
}
