package compiler.listeners

import compiler.RyCompilerProxy
import compiler.RyBaseListener
import compiler.RyParser
import org.antlr.v4.runtime.tree.ParseTree

class RyExpressionListListener extends RyBaseListener{
    public void enterExpression_list(RyParser.Expression_listContext ctx) {
        // get the class name (default -> _TopObject)
        def node_info = RyCompilerProxy.node_compile_info.get(ctx.getParent());

        if (node_info != null) {
            RyCompilerProxy.storeNodeCompileInfo(ctx);
        } else {
            // store the information of class and the current scoping
            RyCompilerProxy.node_compile_info.put(ctx, [
                    "class": "TopObject",
                    "scope": "main"
            ]);
        }
    }

    public void exitExpression_list(RyParser.Expression_listContext ctx) {
        /* expression list won't use the node compile info itself, but the children of it will alter the datum
           according to the information given by expression list
        */
        int child_list_len = ctx.getChildCount() - 1;  // -- eliminate terminator
        StringBuilder expression_list_expression = new StringBuilder();

        int expr_pointer = 0;
        while (expr_pointer < child_list_len - 1) { // minus one since the terminator is removed
            // concatenation
            String child_expression = RyCompilerProxy.node_expression.get(ctx.getChild(expr_pointer));
//            if (!child_expression.contains("static")) {
                // processing expressions
            expression_list_expression.append("\t${child_expression}\n");
//            }
            expr_pointer++;
        }

        String return_statement = RyCompilerProxy.node_expression.get(ctx.getChild(expr_pointer));
        // check whether expressions are inside a function
        // TODO: fix return not append bug
        if (ctx.getParent().getRuleContext() instanceof RyParser.Function_bodyContext) {
            if (!return_statement.contains("return")) {
                expression_list_expression.append("return ${return_statement}");
            } else {
                expression_list_expression.append(return_statement);
            }
        } else {
            expression_list_expression.append(return_statement);
        }

        RyCompilerProxy.node_expression.put(ctx, expression_list_expression.toString());
    }
}

