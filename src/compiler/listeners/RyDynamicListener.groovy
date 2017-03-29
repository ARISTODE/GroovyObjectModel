package compiler.listeners

import ObjectModel.Instance
import compiler.RyCompilerProxy
import compiler.RyBaseListener
import compiler.RyParser
import org.antlr.v4.runtime.tree.ParseTree

class RyDynamicListener extends RyBaseListener{

    public void enterDynamic_result(RyParser.Dynamic_resultContext ctx) {
        RyCompilerProxy.storeNodeCompileInfo(ctx);
    }

    public void exitDynamic_result(RyParser.Dynamic_resultContext ctx) {
        String cls_name = RyCompilerProxy.class_definition.get(ctx.getParent());

        if (ctx.getChildCount() == 3 && ctx.op != null) {
            String var = RyCompilerProxy.node_expression.get(ctx.getChild(0));
            String dynamic_result_expression = RyCompilerProxy.node_expression.get(ctx.getChild(2));
            String opr_text = RyCompilerProxy.getOprText(ctx.op.getText());
            String id_expression = RyCompilerProxy.generateResultExpression(var, opr_text, dynamic_result_expression);
            RyCompilerProxy.node_expression.put(ctx, id_expression);
        }
        else if (ctx.getChildCount() == 1) {
            String var = RyCompilerProxy.node_expression.get(ctx.getChild(0));
            String id_expression = "";
            if (cls_name != "TopObject") {
                id_expression = var;
            } else {
                id_expression = "instance_manager.getInstance(\"${var}\")";
            }
            RyCompilerProxy.node_expression.put(ctx, id_expression);
        }
    }

    public void enterDynamic(RyParser.DynamicContext ctx) {
        RyCompilerProxy.storeNodeCompileInfo(ctx);
    }

    public void exitDynamic(RyParser.DynamicContext ctx) {
        String id_expression = ctx.var_id.getText();
        RyCompilerProxy.node_expression.put(ctx, id_expression);
    }

    public void enterAll_result(RyParser.All_resultContext ctx) {
       RyCompilerProxy.storeNodeCompileInfo(ctx);
    }

    public void exitAll_result(RyParser.All_resultContext ctx) {
        // logic here is to getting out the default generated expression of dynamic result
        String all_expression = RyCompilerProxy.node_expression.get(ctx.getChild(0));
        RyCompilerProxy.node_expression.put(ctx, all_expression);
    }
}
