package compiler.listeners

import compiler.RyBaseListener
import compiler.RyCompilerProxy
import compiler.RyParser
import org.antlr.v4.runtime.ParserRuleContext
import org.antlr.v4.runtime.tree.ParseTree

class RyClassListener extends RyBaseListener {

    /*  ======================  class definition  ======================  */

    public void enterClass_definition(RyParser.Class_definitionContext ctx) {
        RyCompilerProxy.storeNodeCompileInfo(ctx);
    }

    public void exitClass_definition(RyParser.Class_definitionContext ctx) {
        String class_name = RyCompilerProxy.node_expression.get(ctx.getChild(1));
        int child_len = ctx.getChildCount();
        String class_body_expressions;

        // switch body expression according the superclass exist
        class_body_expressions = child_len <= 5 ? RyCompilerProxy.node_expression.get(ctx.getChild(3)) : RyCompilerProxy.node_expression.get(ctx.getChild(5));

        // get super class
        String super_cls_name = RyCompilerProxy.node_compile_info.get(ctx)["superclass"];

        if (super_cls_name == null) {
            super_cls_name = "TopObject";
        }

        String class_definition_expression = "class_manager.addCls(\"${class_name}\", [\"initialize\": {self, Object... args -> }], class_manager.getCls(\"RyClass\"), class_manager.getCls(\"${super_cls_name}\"))"
        class_definition_expression += "\n${class_body_expressions}";

        RyCompilerProxy.node_expression.put(ctx, class_definition_expression);
    }

    /*  ======================  class name  ======================  */

    public void enterClass_name(RyParser.Class_nameContext ctx) {
        String class_name = ctx.constant().getText();
        // find parent class definition and store it with new class and scope map
        RyCompilerProxy.updateNodeCompileInfo(ctx.getParent(), ["class":class_name, "scope": class_name]);
    }

    public void exitClass_name(RyParser.Class_nameContext ctx) {
        String constant_expression = ctx.constant().getText();
        // get the context node of parent. store the name of class with the parent class definition node
        RyCompilerProxy.node_expression.put(ctx, constant_expression);
    }

    /*  ======================  super class name  ======================  */
    public void enterSuper_class_name(RyParser.Super_class_nameContext ctx) {
        RyCompilerProxy.storeNodeCompileInfo(ctx);
    }

    public void exitSuper_class_name(RyParser.Super_class_nameContext ctx) {
        String super_cls_name = ctx.constant().getText();
        HashMap<String, String> old_node_info = RyCompilerProxy.node_compile_info.get(ctx);

        // add super class info the node information map
        RyCompilerProxy.updateNodeCompileInfo(ctx.getParent(), (HashMap)(old_node_info << ["superclass": super_cls_name]));
        RyCompilerProxy.node_expression.put(ctx, super_cls_name);
    }

    public void enterClass_body(RyParser.Class_bodyContext ctx) {
        RyCompilerProxy.storeNodeCompileInfo(ctx);
    }

    public void exitClass_body(RyParser.Class_bodyContext ctx) {
        String class_body_expression_list = RyCompilerProxy.node_expression.get(ctx.getChild(0));
        RyCompilerProxy.node_expression.put(ctx, class_body_expression_list);
    }

    public void enterObject_definition(RyParser.Object_definitionContext ctx) {
        RyCompilerProxy.storeNodeCompileInfo(ctx);
    }

    public void exitObject_definition(RyParser.Object_definitionContext ctx) {
        String class_name = RyCompilerProxy.node_expression.get(ctx.getChild(0));
        String function_params_expression = RyCompilerProxy.node_expression.get(ctx.getChild(4));
        String object_definition_expression = "new Instance(class_manager.getCls(\"${class_name}\"))";
        RyCompilerProxy.node_expression.put(ctx, object_definition_expression);
    }
}
