package compiler.listeners

import compiler.RyCompilerProxy
import compiler.RyBaseListener
import compiler.RyParser

class RyCondListener extends RyBaseListener {
    // ================================  If statement  =======================================

    public void exitIf_statement(RyParser.If_statementContext ctx) {
        String comp_expression = RyCompilerProxy.node_expression.get(ctx.getChild(1));
        String statement_body_expression = RyCompilerProxy.node_expression.get(ctx.getChild(3));
        String if_expression = String.format("if (%s) {\n \t%s \n}", comp_expression,statement_body_expression);
        // either end or else if
        if (ctx.getChild(4) instanceof RyParser.Else_tokenContext) {
            String else_statement_body_expression =  RyCompilerProxy.node_expression.get(ctx.getChild(6));
            String if_else_expression = String.format("%s else {\n \t%s \n}",if_expression, else_statement_body_expression);
            RyCompilerProxy.node_expression.put(ctx, if_else_expression);
        } else if (ctx.getChild(4) instanceof RyParser.Elsif_statementContext) {
            String else_if_expression = RyCompilerProxy.node_expression.get(ctx.getChild(4));
            // handle the form of elseif in the exitIf_else func
            String if_elsif_expression = String.format("%s%s",if_expression, else_if_expression);
            RyCompilerProxy.node_expression.put(ctx, if_elsif_expression);
        } else {
            RyCompilerProxy.node_expression.put(ctx, if_expression);
        }
    }

    public void exitCond_expression(RyParser.Cond_expressionContext ctx) {
        String cond_expression = RyCompilerProxy.node_expression.get(ctx.getChild(0));
        RyCompilerProxy.node_expression.put(ctx, cond_expression);
    }

    public void exitComparison_list(RyParser.Comparison_listContext ctx) {
        String comp_list_expression = "";
        int child_count = ctx.getChildCount();
        if (child_count > 1) {
            for (int i = 0;i < child_count; i++) {
                if (i % 2 != 0) {
                    comp_list_expression += ctx.getChild(i).getText();
                } else {
                    comp_list_expression += ("(" + RyCompilerProxy.node_expression.get(ctx.getChild(i)) + ")");
                }
            }
        } else {
            comp_list_expression = RyCompilerProxy.node_expression.get(ctx.getChild(0));
        }

        RyCompilerProxy.node_expression.put(ctx, comp_list_expression);
    }

    public void exitComparison(RyParser.ComparisonContext ctx) {
        if (ctx.getChildCount() == 3 && ctx.op != null) {
            String left_expression = RyCompilerProxy.node_expression.get(ctx.getChild(0));
            String right_expression = RyCompilerProxy.node_expression.get(ctx.getChild(2));

            String comp_op_text = RyCompilerProxy.getCompareText(ctx.op.getText());
            String comp_var_expression = left_expression+".compareTo(" + right_expression + ") " + comp_op_text;

            RyCompilerProxy.node_expression.put(ctx, comp_var_expression);
        } else if (ctx.getChildCount() == 1 ) {
            RyCompilerProxy.node_expression.put(ctx, RyCompilerProxy.node_expression.get(ctx.getChild(0)));
        }
    }

    public void exitComp_var(RyParser.Comp_varContext ctx) {
        String comp_var_expression = RyCompilerProxy.node_expression.get(ctx.getChild(0));
        RyCompilerProxy.node_expression.put(ctx, comp_var_expression);
    }

    public void exitAll_result(RyParser.All_resultContext ctx) {
        String all_expression = RyCompilerProxy.node_expression.get(ctx.getChild(0));
        RyCompilerProxy.node_expression.put(ctx, all_expression);
    }


    // ================================  ElseIf statement  =====================================
    public void exitElsif_statement(RyParser.Elsif_statementContext ctx) {
        String elseif_expression = RyCompilerProxy.node_expression.get(ctx.getChild(0));
        RyCompilerProxy.node_expression.put(ctx, elseif_expression);
    }

