package compiler.listeners

import compiler.RyCompilerProxy
import compiler.RyBaseListener
import compiler.RyParser


class RyFunctionListener extends RyBaseListener {
    public void exitFunction_definition(RyParser.Function_definitionContext ctx) {
        String function_header_expression = RyCompilerProxy.node_expression.get(ctx.getChild(0));
        String function_body_expression = RyCompilerProxy.node_expression.get(ctx.getChild(1));
        String function_definition_expression = "public static Value " + function_header_expression + function_body_expression;
        RyCompilerProxy.node_expression.put(ctx, function_definition_expression);
        RyCompilerProxy.printToFunctionStream(function_definition_expression + "\n");
    }

    public void exitFunction_header(RyParser.Function_headerContext ctx) {
        String function_header_expression = "";
        String function_name = RyCompilerProxy.node_expression.get(ctx.getChild(1));
        int param_amount = 0;
        // if has params
        if (ctx.getChild(2).getText().length() > 2) {
            String function_params_expression = RyCompilerProxy.node_expression.get(ctx.getChild(2));
            function_header_expression = function_name + function_params_expression;
            param_amount = RyCompilerProxy.derieveParamNum(function_params_expression);
        } else {
            function_header_expression = function_name + "()";
        }

        // record function definition
        RyCompilerProxy.function_definition.put(function_name, param_amount);
        RyCompilerProxy.node_expression.put(ctx, function_header_expression);
    }

    public void exitFunction_name(RyParser.Function_nameContext ctx) {
        String function_name_expression = RyCompilerProxy.node_expression.get(ctx.getChild(0));
        RyCompilerProxy.node_expression.put(ctx, function_name_expression);
    }

    public void exitFunction_params(RyParser.Function_paramsContext ctx) {
        String all_params_expression = RyCompilerProxy.node_expression.get(ctx.getChild(1));
        RyCompilerProxy.node_expression.put(ctx, "(" + all_params_expression + ")");
    }

    public void exitFunction_definition_param_list(RyParser.Function_definition_param_listContext ctx) {
        int child_count = ctx.getChildCount();
        String all_param_expression = "";
        if (child_count == 1) {
            all_param_expression = RyCompilerProxy.node_expression.get(ctx.getChild(0));
        } else {
            for (int i = 0;i < child_count;i++) {
                if (ctx.getChild(i) != ctx.COMMA()) {
                    if (i != child_count - 1) {
                        all_param_expression += RyCompilerProxy.node_expression.get(ctx.getChild(i)) + ", ";
                    } else {
                        all_param_expression += RyCompilerProxy.node_expression.get(ctx.getChild(i));
                    }
                }
            }
        }

        RyCompilerProxy.node_expression.put(ctx, all_param_expression);
    }

    public void exitFunction_definition_param_id(RyParser.Function_definition_param_idContext ctx) {
        String param_id_expression = "Value " + RyCompilerProxy.node_expression.get(ctx.getChild(0));
        RyCompilerProxy.node_expression.put(ctx, param_id_expression);
    }

    public void exitFunction_body(RyParser.Function_bodyContext ctx) {
        String expression_list_expression = RyCompilerProxy.node_expression.get(ctx.getChild(0));
        // judge if the last expression is return statement
        int child_count = ctx.getChildCount();
        if (!ctx.getChild(child_count - 1).getText().contains("return")) {
            expression_list_expression += String.format("return (%s)", RyCompilerProxy.node_expression.get(ctx.getChild(child_count-2)));
        }
        RyCompilerProxy.node_expression.put(ctx, "{ \t" + expression_list_expression + "\n}");
    }

    public void exitReturn_statement(RyParser.Return_statementContext ctx) {
        String all_result_expression = RyCompilerProxy.node_expression.get(ctx.getChild(1));
        String return_expression = "return " + all_result_expression + ";";
        RyCompilerProxy.node_expression.put(ctx, return_expression);
    }

    // ================================  Function call  =======================================
    public void exitFunction_call(RyParser.Function_callContext ctx) {
        String function_call_expression = "";
        String function_name = RyCompilerProxy.node_expression.get(ctx.getChild(0));
        String param_list_expression = RyCompilerProxy.node_expression.get(ctx.getChild(2));
        function_call_expression += function_name;

        int param_amount = RyCompilerProxy.function_param_number.get(ctx.getChild(2)) == null ? 0 : function_param_number.get(ctx.getChild(2));
        int expect_param_amount = RyCompilerProxy.function_definition.get(function_name);

        if (param_amount != expect_param_amount) {
            RyCompilerProxy.printToErrorStream("Argument number error: " + "at function " + function_name + " expect " + expect_param_amount + " params, but get " + param_amount + " params");
        }

        if (param_list_expression != null) {
            function_call_expression += "(" + param_list_expression + ")";
        } else {
            function_call_expression += "()";
        }

        function_call_expression += ";\n";
        RyCompilerProxy.node_expression.put(ctx, function_call_expression);
    }

    public void exitFunction_call_param_list(RyParser.Function_call_param_listContext ctx) {
        String function_call_params_expression = RyCompilerProxy.node_expression.get(ctx.getChild(0));
        int params_number = RyCompilerProxy.function_param_number.get(ctx.getChild(0));
        RyCompilerProxy.function_param_number.put(ctx, params_number);
        RyCompilerProxy.node_expression.put(ctx, function_call_params_expression);
    }

    public void exitFunction_call_params(RyParser.Function_call_paramsContext ctx) {
        int child_count = ctx.getChildCount();
        String function_call_params_expression = "";

        for (int i = 0;i < child_count;i++) {
            if (ctx.getChild(i) != ctx.COMMA()) {
                if (i != child_count - 1) {
                    function_call_params_expression += RyCompilerProxy.node_expression.get(ctx.getChild(i)) + ", ";
                } else {
                    function_call_params_expression += RyCompilerProxy.node_expression.get(ctx.getChild(i));
                }
            }
        }

        int all_child_num = 0;
        if (child_count > 1) {
            int nested_param_num = RyCompilerProxy.function_param_number.get(ctx.getChild(0));
            int single_param_num = RyCompilerProxy.function_param_number.get(ctx.getChild(2));
            all_child_num = nested_param_num + single_param_num;
        } else {
            all_child_num = 1;
        }

        RyCompilerProxy.function_param_number.put(ctx, all_child_num);
        RyCompilerProxy.node_expression.put(ctx, function_call_params_expression);
    }

    public void exitFunction_param(RyParser.Function_paramContext ctx) {
        String function_param_expression = RyCompilerProxy.node_expression.get(ctx.getChild(0));
        RyCompilerProxy.node_expression.put(ctx, function_param_expression);
        RyCompilerProxy.function_param_number.put(ctx, 1);
    }

    public void exitFunction_call_unnamed_param(RyParser.Function_call_unnamed_paramContext ctx) {
        String unnamed_param_expression = RyCompilerProxy.node_expression.get(ctx.getChild(0));

        RyCompilerProxy.node_expression.put(ctx, unnamed_param_expression);
    }

    public void exitFunction_call_named_param(RyParser.Function_call_named_paramContext ctx) {
        String named_param_expression = RyCompilerProxy.node_expression.get(ctx.getChild(0));
        RyCompilerProxy.node_expression.put(ctx, named_param_expression);
    }

    public void exitFunction_call_assignment(RyParser.Function_call_assignmentContext ctx) {
        String function_called_assignment_expression = RyCompilerProxy.node_expression.get(ctx.getChild(0));
        RyCompilerProxy.node_expression.put(ctx ,function_called_assignment_expression);
    }
}
