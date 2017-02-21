package compiler

import compiler.listeners.ProxyParseTreeListener
import compiler.listeners.RyExpressionListListener
import compiler.listeners.RyExpressionListener
import compiler.listeners.RyFloatListener
import compiler.listeners.RyIntegerListener
import compiler.listeners.RyProgListener
import compiler.listeners.RyRvalueListener
import org.antlr.v4.runtime.ANTLRInputStream
import org.antlr.v4.runtime.CommonTokenStream
import org.antlr.v4.runtime.tree.ParseTree
import org.antlr.v4.runtime.tree.ParseTreeProperty
import org.antlr.v4.runtime.tree.ParseTreeWalker
import utils.Formatter

class RyCompilerProxy {
    // ------------- initialze setups -------------

    // create parse tree properties to store each node's information
    public static ParseTreeProperty<String> value_store = new ParseTreeProperty<String>();
    // used to store node's
    public static ParseTreeProperty<String> node_expression = new ParseTreeProperty<String>();
    // map used to store function params list
    public static HashMap<String, Integer> function_definition = new HashMap<>();
    public static ParseTreeProperty<Integer> function_param_number = new ParseTreeProperty<>();
    // push each line or a block of code into a stack, out_stream will store all extract info of each line
    public static Stack<ByteArrayOutputStream> stack_out_stream = new Stack<ByteArrayOutputStream>();
    public static ByteArrayOutputStream main_stream = new ByteArrayOutputStream();
    public static ByteArrayOutputStream expression_stream = new ByteArrayOutputStream();
    public static ByteArrayOutputStream function_stream = new ByteArrayOutputStream();
    public static ByteArrayOutputStream error_stream = new ByteArrayOutputStream();

    static void printToOutStream(String text) {
//            ByteArrayOutputStream out = stack_out_stream.pop();
        PrintStream ps = new PrintStream(expression_stream);
        ps.print(text);
//            stack_out_stream.push(out);
    }

    public static void main(String[] args) {
        String inputFile = null;
        String currentDir = null;
        String genName = null;

        if (args.length > 0) {
            inputFile = args[0];
            currentDir = System.getProperty("user.dir");
            genName = retriveDir(args[0].substring(0, args[0].length() - 3));
        }

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
        proxy.add(new RyRvalueListener());
        proxy.add(new RyIntegerListener());
        proxy.add(new RyFloatListener());

//        Evaluator eval = new Evaluator();
        walker.walk(proxy, tree);

        // errors checking
        ByteArrayOutputStream errors = stack_out_stream.pop();
        String errors_msg = errors.toString();
        if (!errors_msg.equals("")) {
            System.out.println(errors_msg);
            return;
        }

        ByteArrayOutputStream out_function = stack_out_stream.pop();
        ByteArrayOutputStream out_expressions = stack_out_stream.pop();

        // put code into file
//        FileWriter fw = new FileWriter(currentDir + "/" + genName + ".java");
//        PrintWriter pw = new PrintWriter(fw);


        String wholeScript = Formatter.wrapClass(Formatter.wrapFunctions(Formatter.wrapExpressions(out_expressions.toString()), out_function.toString()), genName);
//        pw.print(wholeScript);
//        pw.close();
        System.out.println(wholeScript);
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
                ret_text = "min";
                break;
            case "*" :
                ret_text = "mul";
                break;
            case "/" :
                ret_text = "div";
                break;
            case "%" :
                ret_text = "mod";
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
        return leftVal + "." + operation + "(" + rightVal + ")";
    }
}
