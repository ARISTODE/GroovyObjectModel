package compiler.listeners

import compiler.RyCompilerProxy
import compiler.RyBaseListener
import compiler.RyParser

class RyStringListener extends RyBaseListener{

    public void exitString_assignment(RyParser.String_assignmentContext ctx) {
        String var = RyCompilerProxy.node_expression.get(ctx.getChild(0));

        String string_result_expression = RyCompilerProxy.node_expression.get(ctx.getChild(2));
        String string_assignment_expression;

        switch(ctx.op.getType()) {
            case RyParser.ASSIGN:
                if (RyCompilerProxy.var_definition.contains(var)) {
                    string_assignment_expression = "${var} = ${string_result_expression}";
                } else {
                    string_assignment_expression = "Instance ${var} = ${string_result_expression}";
                    RyCompilerProxy.var_definition.add(var);
                }
                break;
            default:
                String assignOprText = RyCompilerProxy.getAssignOprText(ctx.op.getText());
                string_assignment_expression = "(Instance)${var}.callmethod(\"${assignOprText}\", ${string_result_expression})";
                break;
        }

        RyCompilerProxy.node_expression.put(ctx, string_assignment_expression);
    }

    public void exitString_result(RyParser.String_resultContext ctx) {
        String left_expr = "";
        String right_expr = "";
        String strToRepeat = "";

        if (ctx.getChildCount() == 3 && ctx.op != null) {
            // in ruby, only "Hello"*3 is allowed
            String string_expression = null;
            // get operation text "add" or "multiply"
            String oprText = RyCompilerProxy.getOprText(ctx.op.getText());
            // operation implementation is delegate to Ruby Object Model
            left_expr = RyCompilerProxy.node_expression.get(ctx.getChild(0));
            right_expr = RyCompilerProxy.node_expression.get(ctx.getChild(0));
            string_expression = "new Instance(Global._RyString, (${left_expr}).callmethod(\"${oprText}\", ${right_expr}))";
            RyCompilerProxy.node_expression.put(ctx, string_expression);
        }
        else if (ctx.getChildCount() == 1) {
            String literal_t_expr = RyCompilerProxy.node_expression.get(ctx.getChild(0));
            String string_expression = "new Instance(Global._RyString, ${literal_t_expr})";
            RyCompilerProxy.node_expression.put(ctx, string_expression);
        }
    }

    public void exitLiteral_t(RyParser.Literal_tContext ctx) {
        int str_len = ctx.LITERAL().getText().length();
//        RyCompilerProxy.node_expression.put(ctx, ctx.LITERAL().getText().substring(1, str_len - 1));
        RyCompilerProxy.node_expression.put(ctx, ctx.LITERAL().getText());
    }
}
