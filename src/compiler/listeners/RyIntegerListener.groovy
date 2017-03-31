package compiler.listeners

import compiler.CompileUtils
import compiler.RyBaseListener
import compiler.RyCompilerProxy
import compiler.RyParser

class RyIntegerListener extends RyBaseListener {
    // 5 -> new Instance(class_manager.getCls("RyInteger"), ["__default__": 5]);
    public void exitInt_result(RyParser.Int_resultContext ctx) {
        if (ctx.getChildCount() == 3 && ctx.op != null) {
            String left_expression = RyCompilerProxy.node_expression.get(ctx.getChild(0));
            String right_expression = RyCompilerProxy.node_expression.get(ctx.getChild(2));
            String opr_text = CompileUtils.getOprText(ctx.op.getText());
            String int_result_expression = CompileUtils.generateResultExpression(left_expression, opr_text, right_expression);
            RyCompilerProxy.node_expression.put(ctx, int_result_expression);
        } else {
            if (ctx.getChildCount() == 1) {
//                String int_result_expression = "new Instance(Global._RyInteger,${RyCompilerProxy.node_expression.get(ctx.getChild(0))})";
                String int_result_expression = "new Instance(class_manager.getCls(\"RyInteger\"), [\"__default__\":${RyCompilerProxy.node_expression.get(ctx.getChild(0))}])"
                RyCompilerProxy.node_expression.put(ctx, int_result_expression);
            }
        }
    }

    public void exitInt_t(RyParser.Int_tContext ctx) {
        String int_val_text = ctx.INT().getText();
        RyCompilerProxy.node_expression.put(ctx, int_val_text);
    }
}
