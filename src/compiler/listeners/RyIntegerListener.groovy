package compiler.listeners

import compiler.RyBaseListener
import compiler.RyCompilerProxy
import compiler.RyParser

class RyIntegerListener extends RyBaseListener {
    public void exitInt_assignment(RyParser.Int_assignmentContext ctx) {
        String var = ctx.var_id.getText();
        String int_result_expression = RyCompilerProxy.node_expression.get(ctx.getChild(2));
        String int_assignment_expression;

        switch(ctx.op.getType()) {
            case RyParser.ASSIGN:
                if (RyCompilerProxy.var_definition.contains(var)) {
                    int_assignment_expression = "${var} = ${int_result_expression}";
                } else {
                    int_assignment_expression = "Instance ${var} = ${int_result_expression}";
                    RyCompilerProxy.var_definition.add(var);
                }
                break;
            default:
                String assignOprText = RyCompilerProxy.getAssignOprText(ctx.op.getText());
                int_assignment_expression = "${var}.callmethod(\"assignOprText\",${int_result_expression})";
                break;
        }

        RyCompilerProxy.node_expression.put(ctx, int_assignment_expression);
    }

    public void exitInt_result(RyParser.Int_resultContext ctx) {
        if (ctx.getChildCount() == 3 && ctx.op != null) {
            String left_expression = RyCompilerProxy.node_expression.get(ctx.getChild(0));
            String right_expression = RyCompilerProxy.node_expression.get(ctx.getChild(2));
            String opr_text = RyCompilerProxy.getOprText(ctx.op.getText());
            String int_result_expression = RyCompilerProxy.generateResultExpression(left_expression, opr_text, right_expression);
            RyCompilerProxy.node_expression.put(ctx, int_result_expression);
        } else {
            if (ctx.getChildCount() == 1) {
                String int_result_expression = "new Instance(Global._RyInteger,${RyCompilerProxy.node_expression.get(ctx.getChild(0))})";
                RyCompilerProxy.node_expression.put(ctx, int_result_expression);
            }
        }
    }

    public void exitInt_t(RyParser.Int_tContext ctx) {
        String int_val_text = ctx.INT().getText();
        RyCompilerProxy.node_expression.put(ctx, int_val_text);
    }
}
