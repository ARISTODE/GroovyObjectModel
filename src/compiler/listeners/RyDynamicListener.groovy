package compiler.listeners

import compiler.RyCompilerProxy
import compiler.RyBaseListener
import compiler.RyParser

class RyDynamicListener extends RyBaseListener{
    public void exitDynamic_assignment(RyParser.Dynamic_assignmentContext ctx) {
        String left_expression = RyCompilerProxy.node_expression.get(ctx.getChild(0));
        String right_expression = RyCompilerProxy.node_expression.get(ctx.getChild(2));
        String dynamic_assignment_expression = "";

        if (ctx.op.getText().equals("=")) {
            dynamic_assignment_expression = "Value " + left_expression + " = " + right_expression;
        } else {
            String opr_text = RyCompilerProxy.getOprText(ctx.op.getText());
            dynamic_assignment_expression = RyCompilerProxy.generateResultExpression(left_expression, opr_text,right_expression);
        }

        dynamic_assignment_expression += ";";
        RyCompilerProxy.node_expression.put(ctx, dynamic_assignment_expression);
    }

    public void exitDynamic_result(RyParser.Dynamic_resultContext ctx) {
        if (ctx.getChildCount() == 3 && ctx.op != null) {
            String var = RyCompilerProxy.node_expression.get(ctx.getChild(0));
            String dynamic_result_expression = RyCompilerProxy.node_expression.get(ctx.getChild(2));

            String opr_text = RyCompilerProxy.getOprText(ctx.op.getText());
            String id_expression = RyCompilerProxy.generateResultExpression(var, opr_text, dynamic_result_expression);
            RyCompilerProxy.node_expression.put(ctx, id_expression);
        }
        else if (ctx.getChildCount() == 1) {
            String id_expression = RyCompilerProxy.node_expression.get(ctx.getChild(0));
            RyCompilerProxy.node_expression.put(ctx, id_expression);
        }
    }

    public void exitDynamic(RyParser.DynamicContext ctx) {
        String id_expression = RyCompilerProxy.node_expression.get(ctx.getChild(0));
        if (ctx.getChild(0) instanceof RyParser.Function_call_assignmentContext) {
            // if is function call, strip the last char
            id_expression = id_expression.substring(0, id_expression.length() - 2);
        }
        RyCompilerProxy.node_expression.put(ctx, id_expression);
    }

    public void exitId(RyParser.IdContext ctx) {
        String id_expression = ctx.ID().getText();
        RyCompilerProxy.node_expression.put(ctx, id_expression);
    }
}
