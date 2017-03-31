package compiler.listeners

import compiler.CompileUtils
import compiler.RyCompilerProxy
import compiler.RyBaseListener
import compiler.RyParser

class RyStringListener extends RyBaseListener{

    public void exitString_result(RyParser.String_resultContext ctx) {
        String left_expr = "";
        String right_expr = "";
        String strToRepeat = "";

        if (ctx.getChildCount() == 3 && ctx.op != null) {
            // in ruby, only "Hello"*3 is allowed
            String string_expression = null;
            // get operation text "add" or "multiply"
            String oprText = CompileUtils.getOprText(ctx.op.getText());
            // operation implementation is delegate to Ruby Object Model
            left_expr = RyCompilerProxy.node_expression.get(ctx.getChild(0));
            right_expr = RyCompilerProxy.node_expression.get(ctx.getChild(0));
            string_expression = CompileUtils.generateResultExpression(left_expr, oprText, right_expr);
            RyCompilerProxy.node_expression.put(ctx, string_expression);
        }
        else if (ctx.getChildCount() == 1) {
            String literal_t_expr = RyCompilerProxy.node_expression.get(ctx.getChild(0));
            String string_expression = "new Instance(class_manager.getCls(\"RyString\"), [\"__default__\":${literal_t_expr}])";
            RyCompilerProxy.node_expression.put(ctx, string_expression);
        }
    }

    public void exitLiteral_t(RyParser.Literal_tContext ctx) {
        int str_len = ctx.LITERAL().getText().length();
//        RyCompilerProxy.node_expression.put(ctx, ctx.LITERAL().getText().substring(1, str_len - 1));
        RyCompilerProxy.node_expression.put(ctx, ctx.LITERAL().getText());
    }
}
