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
                float_assignment_expression = "Instance ${var} = ${float_result_expression};";
                break;
            default:
                String assignOprText = RyCompilerProxy.getAssignOprText(ctx.op.getText());
                float_assignment_expression = "${var}.${assignOprText}(${float_result_expression})\n";
                break;
        }

        RyCompilerProxy.node_expression.put(ctx, float_assignment_expression);
    }

    public void exitFloat_result(RyParser.Float_resultContext ctx) {
        if (ctx.getChildCount() == 3 && ctx.op != null) {
            String left_expression = RyCompilerProxy.node_expression.get(ctx.getChild(0));
            String right_expression = RyCompilerProxy.node_expression.get(ctx.getChild(2));
            String float_expression = RyCompilerProxy.generateResultExpression(left_expression, ctx.op.getText(), right_expression);

            switch(ctx.op.getType()) {
                case RyParser.PLUS:
                    // store the type of this specific node
                    RyCompilerProxy.value_store.put(ctx, "Float");
                    break;
                case RyParser.MINUS:
                    RyCompilerProxy.value_store.put(ctx, "Float");
                    break;
                case RyParser.MUL:
                    RyCompilerProxy.value_store.put(ctx, "Float");
                    break;
                case RyParser.DIV:
                    RyCompilerProxy.value_store.put(ctx, "Float");
                    break;
                case RyParser.MOD:
                    RyCompilerProxy.value_store.put(ctx, "Float");
                    break;
            }
            RyCompilerProxy.node_expression.put(ctx, float_expression);
        }
        else if (ctx.getChildCount() == 1) {
            String float_expression = "new Instance(Global._RyFloat, ${RyCompilerProxy.node_expression.get(ctx.getChild(0))})";
            RyCompilerProxy.node_expression.put(ctx, float_expression);
            RyCompilerProxy.value_store.put(ctx, "Float");
        }
    }

    public void exitFloat_t(RyParser.Float_tContext ctx) {
        RyCompilerProxy.node_expression.put(ctx, ctx.FLOAT().getText());
        RyCompilerProxy.value_store.put(ctx, RyCompilerProxy.value_store.get(ctx.getChild(0)));
    }
}
