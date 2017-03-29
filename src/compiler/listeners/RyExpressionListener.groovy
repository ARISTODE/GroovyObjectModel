package compiler.listeners

import compiler.RyCompilerProxy
import compiler.RyBaseListener
import compiler.RyParser

class RyExpressionListener extends RyBaseListener{
    public void enterExpression(RyParser.ExpressionContext ctx) {
        RyCompilerProxy.storeNodeCompileInfo(ctx);
    }

    public void exitExpression(RyParser.ExpressionContext ctx) {
        // if the subexpression is a modifier
        if (ctx.cond_modifier != null) {
            String modifier = ctx.cond_modifier.getText();
            String expression_to_eval = RyCompilerProxy.node_expression.get(ctx.getChild(0));
            String cond_expression = RyCompilerProxy.node_expression.get(ctx.getChild(2));
            String modifierExpr = String.format("%s (%s) {\n %s \n}", modifier, cond_expression, expression_to_eval);
            RyCompilerProxy.node_expression.put(ctx, modifierExpr);
        }else {
            String child_expression = RyCompilerProxy.node_expression.get(ctx.getChild(0));
            RyCompilerProxy.node_expression.put(ctx, "${child_expression}");
        }
    }
}
