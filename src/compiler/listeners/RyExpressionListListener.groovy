package compiler.listeners

import compiler.RyCompilerProxy
import compiler.RyBaseListener
import compiler.RyParser

class RyExpressionListListener extends RyBaseListener{
    public void exitExpression_list(RyParser.Expression_listContext ctx) {
        int child_list_len = ctx.getChildCount() - 1;  // -- eliminate terminator
        String expression_list_expression = "";
        for (int i = 0; i < child_list_len ; i++ ) {
            // concatenation
            String child_expression = RyCompilerProxy.node_expression.get(ctx.getChild(i));
            if (!child_expression.contains("static")) {
                expression_list_expression += ("\n" + child_expression);
            }
        }

        RyCompilerProxy.node_expression.put(ctx, expression_list_expression);
    }
}