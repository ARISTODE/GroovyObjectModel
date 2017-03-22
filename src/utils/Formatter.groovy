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
    String wholeScript = """public static void main(String[] args) {
        ClassManager class_manager = new ClassManager();
        InstanceManager instance_manager = new InstanceManager(class_manager);
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
