package compiler.listeners

import compiler.RyBaseListener
import compiler.RyCompilerProxy
import compiler.RyParser


class RyFloatListener extends RyBaseListener {
    // ================================  Float  =======================================
    public void exitFloat_assignment(RyParser.Float_assignmentContext ctx) {
        String var = ctx.var_id.getText();
        String float_result_expression = RyCompilerProxy.node_expression.get(ctx.getChild(2));
        String float_assignment_expression;

        switch(ctx.op.getType()) {
            case RyParser.ASSIGN:
                if (RyCompilerProxy.var_definition.contains(var)) {
                    float_assignment_expression = "${var} = ${float_result_expression}";
                } else {
                    float_assignment_expression = "Instance ${var} = ${float_result_expression}";
                    RyCompilerProxy.var_definition.add(var);
                }
                break;
            default:
                String assignOprText = RyCompilerProxy.getAssignOprText(ctx.op.getText());
                float_assignment_expression = "${var}.callmethod(\"${assignOprText}\",${float_result_expression})";
                break;
        }

        RyCompilerProxy.node_expression.put(ctx, float_assignment_expression);
    }

    public void exitFloat_result(RyParser.Float_resultContext ctx) {
        if (ctx.getChildCount() == 3 && ctx.op != null) {
            String left_expression = RyCompilerProxy.node_expression.get(ctx.getChild(0));
            String right_expression = RyCompilerProxy.node_expression.get(ctx.getChild(2));
            String float_expression = RyCompilerProxy.generateResultExpression(left_expression, ctx.op.getText(), right_expression);

            RyCompilerProxy.node_expression.put(ctx, float_expression);
        }
        else if (ctx.getChildCount() == 1) {
            String float_expression = "new Instance(Global._RyFloat, ${RyCompilerProxy.node_expression.get(ctx.getChild(0))})";
            RyCompilerProxy.node_expression.put(ctx, float_expression);
        }
    }

    public void exitFloat_t(RyParser.Float_tContext ctx) {
        RyCompilerProxy.node_expression.put(ctx, ctx.FLOAT().getText());
//        RyCompilerProxy.value_store.put(ctx, RyCompilerProxy.value_store.get(ctx.getChild(0)));
    }
}
