package compiler.listeners

import compiler.RyBaseListener
import compiler.RyCompilerProxy
import compiler.RyParser


class RyAssignmentListener extends RyBaseListener{
    public void exitAssignment(RyParser.AssignmentContext ctx) {
        String var = ctx.var_id.getText(); // left expression
        String right_expression = RyCompilerProxy.node_expression.get(ctx.getChild(2));
        String assignment_expression = "";

        if (ctx.op.getText() == "=") {
            assignment_expression = "instance_manager.addInstance(\"${var }\", ${right_expression})";
        } else {
            String opr_text = RyCompilerProxy.getOprText(ctx.op.getText());
            // for + - * /
            assignment_expression = RyCompilerProxy.generateResultExpression(var, opr_text,right_expression);
        }

        assignment_expression += ";\n";
        RyCompilerProxy.node_expression.put(ctx, assignment_expression);
    }

    public void enterDynamic_assignment(RyParser.Dynamic_assignmentContext ctx) {
        String cls_name = RyCompilerProxy.class_definition.get(ctx.getParent());
        RyCompilerProxy.class_definition.put(ctx, cls_name);
    }

    // dynamic assignment
    public void exitDynamic_assignment(RyParser.Dynamic_assignmentContext ctx) {
        String cls_name = RyCompilerProxy.class_definition.get(ctx);
        String var = ctx.var_id.getText();
        String right_expression = RyCompilerProxy.node_expression.get(ctx.getChild(2));
        String dynamic_assignment_expression = "";

        if (cls_name != "TopObject") {
            // inside a class definition
            if (ctx.op.getText() == "=") {
                dynamic_assignment_expression = "self.write_attr(\"${var}\", ${right_expression})";
            dynamic_assignment_expression += ";\n";
            RyCompilerProxy.node_expression.put(ctx, dynamic_assignment_expression);
            }
        } else {
            // just in the TopObject context
            if (ctx.op.getText() == "=") {
                dynamic_assignment_expression = "instance_manager.addInstance(\"${var }\", ${right_expression})";
                // TODO: add + - * /
            } else {
                String opr_text = RyCompilerProxy.getOprText(ctx.op.getText());
                // for + - * /
                dynamic_assignment_expression = RyCompilerProxy.generateResultExpression(var, opr_text,right_expression);
            }

            dynamic_assignment_expression += ";\n";
            RyCompilerProxy.node_expression.put(ctx, dynamic_assignment_expression);
        }
    }

    public void exitFloat_assignment(RyParser.Float_assignmentContext ctx) {
        String var = ctx.var_id.getText();
        String float_result_expression = RyCompilerProxy.node_expression.get(ctx.getChild(2));
        String float_assignment_expression;

        switch(ctx.op.getType()) {
            case RyParser.ASSIGN:
                float_assignment_expression = "instance_manager.addInstance(\"${var}\", ${float_result_expression})";
                break;
            default:
                String assignOprText = RyCompilerProxy.getAssignOprText(ctx.op.getText());
                float_assignment_expression = "instance_manager.getInstance(\"${var}\").callmethod(\"${assignOprText}\",${float_result_expression})";
                break;
        }

        RyCompilerProxy.node_expression.put(ctx, float_assignment_expression);
    }

    public void exitInt_assignment(RyParser.Int_assignmentContext ctx) {
        String var = ctx.var_id.getText();
        String int_result_expression = RyCompilerProxy.node_expression.get(ctx.getChild(2));
        String int_assignment_expression;

        switch(ctx.op.getType()) {
            case RyParser.ASSIGN:
                int_assignment_expression = "instance_manager.addInstance(\"${var}\", ${int_result_expression})";
                break;
            default:
                String assignOprText = RyCompilerProxy.getAssignOprText(ctx.op.getText());
                int_assignment_expression = "instance_manager.getInstance(\"${var}\").callmethod(\"${assignOprText}\",${int_result_expression})";
                break;
        }

        RyCompilerProxy.node_expression.put(ctx, int_assignment_expression);
    }

    public void exitString_assignment(RyParser.String_assignmentContext ctx) {
        // not getting expression from the node_expression but from the var_id renamed token
        String var = ctx.var_id.getText();
        String string_result_expression = RyCompilerProxy.node_expression.get(ctx.getChild(2));
        String string_assignment_expression;

        switch(ctx.op.getType()) {
            case RyParser.ASSIGN:
                string_assignment_expression = "instance_manager.addInstance(\"${var}\", ${string_result_expression})";
                break;
            default:
                String assignOprText = RyCompilerProxy.getAssignOprText(ctx.op.getText());
                string_assignment_expression = "instance_manager.getInstance(\"${var}\").callmethod(\"${assignOprText}\", ${string_result_expression})";
                break;
        }

        RyCompilerProxy.node_expression.put(ctx, string_assignment_expression);
    }
}

