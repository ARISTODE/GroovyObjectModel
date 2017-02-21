package compiler.listeners

import compiler.RyBaseListener
import compiler.RyCompilerProxy
import compiler.RyParser

class RyValueListener extends RyBaseListener{
    public void exitRvalue(RyParser.RvalueContext ctx) {
        String child_expression = RyCompilerProxy.node_expression.get(ctx.getChild(0));
        RyCompilerProxy.node_expression.put(ctx, child_expression);
    }

    public void exitLvalue(RyParser.LvalueContext ctx) {
        String child_expression = RyCompilerProxy.node_expression.get(ctx.getChild(0));
        RyCompilerProxy.node_expression.put(ctx, child_expression);
    }
}