    public void exitIf_elsif_statement(RyParser.If_elsif_statementContext ctx) {
        int child_num = ctx.getChildCount();
        String cond_expression = RyCompilerProxy.node_expression.get(ctx.getChild(1));
        String statement_body_expression = RyCompilerProxy.node_expression.get(ctx.getChild(3));
        String if_elseif_statement_body = String.format("else if(%s) {\n %s \n}", cond_expression, statement_body_expression);

        if (child_num == 4) {
            RyCompilerProxy.node_expression.put(ctx, if_elseif_statement_body);
        } else {
            if (ctx.getChild(4) instanceof RyParser.Else_tokenContext) {
                String else_statement_expression =  RyCompilerProxy.node_expression.get(ctx.getChild(6));
                String if_elseif_else_expression = String.format("%s else {\n %s \n}", if_elseif_statement_body, else_statement_expression);
                RyCompilerProxy.node_expression.put(ctx, if_elseif_else_expression);
            } else if (ctx.getChild(4) instanceof RyParser.If_elsif_statementContext) {
                String nested_elseif_expression = RyCompilerProxy.node_expression.get(ctx.getChild(4));
                String if_elseif_nested_expression = String.format("%s%s", if_elseif_statement_body, nested_elseif_expression);
                RyCompilerProxy.node_expression.put(ctx, if_elseif_nested_expression);
            }
        }
    }

    public void exitElse_token(RyParser.Else_tokenContext ctx) {
        String else_token_expression = ctx.ELSE().getText();
        RyCompilerProxy.node_expression.put(ctx, else_token_expression);
    }

    // ================================  For statement  =====================================
    public void exitFor_statement(RyParser.For_statementContext ctx) {
        int low = Integer.parseInt(ctx.getChild(4).getText());
        int high = Integer.parseInt(ctx.getChild(6).getText());
        String var = ctx.getChild(1).getText();
        String statement_body_expression = RyCompilerProxy.node_expression.get(ctx.getChild(9));

        String for_statement_expression;
        if (low < high) {
            for_statement_expression = "\tfor (int " + var + " = " + low + "; a <= " + high + "; " + var + "++)";
        } else {
            for_statement_expression = "\tfor (int " + var + " = " + high + "; a <= " + low + "; " + var + "++)";
        }

        for_statement_expression += "{\t\t" + statement_body_expression + "\n\t}";
        RyCompilerProxy.node_expression.put(ctx, for_statement_expression);
    }

    // ================================  While statement  =====================================
    public void exitWhile_statement(RyParser.While_statementContext ctx) {
        String cond_expression = RyCompilerProxy.node_expression.get(ctx.getChild(1));
        String statement_body_expression = RyCompilerProxy.node_expression.get(ctx.getChild(3));
        String while_expression = "while (" + cond_expression + ") {\t" + statement_body_expression + "\n}";

        RyCompilerProxy.node_expression.put(ctx, while_expression);
    }


    // ================================  Statement body =====================================
    public void exitStatement_body(RyParser.Statement_bodyContext ctx) {
        String statement_body_expression = RyCompilerProxy.node_expression.get(ctx.getChild(0));
        RyCompilerProxy.node_expression.put(ctx, statement_body_expression);
    }

    public void exitStatement_expression_list(RyParser.Statement_expression_listContext ctx) {
        // store all expression from children in differen line
        int child_list_len = ctx.getChildCount() - 1;  // -- eliminate terminator
        String statement_expression_list_expression = "";
        for (int i = 0; i < child_list_len ; i++ ) {
            statement_expression_list_expression += ( "\t" + RyCompilerProxy.node_expression.get(ctx.getChild(i)) + "\n" );
        }

        RyCompilerProxy.node_expression.put(ctx, statement_expression_list_expression);
    }

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
            RyCompilerProxy.node_expression.put(ctx, child_expression);
        }
    }
}
