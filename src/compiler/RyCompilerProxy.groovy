package compiler

import compiler.listeners.*
import org.antlr.v4.runtime.ANTLRInputStream
import org.antlr.v4.runtime.CommonTokenStream
import org.antlr.v4.runtime.ParserRuleContext
import org.antlr.v4.runtime.RuleContext
import org.antlr.v4.runtime.tree.ParseTree
import org.antlr.v4.runtime.tree.ParseTreeProperty
import org.antlr.v4.runtime.tree.ParseTreeWalker
import utils.Formatter
import java.lang.reflect.Array

class RyCompilerProxy {
    // ------------- initialze setups -------------

    // used to store node's expression
    public static ParseTreeProperty<String> node_expression = new ParseTreeProperty<String>();
    // map used to store function params list
    // map used to store class name with nodes
    public static ParseTreeProperty<String> class_definition = new ParseTreeProperty<>();
    public static HashMap<String, Integer> function_definition = new HashMap<>();
    public static ParseTreeProperty<Integer> function_param_number = new ParseTreeProperty<>();

    public static ParseTreeProperty<HashMap<String,String>> node_compile_info = new ParseTreeProperty<HashMap<String, String>>();

    // push each line or a block of code into a stack, out_stream will store all extract info of each line
    public static Stack<ByteArrayOutputStream> stack_out_stream = new Stack<ByteArrayOutputStream>();
    public static ByteArrayOutputStream class_stream = new ByteArrayOutputStream();
    public static ByteArrayOutputStream expression_stream = new ByteArrayOutputStream();
    public static ByteArrayOutputStream function_stream = new ByteArrayOutputStream();
    public static ByteArrayOutputStream error_stream = new ByteArrayOutputStream();

    public static void main(String[] args) {
        String inputFile = null;
        String currentDir = null;
        String genName = null;

        if (args.length > 0) {
            inputFile = args[0];
            currentDir = System.getProperty("user.dir");
            genName = retriveDir(args[0].substring(0, args[0].length() - 3));
        }

        println(currentDir)

        InputStream is = System.in;

        if (inputFile != null) {
            is = new FileInputStream(inputFile);
        } else {
            System.out.println("File open fail !!");
        }

        ANTLRInputStream input = new ANTLRInputStream(is);
        RyLexer lexer = new RyLexer(input);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        RyParser parser = new RyParser(tokens);

        parser.setBuildParseTree(true);
        ParseTree tree = parser.prog();

        ParseTreeWalker walker = ParseTreeWalker.DEFAULT;
        ProxyParseTreeListener proxy = new ProxyParseTreeListener();
        proxy.add(new RyProgListener());
        proxy.add(new RyExpressionListListener());
        proxy.add(new RyExpressionListener());
        proxy.add(new RyIntegerListener());
        proxy.add(new RyFloatListener());
        proxy.add(new RyStringListener());
        proxy.add(new RyDynamicListener());
        proxy.add(new RyValueListener());
        proxy.add(new RyCondListener());
        proxy.add(new RyFunctionListener());
        proxy.add(new RyClassListener());
        proxy.add(new RyAssignmentListener());

        // Evaluator eval = new Evaluator();
        walker.walk(proxy, tree);

        // errors checking
        ByteArrayOutputStream errors = stack_out_stream.pop();
        String errors_msg = errors.toString();
        if (errors_msg != "") {
            System.out.println(errors_msg);
            return;
        }

        ByteArrayOutputStream out_function = stack_out_stream.pop();
        ByteArrayOutputStream out_expressions = stack_out_stream.pop();
        ByteArrayOutputStream out_class = stack_out_stream.pop();
//         put code into file
        String filename = "${currentDir}/src/${genName}.groovy";
        File file = new File(filename);

//        String runtime_filename = "${currentDir}/src/ObjectModel/Userdefine.groovy"
//        File func_file = new File(runtime_filename);

        String dependencies = """
import ObjectModel.*;
"""

        String code_body = Formatter.wrapClass(Formatter.wrapExpressions(out_expressions.toString()), genName);
        String whole_script = "${dependencies}${code_body}"

        // write content to specific file
//        file.setText(whole_script)
//        func_file.setText(Formatter.wrapFunctions(out_function.toString()));

        println("----------------------------debug--------------------------")
        // TODO: debug cursor
        println(whole_script);
        println("----------------------------debug--------------------------")


//        println("----------------------------debug--------------------------")
//        // TODO: debug cursor
//        println(Formatter.wrapFunctions(out_function.toString(), out_class.toString()));
//        println("----------------------------debug--------------------------")

    }

    public static String retriveDir(String fileName) {
        String[] strs = fileName.split("/");
        return strs[strs.length - 1];
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

    public static String generateResultExpression(String leftVal, String operation, String rightVal) {
        return "${leftVal}.callmethod(\"${operation}\",${rightVal})";
    }

    public static void printToOutStream(String text) {
//            ByteArrayOutputStream out = stack_out_stream.pop();
        PrintStream ps = new PrintStream(expression_stream);
        ps.print(text);
//            stack_out_stream.push(out);
    }

    public static void printToFunctionStream(String text) {
        PrintStream ps = new PrintStream(function_stream);
        ps.print(text);
    }

    public static void printToClassStream(String text) {
//            ByteArrayOutputStream out = main_stream;
        PrintStream ps = new PrintStream(class_stream);
        ps.print(text);
//            stack_out_stream.push(out);
    }

    public static void printToErrorStream(String text) {
        PrintStream ps = new PrintStream(error_stream);
        ps.print(text);
    }

    public static int derieveParamNum(String params_expression) {
//        String params = params_expression.substring(1, params_expression.length());
        String[] param_list = params_expression.split(",");
        return param_list.length;
    }

    // write a method to the class according to the class definition
    public static String write_cls_func(cls_name="TopObject", func_expr) {
        // write function directly to the cls_method map
        return "class_manager.getCls(\"${cls_name}\").write_attr(${func_expr})"
    }

    public static void storeNodeCompileInfo(RuleContext ctx) {
        HashMap<String, String> old_node_info = node_compile_info.get(ctx.getParent());
        node_compile_info.put(ctx, old_node_info);
    }

    public static void updateNodeCompileInfo(RuleContext ctx, HashMap<String, String> update_info) {
        node_compile_info.put(ctx, update_info);
    }

}
