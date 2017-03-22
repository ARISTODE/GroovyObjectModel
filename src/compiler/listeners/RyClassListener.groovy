package compiler.listeners

import compiler.RyBaseListener
import compiler.RyCompilerProxy
import compiler.RyParser
import org.antlr.v4.runtime.ParserRuleContext
import org.antlr.v4.runtime.tree.ParseTree

class RyClassListener extends RyBaseListener {

    public void exitClass_definition(RyParser.Class_definitionContext ctx) {
        String class_name = RyCompilerProxy.node_expression.get(ctx.getChild(1));
        String class_body_expressions = RyCompilerProxy.node_expression.get(ctx.getChild(3));
//        String class_definition_expression = "class ${class_name} {\n ${class_body_expressions} \n}";
        // TODO: switch TopObject to the extend class
        String class_definition_expression = "class_manager.addCls(\"${class_name}\", [\"initialize\": {self, Object... args -> }], class_manager.getCls(\"RyClass\"), class_manager.getCls(\"TopObject\"))"

        class_definition_expression += "\n${class_body_expressions}";

//        RyCompilerProxy.printToClassStream(class_definition_expression);
        // make user create class globally access
        RyCompilerProxy.node_expression.put(ctx, class_definition_expression);
    }

    public void exitClass_name(RyParser.Class_nameContext ctx) {
        String constant_expression = ctx.constant().getText();
        RyCompilerProxy.node_expression.put(ctx, constant_expression);
        // get the context node of parent. store the name of class with the parent class definition node
        ParserRuleContext parent_ctx = ctx.getParent();
        RyCompilerProxy.class_definition.put(parent_ctx, constant_expression);
        RyCompilerProxy.node_expression.put(ctx, constant_expression);
    }

    public void enterClass_body(RyParser.Class_bodyContext ctx) {
        String cls_name = RyCompilerProxy.class_definition.get(ctx.getParent()); // name is already stored after the walker walk out of the node
        RyCompilerProxy.class_definition.put(ctx, cls_name);
    }

    public void exitClass_body(RyParser.Class_bodyContext ctx) {
        String class_body_expression_list = RyCompilerProxy.node_expression.get(ctx.getChild(0));
        RyCompilerProxy.node_expression.put(ctx, class_body_expression_list);
    }

    public void exitObject_definition(RyParser.Object_definitionContext ctx) {
        String class_name = RyCompilerProxy.node_expression.get(ctx.getChild(0));
        String function_params_expression = RyCompilerProxy.node_expression.get(ctx.getChild(4));
        String object_definition_expression = "new Instance(class_manager.getCls(\"${class_name}\"))";
        RyCompilerProxy.node_expression.put(ctx, object_definition_expression);
    }
}
