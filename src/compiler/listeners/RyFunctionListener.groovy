package compiler.listeners

import compiler.RyCompilerProxy
import compiler.RyBaseListener
import compiler.RyParser
import org.antlr.v4.runtime.tree.ParseTree


class RyFunctionListener extends RyBaseListener {
    public void enterFunction_definition(RyParser.Function_definitionContext ctx) {
        String cls_name = RyCompilerProxy.class_definition.get(ctx.getParent());
        if (cls_name != null) {
            RyCompilerProxy.class_definition.put(ctx, cls_name);
        }
    }

    public void exitFunction_definition(RyParser.Function_definitionContext ctx) {
        String function_header_expression = RyCompilerProxy.node_expression.get(ctx.getChild(0));
        String function_body_expression = RyCompilerProxy.node_expression.get(ctx.getChild(1));
        String function_definition_expression = "${function_header_expression} ${function_body_expression} }";

        String cls_name = RyCompilerProxy.class_definition.get(ctx);

        String write_function_definition = RyCompilerProxy.write_cls_func(cls_name, function_definition_expression);

        // TODO: add initialize method
        // checking the hooking methods
        String func_name = RyCompilerProxy.node_expression.get(ctx.getChild(0).getChild(1));

        // cls_name is TopObject by default
        RyCompilerProxy.node_expression.put(ctx, write_function_definition);
    }

    public void exitFunction_header(RyParser.Function_headerContext ctx) {
        String function_header_expression = "";
        String function_name = RyCompilerProxy.node_expression.get(ctx.getChild(1));
        int param_amount = 0;
        // if has params
        if (ctx.getChild(2).getText().length() > 2) {
            String function_params_expression = RyCompilerProxy.node_expression.get(ctx.getChild(2));
//            function_header_expression = "def ${function_name} = { \n self, Object... args -> \n ${function_params_expression} \n"; // do parameter type casting
                function_header_expression = "\"${function_name}\", { \n self, Object... args -> \n ${function_params_expression} \n"; // do parameter type casting
        } else {
//            function_header_expression = "def ${function_name} = { \nself, Object... args -> \n";
                function_header_expression = "\"${function_name}\", { \nself, Object... args -> \n";
        }

        // record function definition, since the function params also store the information of params' length, so
        // retrive the length of params from there
        RyCompilerProxy.function_definition.put(function_name, RyCompilerProxy.function_definition.get(ctx.getChild(2)));
        RyCompilerProxy.node_expression.put(ctx, function_header_expression);
    }

    public void exitFunction_name(RyParser.Function_nameContext ctx) {
        String function_name_expression = ctx.func_name.getText();
        RyCompilerProxy.node_expression.put(ctx, function_name_expression);
    }

