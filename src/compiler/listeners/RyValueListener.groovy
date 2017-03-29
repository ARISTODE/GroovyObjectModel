package compiler.listeners

import ObjectModel.ScopeManager
import compiler.RyBaseListener
import compiler.RyCompilerProxy
import compiler.RyParser

class RyValueListener extends RyBaseListener{
    public void enterRvalue(RyParser.RvalueContext ctx) {
        RyCompilerProxy.storeNodeCompileInfo(ctx);
    }

    public void exitRvalue(RyParser.RvalueContext ctx) {
        RyCompilerProxy.node_expression.put(ctx, RyCompilerProxy.node_expression.get(ctx.getChild(0)));
    }

    public void enterLvalue(RyParser.LvalueContext ctx) {
       RyCompilerProxy.storeNodeCompileInfo(ctx);
    }

    public void exitLvalue(RyParser.LvalueContext ctx) {
        String var_text = RyCompilerProxy.node_expression.get(ctx.getChild(0));
        RyCompilerProxy.node_expression.put(ctx, var_text);
    }

    public void enterVar(RyParser.VarContext ctx) {
        RyCompilerProxy.storeNodeCompileInfo(ctx);
    }

    public void exitVar(RyParser.VarContext ctx) {
        HashMap<String,String> node_info = RyCompilerProxy.node_compile_info.get(ctx);
        String cls_name = node_info["class"];
        String scope_name = node_info["scope"];

        String var_expr = "";
        String var_text = ctx.ID().getText();

        if (ScopeManager.checkInstanceExistence(scope_name, var_text)){
            var_expr = "${var_text}";
        } else {
            var_expr = "Instance ${var_text}";
        }
        RyCompilerProxy.node_expression.put(ctx, var_expr);
    }

    public void enterVar_instance(RyParser.Var_instanceContext ctx) {
        RyCompilerProxy.storeNodeCompileInfo(ctx);
    }

    public void exitVar_instance(RyParser.Var_instanceContext ctx) {
        String var_text = "${ctx.getChild(0)}${ctx.getChild(1)}";
        String var_instance = "";

        String operation = RyCompilerProxy.node_compile_info.get(ctx.getParent())["operation"];
        if (operation != null) {
            if (operation == "read") {
                var_instance = "self.read_attr(\"${var_text}\")";
            } else {
                var_instance = "self.write_attr(\"${var_text}\"";
            }
        } else {
            // default behavior
            var_instance = "self.write_attr(\"${var_text}\"";
        }

        RyCompilerProxy.node_expression.put(ctx,var_instance);
    }
}
