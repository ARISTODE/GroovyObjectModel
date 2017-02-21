import compiler.*;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.misc.Interval;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeListener;
import org.antlr.v4.runtime.tree.ParseTreeProperty;
import org.antlr.v4.runtime.tree.ParseTreeWalker;
import org.antlr.v4.runtime.CharStream.*;
import org.antlr.v4.runtime.tree.gui.SystemFontMetrics;

import java.io.*;
import java.util.HashMap;
import java.util.Stack;
import utils.*;

public class RyCompiler {


    // inner class, used to do the type checking etc ...
    public static class Evaluator extends RyBaseListener {
        // create parse tree properties to store each node's information
        ParseTreeProperty<String> value_store = new ParseTreeProperty<String>();
        // used to store node's
        ParseTreeProperty<String> node_expression = new ParseTreeProperty<String>();
        // map used to store function params list
        HashMap<String, Integer> function_definition = new HashMap<>();
        ParseTreeProperty<Integer> function_param_number = new ParseTreeProperty<>();

        // push each line or a block of code into a stack, out_stream will store all extract info of each line
        Stack<ByteArrayOutputStream> stack_out_stream = new Stack<ByteArrayOutputStream>();
        ByteArrayOutputStream main_stream = new ByteArrayOutputStream();
        ByteArrayOutputStream expression_stream = new ByteArrayOutputStream();
        ByteArrayOutputStream function_stream = new ByteArrayOutputStream();
        ByteArrayOutputStream error_stream = new ByteArrayOutputStream();

