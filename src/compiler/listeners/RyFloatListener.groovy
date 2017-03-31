package compiler.listeners

import compiler.CompileUtils
import compiler.RyBaseListener
import compiler.RyCompilerProxy
import compiler.RyParser


class RyFloatListener extends RyBaseListener {
    //5.0 -> new Instance(class_manager.getCls("RyInteger"), ["__default__": 5.0]);
    public void exitFloat_result(RyParser.Float_resultContext ctx) {
        if (ctx.getChildCount() == 3 && ctx.op != null) {
            String left_expression = RyCompilerProxy.node_expression.get(ctx.getChild(0));
            String right_expression = RyCompilerProxy.node_expression.get(ctx.getChild(2));
            String opr_text = CompileUtils.getOprText(ctx.op.getText());
            //5.0 + 3.0 -> new Instance(class_manager.getCls("RyInteger"), ["__default__": 5.0]).
            // callmethod("add", new Instance(class_manager.getCls("RyInteger"), ["__default__": 3.0]));
            String float_expression = CompileUtils.generateResultExpression(left_expression, opr_text, right_expression);
            RyCompilerProxy.node_expression.put(ctx, float_expression);
        }
        else if (ctx.getChildCount() == 1) {
            String float_expression = "new Instance(class_manager.getCls(\"RyFloat\"), [\"__default__\":${RyCompilerProxy.node_expression.get(ctx.getChild(0))}])";
            RyCompilerProxy.node_expression.put(ctx, float_expression);
        }
    }

    public void exitFloat_t(RyParser.Float_tContext ctx) {
        RyCompilerProxy.node_expression.put(ctx, ctx.FLOAT().getText());
//        RyCompilerProxy.value_store.put(ctx, RyCompilerProxy.value_store.get(ctx.getChild(0)));
    }
}
