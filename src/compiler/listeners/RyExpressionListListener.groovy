package compiler.listeners

import compiler.RyCompilerProxy
import compiler.RyBaseListener
import compiler.RyParser
import org.antlr.v4.runtime.tree.ParseTree

class RyExpressionListListener extends RyBaseListener{
    public void enterExpression_list(RyParser.Expression_listContext ctx) {
        // get the class name (default -> _TopObject)
        String cls_name = RyCompilerProxy.class_definition.get(ctx.getParent());
        if (cls_name != null) {
            // use the class name in child expression
            RyCompilerProxy.class_definition.put(ctx, cls_name);
        } else {
            // default write to the TopObject class
            RyCompilerProxy.class_definition.put(ctx, "TopObject");
        }
    }

    public void exitExpression_list(RyParser.Expression_listContext ctx) {
        int child_list_len = ctx.getChildCount() - 1;  // -- eliminate terminator
        StringBuilder expression_list_expression = new StringBuilder();

        int expr_pointer = 0;
        while (expr_pointer < child_list_len - 2) {
            // concatenation
            String child_expression = RyCompilerProxy.node_expression.get(ctx.getChild(expr_pointer));
//            if (!child_expression.contains("static")) {
                // processing expressions
            expression_list_expression.append("\t${child_expression}");
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