        public static String repeat(String str, int times) {
            if (times <= 0) {
                return "";
            } else {
                return str + repeat(str,times - 1);
            }
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

        public void printToOutStream(String text) {
//            ByteArrayOutputStream out = stack_out_stream.pop();
            PrintStream ps = new PrintStream(expression_stream);
            ps.print(text);
//            stack_out_stream.push(out);
        }

        public void printToFunctionStream(String text) {
            PrintStream ps = new PrintStream(function_stream);
            ps.print(text);
        }

        public void printToMainStream(String text) {
//            ByteArrayOutputStream out = main_stream;
            PrintStream ps = new PrintStream(expression_stream);
            ps.println(text);
//            stack_out_stream.push(out);
        }

        public void printToErrorStream(String text) {
            PrintStream ps = new PrintStream(error_stream);
            ps.println(text);
        }

        public int derieveParamNum(String params_expression) {
            String params = params_expression.substring(1, params_expression.length());
            String[] param_list = params.split(",");
            return  param_list.length;
        }

        public void debug(String text) {
            System.out.println(text);
        }

// ======================================  Prog ===============================================

        public void enterProg(RyParser.ProgContext ctx) {
//            printToMainStream("class {");
            ByteArrayOutputStream out = main_stream;
//            stack_out_stream.push(out);
        }

        public void exitProg(RyParser.ProgContext ctx) {
            String all_expressions = node_expression.get(ctx.getChild(0));
            printToOutStream(all_expressions);
            stack_out_stream.push(main_stream);
            stack_out_stream.push(expression_stream);
            stack_out_stream.push(function_stream);
            stack_out_stream.push(error_stream);
        }

// ================================  Int  =======================================

        public void exitInt_assignment(RyParser.Int_assignmentContext ctx) {
            String var = ctx.var_id.getText();
            String int_result_expression = node_expression.get(ctx.getChild(2));
            String int_assignment_expression;

            switch(ctx.op.getType()) {
                case RyParser.ASSIGN:
                    int_assignment_expression = "Instance ${var} = ${int_result_expression};\n";
                    break;
                default:
                    String assignOprText = getAssignOprText(ctx.op.getText());
                    int_assignment_expression = "${var}.${assignOprText}(${int_result_expression});\n";
                    break;
            }

            node_expression.put(ctx, int_assignment_expression);
        }

        // the result of this function will be pushed to the defined value
        public void exitInt_result(RyParser.Int_resultContext ctx) {
            if (ctx.getChildCount() == 3 && ctx.op != null) {
                String left_expression = node_expression.get(ctx.getChild(0));
                String right_expression = node_expression.get(ctx.getChild(2));
                String opr_text = getOprText(ctx.op.getText());
                String int_result_expression = generateResultExpression(left_expression, opr_text, right_expression);
                node_expression.put(ctx, int_result_expression);
            } else if (ctx.getChildCount() == 1) {
                String int_result_expression = "new Instance(\"Global._RyInteger\",${node_expression.get(ctx.getChild(0))})";
                node_expression.put(ctx, int_result_expression);
            }
        }

        public void exitInt_t(RyParser.Int_tContext ctx) {
            String int_val_text = ctx.INT().getText();
            node_expression.put(ctx, int_val_text);
        }


        // =================================  Class  ======================================
        public void exitClass_definition(RyParser.Class_definitionContext ctx) {
            String class_name = node_expression.get(ctx.getChild(1));
            String class_body_expressions = node_expression.get(ctx.getChild(3));
            String class_definition_expression = String.format("class %s {\n %s \n}", class_name, class_body_expressions);
            node_expression.put(ctx, class_definition_expression);
        }

        public void exitClass_name(RyParser.Class_nameContext ctx) {
            String constant_expression = node_expression.get(ctx.getChild(0));
            node_expression.put(ctx, constant_expression);
        }

        public void exitClass_body(RyParser.Class_bodyContext ctx) {
            String class_body_expression_list = node_expression.get(ctx.getChild(0));
            node_expression.put(ctx, class_body_expression_list);
        }

        // ================================  Function =====================================
        public void exitFunction_definition(RyParser.Function_definitionContext ctx) {
            String function_header_expression = node_expression.get(ctx.getChild(0));
            String function_body_expression = node_expression.get(ctx.getChild(1));
            String function_definition_expression = "public static Value " + function_header_expression + function_body_expression;
            node_expression.put(ctx, function_definition_expression);
            printToFunctionStream(function_definition_expression + "\n");
        }

        public void exitFunction_header(RyParser.Function_headerContext ctx) {
            String function_header_expression = "";
            String function_name = node_expression.get(ctx.getChild(1));
            int param_amount = 0;
            // if has params
            if (ctx.getChild(2).getText().length() > 2) {
                String function_params_expression = node_expression.get(ctx.getChild(2));
                function_header_expression = function_name + function_params_expression;
                param_amount = derieveParamNum(function_params_expression);
            } else {
                function_header_expression = function_name + "()";
            }

            // record function definition
            function_definition.put(function_name, param_amount);
            node_expression.put(ctx, function_header_expression);
        }

        public void exitFunction_name(RyParser.Function_nameContext ctx) {
            String function_name_expression = node_expression.get(ctx.getChild(0));
            node_expression.put(ctx, function_name_expression);
        }

        public void exitFunction_params(RyParser.Function_paramsContext ctx) {
            String all_params_expression = node_expression.get(ctx.getChild(1));
            node_expression.put(ctx, "(" + all_params_expression + ")");
        }

        public void exitFunction_definition_param_list(RyParser.Function_definition_param_listContext ctx) {
            int child_count = ctx.getChildCount();
            String all_param_expression = "";
            if (child_count == 1) {
                all_param_expression = node_expression.get(ctx.getChild(0));
            } else {
                for (int i = 0;i < child_count;i++) {
                    if (ctx.getChild(i) != ctx.COMMA()) {
                        if (i != child_count - 1) {
                            all_param_expression += node_expression.get(ctx.getChild(i)) + ", ";
                        } else {
                            all_param_expression += node_expression.get(ctx.getChild(i));
                        }
                    }
                }
            }

            node_expression.put(ctx, all_param_expression);
        }

        public void exitFunction_definition_param_id(RyParser.Function_definition_param_idContext ctx) {
            String param_id_expression = "Value " + node_expression.get(ctx.getChild(0));
            node_expression.put(ctx, param_id_expression);
        }

        public void exitFunction_body(RyParser.Function_bodyContext ctx) {
            String expression_list_expression = node_expression.get(ctx.getChild(0));
            // judge if the last expression is return statement
            int child_count = ctx.getChildCount();
            if (!ctx.getChild(child_count - 1).getText().contains("return")) {
                expression_list_expression += String.format("return (%s)", node_expression.get(ctx.getChild(child_count-2)));
            }
            node_expression.put(ctx, "{ \t" + expression_list_expression + "\n}");
        }

        public void exitReturn_statement(RyParser.Return_statementContext ctx) {
            String all_result_expression = node_expression.get(ctx.getChild(1));
            String return_expression = "return " + all_result_expression + ";";
            node_expression.put(ctx, return_expression);
        }

        // ================================  Function call  =======================================
        public void exitFunction_call(RyParser.Function_callContext ctx) {
            String function_call_expression = "";
            String function_name = node_expression.get(ctx.getChild(0));
            String param_list_expression = node_expression.get(ctx.getChild(2));
            function_call_expression += function_name;

            int param_amount = function_param_number.get(ctx.getChild(2)) == null ? 0 : function_param_number.get(ctx.getChild(2));
            int expect_param_amount = function_definition.get(function_name);

            if (param_amount != expect_param_amount) {
                printToErrorStream("Argument number error: " + "at function " + function_name + " expect " + expect_param_amount + " params, but get " + param_amount + " params");
            }

            if (param_list_expression != null) {
                function_call_expression += "(" + param_list_expression + ")";
            } else {
                function_call_expression += "()";
            }

            function_call_expression += ";\n";
            node_expression.put(ctx, function_call_expression);
        }

        public void exitFunction_call_param_list(RyParser.Function_call_param_listContext ctx) {
            String function_call_params_expression = node_expression.get(ctx.getChild(0));
            int params_number = function_param_number.get(ctx.getChild(0));
            function_param_number.put(ctx, params_number);
            node_expression.put(ctx, function_call_params_expression);
        }

        public void exitFunction_call_params(RyParser.Function_call_paramsContext ctx) {
            int child_count = ctx.getChildCount();
            String function_call_params_expression = "";

            for (int i = 0;i < child_count;i++) {
                if (ctx.getChild(i) != ctx.COMMA()) {
                    if (i != child_count - 1) {
                        function_call_params_expression += node_expression.get(ctx.getChild(i)) + ", ";
                    } else {
                        function_call_params_expression += node_expression.get(ctx.getChild(i));
                    }
                }
            }

            int all_child_num = 0;
            if (child_count > 1) {
                int nested_param_num = function_param_number.get(ctx.getChild(0));
                int single_param_num = function_param_number.get(ctx.getChild(2));
                all_child_num = nested_param_num + single_param_num;
            } else {
                all_child_num = 1;
            }

            function_param_number.put(ctx, all_child_num);
            node_expression.put(ctx, function_call_params_expression);
        }

        public void exitFunction_param(RyParser.Function_paramContext ctx) {
            String function_param_expression = node_expression.get(ctx.getChild(0));
            node_expression.put(ctx, function_param_expression);
            function_param_number.put(ctx, 1);
        }

        public void exitFunction_call_unnamed_param(RyParser.Function_call_unnamed_paramContext ctx) {
            String unnamed_param_expression = node_expression.get(ctx.getChild(0));

            node_expression.put(ctx, unnamed_param_expression);
        }

        public void exitFunction_call_named_param(RyParser.Function_call_named_paramContext ctx) {
            String named_param_expression = node_expression.get(ctx.getChild(0));
            node_expression.put(ctx, named_param_expression);
        }

        public void exitFunction_call_assignment(RyParser.Function_call_assignmentContext ctx) {
            String function_called_assignment_expression = node_expression.get(ctx.getChild(0));
            node_expression.put(ctx ,function_called_assignment_expression);
        }
        // ================================  Float  =======================================

        public void exitFloat_assignment(RyParser.Float_assignmentContext ctx) {
            String var = ctx.var_id.getText();
            String float_result_expression = node_expression.get(ctx.getChild(2));
            String float_assignment_expression;

            switch(ctx.op.getType()) {
                case RyParser.ASSIGN:
                    float_assignment_expression = "Instance ${var} = ${float_result_expression};";
                    break;
                default:
                    String assignOprText = getAssignOprText(ctx.op.getText());
                    float_assignment_expression = "${var}.${assignOprText}(${float_result_expression})\n";
                    break;
            }

            node_expression.put(ctx, float_assignment_expression);
        }

        public void exitFloat_result(RyParser.Float_resultContext ctx) {
            if (ctx.getChildCount() == 3 && ctx.op != null) {
                String left_expression = node_expression.get(ctx.getChild(0));
                String right_expression = node_expression.get(ctx.getChild(2));
                String float_expression = generateResultExpression(left_expression, ctx.op.getText(), right_expression);

                switch(ctx.op.getType()) {
                    case RyParser.PLUS:
                        // store the type of this specific node
                        value_store.put(ctx, "Float");
                        break;
                    case RyParser.MINUS:
                        value_store.put(ctx, "Float");
                        break;
                    case RyParser.MUL:
                        value_store.put(ctx, "Float");
                        break;
                    case RyParser.DIV:
                        value_store.put(ctx, "Float");
                        break;
                    case RyParser.MOD:
                        value_store.put(ctx, "Float");
                        break;
                }
                node_expression.put(ctx, float_expression);
            }
            else if (ctx.getChildCount() == 1) {
                String float_expression = "new Instance(Global._RyFloat, ${node_expression.get(ctx.getChild(0))})";
                node_expression.put(ctx, float_expression);
                value_store.put(ctx, "Float");
            }
        }

        public void exitFloat_t(RyParser.Float_tContext ctx) {
            node_expression.put(ctx, ctx.FLOAT().getText());
            value_store.put(ctx, value_store.get(ctx.getChild(0)));
        }


// ================================  String  =======================================

        public void exitString_assignment(RyParser.String_assignmentContext ctx) {
            String var = node_expression.get(ctx.getChild(0));
            String string_result_expression = node_expression.get(ctx.getChild(2));
            String string_assignment_expression = "";
            switch(ctx.op.getType()) {
                case RyParser.ASSIGN:
                    string_assignment_expression = "Instance ${var} = ${string_assignment_expression};";
                    break;
                default:
                    String assignOprText = getAssignOprText(ctx.op.getText());
                    string_assignment_expression = "${var}.${assignOprText}(${string_result_expression});";
                    break;
            }

            node_expression.put(ctx, string_assignment_expression);
        }

        public void exitString_result(RyParser.String_resultContext ctx) {
            // 3 * "123"
            int times = 0;
            String left = "";
            String right = "";
            String strToRepeat = "";

            if (ctx.getChildCount() == 3 && ctx.op != null) {
                // in ruby, only "Hello"*3 is allowed
                String string_expression = null;
                String valToStore = null;

                switch(ctx.op.getType()) {
                    case RyParser.MUL:
                        // times = int_values.get(ctx.getChild(2));
                        strToRepeat = node_expression.get(ctx.getChild(0));
                        // directly evaluate here because java does not has string multiple feature
                        valToStore = repeat(strToRepeat, times);
                        string_expression = "new Instance(Global._RyString,\"${valToStore}\")";

                        node_expression.put(ctx, string_expression);
                        break;
                    case RyParser.PLUS:
                        String left_expression = node_expression.get(ctx.getChild(0));
                        String right_expression = node_expression.get(ctx.getChild(2));
                        string_expression = generateResultExpression(left_expression, ctx.op.getText(), right_expression);
                        valToStore = (String) left + right;
                        node_expression.put(ctx, string_expression);
                        break;
                }
            }
            else if (ctx.getChildCount() == 1) {
                String literal_t_expr = node_expression.get(ctx.getChild(0));
                String string_expression = String.format("new RyString( %s, %d)", literal_t_expr, RyParser.LITERAL);
                node_expression.put(ctx, string_expression);
            }
        }

        public void exitLiteral_t(RyParser.Literal_tContext ctx) {
            int str_len = ctx.LITERAL().getText().length();
            node_expression.put(ctx, ctx.LITERAL().getText().substring(1, str_len - 1));
            value_store.put(ctx, value_store.get(ctx.getChild(0)));
        }

        // ================================  Dynamic  =======================================
        public void exitDynamic_assignment(RyParser.Dynamic_assignmentContext ctx) {
            String left_expression = node_expression.get(ctx.getChild(0));
            String right_expression = node_expression.get(ctx.getChild(2));
            String dynamic_assignment_expression = "";

            if (ctx.op.getText().equals("=")) {
                dynamic_assignment_expression = "Value " + left_expression + " = " + right_expression;
            } else {
                String opr_text = getOprText(ctx.op.getText());
                dynamic_assignment_expression = generateResultExpression(left_expression, opr_text,right_expression);
            }

            dynamic_assignment_expression += ";";
            node_expression.put(ctx, dynamic_assignment_expression);
        }

        public void exitDynamic_result(RyParser.Dynamic_resultContext ctx) {
            if (ctx.getChildCount() == 3 && ctx.op != null) {
                String var = node_expression.get(ctx.getChild(0));
                String dynamic_result_expression = node_expression.get(ctx.getChild(2));

                String opr_text = getOprText(ctx.op.getText());
                String id_expression = generateResultExpression(var, opr_text, dynamic_result_expression);
                node_expression.put(ctx, id_expression);
            }
            else if (ctx.getChildCount() == 1) {
                String id_expression = node_expression.get(ctx.getChild(0));
                node_expression.put(ctx, id_expression);
            }
        }

        public void exitDynamic(RyParser.DynamicContext ctx) {
            String id_expression = node_expression.get(ctx.getChild(0));
            if (ctx.getChild(0) instanceof RyParser.Function_call_assignmentContext) {
                // if is function call, strip the last char
                id_expression = id_expression.substring(0, id_expression.length() - 2);
            }
            node_expression.put(ctx, id_expression);
        }

        public void exitId(RyParser.IdContext ctx) {
            String id_expression = ctx.ID().getText();
            node_expression.put(ctx, id_expression);
        }

        // ================================  If statement  =======================================

        public void exitIf_statement(RyParser.If_statementContext ctx) {
            String comp_expression = node_expression.get(ctx.getChild(1));
            String statement_body_expression = node_expression.get(ctx.getChild(3));
            String if_expression = String.format("if (%s) {\n \t%s \n}", comp_expression,statement_body_expression);
            // either end or else if
            if (ctx.getChild(4) instanceof RyParser.Else_tokenContext) {
                String else_statement_body_expression =  node_expression.get(ctx.getChild(6));
                String if_else_expression = String.format("%s else {\n \t%s \n}",if_expression, else_statement_body_expression);
                node_expression.put(ctx, if_else_expression);
            } else if (ctx.getChild(4) instanceof RyParser.Elsif_statementContext) {
                String else_if_expression = node_expression.get(ctx.getChild(4));
                // handle the form of elseif in the exitIf_else func
                String if_elsif_expression = String.format("%s%s",if_expression, else_if_expression);
                node_expression.put(ctx, if_elsif_expression);
            } else {
                node_expression.put(ctx, if_expression);
            }
        }

        public void exitCond_expression(RyParser.Cond_expressionContext ctx) {
            String cond_expression = node_expression.get(ctx.getChild(0));
            node_expression.put(ctx, cond_expression);
        }

        public void exitComparison_list(RyParser.Comparison_listContext ctx) {
            String comp_list_expression = "";
            int child_count = ctx.getChildCount();
            if (child_count > 1) {
                for (int i = 0;i < child_count; i++) {
                    if (i % 2 != 0) {
                        comp_list_expression += ctx.getChild(i).getText();
                    } else {
                        comp_list_expression += ("(" + node_expression.get(ctx.getChild(i)) + ")");
                    }
                }
            } else {
                comp_list_expression = node_expression.get(ctx.getChild(0));
            }

            node_expression.put(ctx, comp_list_expression);
        }

        public void exitComparison(RyParser.ComparisonContext ctx) {
            if (ctx.getChildCount() == 3 && ctx.op != null) {
                String left_expression = node_expression.get(ctx.getChild(0));
                String right_expression = node_expression.get(ctx.getChild(2));

                String comp_op_text = getCompareText(ctx.op.getText());
                String comp_var_expression = left_expression+".compareTo(" + right_expression + ") " + comp_op_text;

                node_expression.put(ctx, comp_var_expression);
            } else if (ctx.getChildCount() == 1 ) {
                node_expression.put(ctx, node_expression.get(ctx.getChild(0)));
            }
        }

        public void exitComp_var(RyParser.Comp_varContext ctx) {
            String comp_var_expression = node_expression.get(ctx.getChild(0));
            node_expression.put(ctx, comp_var_expression);
        }

        public void exitAll_result(RyParser.All_resultContext ctx) {
            String all_expression = node_expression.get(ctx.getChild(0));
            node_expression.put(ctx, all_expression);
        }


        // ================================  ElseIf statement  =====================================
        public void exitElsif_statement(RyParser.Elsif_statementContext ctx) {
            String elseif_expression = node_expression.get(ctx.getChild(0));
            node_expression.put(ctx, elseif_expression);
        }

        public void exitIf_elsif_statement(RyParser.If_elsif_statementContext ctx) {
            int child_num = ctx.getChildCount();
            String cond_expression = node_expression.get(ctx.getChild(1));
            String statement_body_expression = node_expression.get(ctx.getChild(3));
            String if_elseif_statement_body = String.format("else if(%s) {\n %s \n}", cond_expression, statement_body_expression);

            if (child_num == 4) {
                node_expression.put(ctx, if_elseif_statement_body);
            } else {
                if (ctx.getChild(4) instanceof RyParser.Else_tokenContext) {
                    String else_statement_expression =  node_expression.get(ctx.getChild(6));
                    String if_elseif_else_expression = String.format("%s else {\n %s \n}", if_elseif_statement_body, else_statement_expression);
                    node_expression.put(ctx, if_elseif_else_expression);
                } else if (ctx.getChild(4) instanceof RyParser.If_elsif_statementContext) {
                    String nested_elseif_expression = node_expression.get(ctx.getChild(4));
                    String if_elseif_nested_expression = String.format("%s%s", if_elseif_statement_body, nested_elseif_expression);
                    node_expression.put(ctx, if_elseif_nested_expression);
                }
            }
        }

        public void exitElse_token(RyParser.Else_tokenContext ctx) {
            String else_token_expression = ctx.ELSE().getText();
            node_expression.put(ctx, else_token_expression);
        }

        // ================================  For statement  =====================================
        public void exitFor_statement(RyParser.For_statementContext ctx) {
            int low = Integer.parseInt(ctx.getChild(4).getText());
            int high = Integer.parseInt(ctx.getChild(6).getText());
            String var = ctx.getChild(1).getText();
            String statement_body_expression = node_expression.get(ctx.getChild(9));

            String for_statement_expression;
            if (low < high) {
                for_statement_expression = "\tfor (int " + var + " = " + low + "; a <= " + high + "; " + var + "++)";
            } else {
                for_statement_expression = "\tfor (int " + var + " = " + high + "; a <= " + low + "; " + var + "++)";
            }

            for_statement_expression += "{\t\t" + statement_body_expression + "\n\t}";
            node_expression.put(ctx, for_statement_expression);
        }

        // ================================  While statement  =====================================
        public void exitWhile_statement(RyParser.While_statementContext ctx) {
            String cond_expression = node_expression.get(ctx.getChild(1));
            String statement_body_expression = node_expression.get(ctx.getChild(3));
            String while_expression = "while (" + cond_expression + ") {\t" + statement_body_expression + "\n}";

            node_expression.put(ctx, while_expression);
        }


        // ================================  Statement body =====================================
        public void exitStatement_body(RyParser.Statement_bodyContext ctx) {
            String statement_body_expression = node_expression.get(ctx.getChild(0));
            node_expression.put(ctx, statement_body_expression);
        }

        public void exitStatement_expression_list(RyParser.Statement_expression_listContext ctx) {
            // store all expression from children in differen line
            int child_list_len = ctx.getChildCount() - 1;  // -- eliminate terminator
            String statement_expression_list_expression = "";
            for (int i = 0; i < child_list_len ; i++ ) {
                statement_expression_list_expression += ( "\t" + node_expression.get(ctx.getChild(i)) + "\n" );
            }

            node_expression.put(ctx, statement_expression_list_expression);
        }

        public void exitExpression_list(RyParser.Expression_listContext ctx) {
            int child_list_len = ctx.getChildCount() - 1;  // -- eliminate terminator
            String expression_list_expression = "";
            for (int i = 0; i < child_list_len ; i++ ) {
                // concatenation
                String child_expression = node_expression.get(ctx.getChild(i));
                if (!child_expression.contains("static")) {
                    expression_list_expression += ("\n" + child_expression);
                }
            }

            node_expression.put(ctx, expression_list_expression);
        }

        public void exitExpression(RyParser.ExpressionContext ctx) {
            // if the subexpression is a modifier
            if (ctx.cond_modifier != null) {
                String modifier = ctx.cond_modifier.getText();
                String expression_to_eval = node_expression.get(ctx.getChild(0));
                String cond_expression = node_expression.get(ctx.getChild(2));
                String modifierExpr = String.format("%s (%s) {\n %s \n}", modifier, cond_expression, expression_to_eval);
                node_expression.put(ctx, modifierExpr);
            }else {
                String child_expression = node_expression.get(ctx.getChild(0));
                node_expression.put(ctx, child_expression);
            }
        }


        // ================================ Values =====================================
        public void exitRvalue(RyParser.RvalueContext ctx) {
            String child_expression = node_expression.get(ctx.getChild(0));
            node_expression.put(ctx, child_expression);
        }

        public void exitLvalue(RyParser.LvalueContext ctx) {
            String child_expression = node_expression.get(ctx.getChild(0));
            node_expression.put(ctx, child_expression);
        }

        //  ================================ Puts call =====================================
        public void exitPuts_call(RyParser.Puts_callContext ctx) {
            String expression_to_print = node_expression.get(ctx.getChild(1));
            node_expression.put(ctx, "Formatter.classCastHelper(" + expression_to_print + ");\n");
        }
    }



    public static String retriveDir(String fileName) {
        String[] strs = fileName.split("/");
        return strs[strs.length - 1];
    }

    // ================================  Main Method  =====================================
    public static void main(String[] args) throws Exception {
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

        ParseTreeWalker walker = new ParseTreeWalker();

        Evaluator eval = new Evaluator();
        walker.walk(eval, tree);

        // errors checking
        ByteArrayOutputStream errors = eval.stack_out_stream.pop();
        String errors_msg = errors.toString();
        if (!errors_msg.equals("")) {
            System.out.println(errors_msg);
            return;
        }

        ByteArrayOutputStream out_function = eval.stack_out_stream.pop();
        ByteArrayOutputStream out_expressions = eval.stack_out_stream.pop();

        // put code into file
        FileWriter fw = new FileWriter(currentDir + "/" + genName + ".java");
//        PrintWriter pw = new PrintWriter(fw);


        String wholeScript = Formatter.wrapClass(Formatter.wrapFunctions(Formatter.wrapExpressions(out_expressions.toString()), out_function.toString()), genName);
//        pw.print(wholeScript);
//        pw.close();
        System.out.println(wholeScript);
    }
}
