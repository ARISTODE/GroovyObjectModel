package compiler.listeners

import compiler.RyBaseListener
import compiler.RyCompilerProxy
import compiler.RyParser

class RyValueListener extends RyBaseListener{
    public void enterRvalue(RyParser.RvalueContext ctx) {
        String cls_name = RyCompilerProxy.class_definition.get(ctx.getParent());
        RyCompilerProxy.class_definition.put(ctx, cls_name);
    }

    public void exitRvalue(RyParser.RvalueContext ctx) {
        String child_expression = RyCompilerProxy.node_expression.get(ctx.getChild(0));
        RyCompilerProxy.node_expression.put(ctx, child_expression);
    }

    public void enterLvalue(RyParser.LvalueContext ctx) {
        String cls_name = RyCompilerProxy.class_definition.get(ctx.getParent());
        RyCompilerProxy.class_definition.put(ctx, cls_name);
    }

    public void exitLvalue(RyParser.LvalueContext ctx) {
        String cls_name = RyCompilerProxy.class_definition.get(ctx.getParent());
        String child_expression = "";
        if (cls_name != "TopObject") {
            String var_text = RyCompilerProxy.node_expression.get(ctx.getChild(0));
            child_expression = "self.read_attr(\"${var_text}\");";
            RyCompilerProxy.node_expression.put(ctx, child_expression);
        } else {
            child_expression = RyCompilerProxy.node_expression.get(ctx.getChild(0));
            RyCompilerProxy.node_expression.put(ctx, child_expression);
        }
    }
}