    public void exitFunction_params(RyParser.Function_paramsContext ctx) {
        String all_params_expression = RyCompilerProxy.node_expression.get(ctx.getChild(1));
        StringBuilder sb = new StringBuilder();
        String[] params;
        if (all_params_expression != null) {
            params = all_params_expression.split(",");
            for (int i = 0; i < params.length; i++) {
                sb.append("Instance ${params[i]} = (Instance)args[${i}];\n");
            }
        } else {
            params = new String[0];
        }

        // store the length of params here
        RyCompilerProxy.function_definition.put(ctx, params.length);
        RyCompilerProxy.node_expression.put(ctx, sb.toString());
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
                        all_param_expression += RyCompilerProxy.node_expression.get(ctx.getChild(i)) + ",";
                    } else {
                        all_param_expression += RyCompilerProxy.node_expression.get(ctx.getChild(i));
                    }
                }
            }
        }

        RyCompilerProxy.node_expression.put(ctx, all_param_expression);
    }

    public void exitFunction_definition_param_id(RyParser.Function_definition_param_idContext ctx) {
        String param_name = ctx.id_name.getText();
        RyCompilerProxy.node_expression.put(ctx, param_name);
    }

    public void enterFunction_body(RyParser.Function_bodyContext ctx) {
        String cls_name = RyCompilerProxy.class_definition.get(ctx.getParent());
        // store the class of the function
        RyCompilerProxy.class_definition.put(ctx, cls_name);
    }

    public void exitFunction_body(RyParser.Function_bodyContext ctx) {
        String expression_list_expression = RyCompilerProxy.node_expression.get(ctx.getChild(0));
        RyCompilerProxy.node_expression.put(ctx, "\t${expression_list_expression}\n");
    }

    public void enterReturn_statement(RyParser.Return_statementContext ctx) {
        String cls_name = RyCompilerProxy.class_definition.get(ctx.getParent());
        RyCompilerProxy.class_definition.put(ctx, cls_name);
    }

    public void exitReturn_statement(RyParser.Return_statementContext ctx) {
        // TODO: fix function call at normal situation back to instance manager pattern
        String all_result_expression = RyCompilerProxy.node_expression.get(ctx.getChild(1));
        String return_expression = "return ${all_result_expression}";
        RyCompilerProxy.node_expression.put(ctx, return_expression);
    }

    // ================================  Function call  =======================================
    public void exitFunction_call(RyParser.Function_callContext ctx) {
        String function_call_expression = "";
        String function_name = RyCompilerProxy.node_expression.get(ctx.getChild(0));
        String param_list_expression = RyCompilerProxy.node_expression.get(ctx.getChild(2));

        int param_amount = RyCompilerProxy.function_param_number.get(ctx.getChild(2)) == null ? 0 : RyCompilerProxy.function_param_number.get(ctx.getChild(2));
        int expect_param_amount = RyCompilerProxy.function_definition.get(function_name);

        if (param_amount != expect_param_amount) {
            RyCompilerProxy.printToErrorStream("Argument number error: " + "at function " + function_name + " expect " + expect_param_amount + " params, but get " + param_amount + " params");
        }

        if (param_list_expression != null) {
            function_call_expression = "instance_manager.current_obj().callmethod(\"${function_name}\",${param_list_expression})";
        } else {
            function_call_expression += "instance_manager.current_obj().callmethod(\"${function_name}\")";
        }

        RyCompilerProxy.node_expression.put(ctx, function_call_expression);
    }

    public void enterFunction_call_param_list(RyParser.Function_call_param_listContext ctx) {
       RyCompilerProxy.storeCls(ctx);
    }

    public void exitFunction_call_param_list(RyParser.Function_call_param_listContext ctx) {
        String function_call_params_expression = RyCompilerProxy.node_expression.get(ctx.getChild(0));
        int params_number = RyCompilerProxy.function_param_number.get(ctx.getChild(0));
        RyCompilerProxy.function_param_number.put(ctx, params_number);
        RyCompilerProxy.node_expression.put(ctx, function_call_params_expression);
    }

    public void enterFunction_call_params(RyParser.Function_call_paramsContext ctx) {
        RyCompilerProxy.storeCls(ctx);
    }

    public void exitFunction_call_params(RyParser.Function_call_paramsContext ctx) {
        int child_count = ctx.getChildCount();
        String function_call_params_expression = "";

        for (int i = 0;i < child_count;i++) {
            if (ctx.getChild(i) != ctx.COMMA()) {
                if (i != child_count - 1) {
                    // TODO: one nested instance manager call problem
                    function_call_params_expression += "${RyCompilerProxy.node_expression.get(ctx.getChild(i))},";
                } else {
//                    function_call_params_expression += "instance_manager.getInstance(${RyCompilerProxy.node_expression.get(ctx.getChild(i))})";
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

    public void enterFunction_param(RyParser.Function_paramContext ctx) {
        RyCompilerProxy.storeCls(ctx);
    }
    public void exitFunction_param(RyParser.Function_paramContext ctx) {
        String function_param_expression = RyCompilerProxy.node_expression.get(ctx.getChild(0));
        RyCompilerProxy.node_expression.put(ctx, function_param_expression);
        RyCompilerProxy.function_param_number.put(ctx, 1);
    }

    public void enterFunction_call_unnamed_param(RyParser.Function_call_unnamed_paramContext ctx) {
        RyCompilerProxy.storeCls(ctx);
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

    public void enterClass_function_call(RyParser.Class_function_callContext ctx) {
        String cls_name = RyCompilerProxy.class_definition.get(ctx.getParent());
        RyCompilerProxy.class_definition.put(ctx, cls_name);
    }

    // class function call
    public void exitClass_function_call(RyParser.Class_function_callContext ctx) {
        String caller_name = ctx.getChild(0) instanceof RyParser.VarContext ?
                ((RyParser.VarContext)ctx.getChild(0)).ID().getText() :
                ((RyParser.Class_nameContext)ctx.getChild(0)).constant().getText();

        String manager = ctx.getChild(0) instanceof RyParser.VarContext ? "instance_manager.getInstance(\"${caller_name}\")" : "class_manager.getCls(\"${caller_name}\")";
        String func_name = ctx.function_name().getText();

        // TODO: consider the case that carrying brackets
        String params_expression = RyCompilerProxy.node_expression.get(ctx.getChild(4));

        String class_function_call_expression = "";

        if (params_expression == null) {
            class_function_call_expression = "${manager}.callmethod(\"${func_name}\")";
        } else {
            class_function_call_expression = "${manager}.callmethod(\"${func_name}\", ${params_expression})";
        }

        RyCompilerProxy.node_expression.put(ctx, class_function_call_expression);
    }
}
