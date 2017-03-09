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
        String class_definition_expression = "public static RyBaseClass ${class_name} = new RyBaseClass(\"${class_name}\", [:], Global._RyClass, Global._TopObject);\n";
        RyCompilerProxy.printToClassStream(class_definition_expression);
        // make user create class globally access
        RyCompilerProxy.node_expression.put(ctx, class_definition_expression);
    }

    public void exitClass_name(RyParser.Class_nameContext ctx) {
        String constant_expression = ctx.constant().getText();
        RyCompilerProxy.node_expression.put(ctx, constant_expression);
        // get the context node of parent. store the name of class with the parent class definition node
        ParserRuleContext parent_ctx = ctx.getParent();
        RyCompilerProxy.class_definition.put(parent_ctx, constant_expression);
    }

    public void enterClass_body(RyParser.Class_bodyContext ctx) {
        ParseTree parent_ctx = ctx.getParent();
        String cls_name = RyCompilerProxy.class_definition.get(parent_ctx); // name is already stored after the walker walk out of the node
        RyCompilerProxy.class_definition.put(ctx, cls_name);
    }

    public void exitClass_body(RyParser.Class_bodyContext ctx) {
        String class_body_expression_list = RyCompilerProxy.node_expression.get(ctx.getChild(0));
        RyCompilerProxy.node_expression.put(ctx, class_body_expression_list);
    }
}
