package compiler.listeners

import compiler.RyCompilerProxy
import compiler.RyBaseListener
import compiler.RyParser

class RyStringListener extends RyBaseListener{

    public void exitString_assignment(RyParser.String_assignmentContext ctx) {
        String var = RyCompilerProxy.node_expression.get(ctx.getChild(0));
        String string_result_expression = RyCompilerProxy.node_expression.get(ctx.getChild(2));
        String string_assignment_expression = "";
        switch(ctx.op.getType()) {
            case RyParser.ASSIGN:
                string_assignment_expression = "Instance ${var} = ${string_assignment_expression};";
                break;
            default:
                String assignOprText = RyCompilerProxy.getAssignOprText(ctx.op.getText());
                string_assignment_expression = "${var}.${assignOprText}(${string_result_expression});";
                break;
        }

        RyCompilerProxy.node_expression.put(ctx, string_assignment_expression);
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

            switch(ctx.op.getType()) {
                case RyParser.MUL:
                    // times = int_values.get(ctx.getChild(2));
                    strToRepeat = RyCompilerProxy.node_expression.get(ctx.getChild(0));
                    // directly evaluate here because java does not has string multiple feature
                    string_expression = "new Instance(Global._RyString,\"${strToRepeat * times}\")";

                    RyCompilerProxy.node_expression.put(ctx, string_expression);
                    break;
                case RyParser.PLUS:
                    String left_expression = RyCompilerProxy.node_expression.get(ctx.getChild(0));
                    String right_expression = RyCompilerProxy.node_expression.get(ctx.getChild(2));
                    string_expression = RyCompilerProxy.generateResultExpression(left_expression, ctx.op.getText(), right_expression);
                    RyCompilerProxy.valToStore = (String) left + right;
                    RyCompilerProxy.node_expression.put(ctx, string_expression);
                    break;
            }
        }
        else if (ctx.getChildCount() == 1) {
            String literal_t_expr = RyCompilerProxy.node_expression.get(ctx.getChild(0));
            String string_expression = String.format("new RyString( %s, %d)", literal_t_expr, RyParser.LITERAL);
            RyCompilerProxy.node_expression.put(ctx, string_expression);
        }
    }

    public void exitLiteral_t(RyParser.Literal_tContext ctx) {
        int str_len = ctx.LITERAL().getText().length();
        RyCompilerProxy.node_expression.put(ctx, ctx.LITERAL().getText().substring(1, str_len - 1));
        RyCompilerProxy.value_store.put(ctx, RyCompilerProxy.value_store.get(ctx.getChild(0)));
    }
}
