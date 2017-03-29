package compiler.listeners

import ObjectModel.ScopeManager
import compiler.RyBaseListener
import compiler.RyCompilerProxy
import compiler.RyParser


class RyAssignmentListener extends RyBaseListener{
    public void enterAssignment(RyParser.AssignmentContext ctx) {
        RyCompilerProxy.storeNodeCompileInfo(ctx);
    }

    public void enterDynamic_assignment(RyParser.Dynamic_assignmentContext ctx) {
        RyCompilerProxy.storeNodeCompileInfo(ctx);
    }

    public void enterFloat_assignment(RyParser.Float_assignmentContext ctx) {
        RyCompilerProxy.storeNodeCompileInfo(ctx);
    }

    public void enterInt_assignment(RyParser.Int_assignmentContext ctx) {
        RyCompilerProxy.storeNodeCompileInfo(ctx);
    }

    public void enterString_assignment(RyParser.String_assignmentContext ctx) {
        RyCompilerProxy.storeNodeCompileInfo(ctx);
    }

    // exit sections

    public void exitAssignment(RyParser.AssignmentContext ctx) {
        // retrieve node compile information
        HashMap<String,String> node_info = RyCompilerProxy.node_compile_info.get(ctx);
        String cls_name = node_info["class"];
        String scope_name = node_info["scope"];

        // left part datum (id literal)
        String var_text = RyCompilerProxy.node_expression.get(ctx.getChild(0));
        // right par datum (new Instance...)
        String right_expression = RyCompilerProxy.node_expression.get(ctx.getChild(2));
        String assignment_expression;

        switch(ctx.op.getType()) {
            case RyParser.ASSIGN:
                if (var_text.contains("self")) {
                    // instance variable
                    assignment_expression = "${var_text}, ${right_expression})";
                } else {
                    assignment_expression = "${var_text} = ${right_expression}";
                }
                break;
            default:
                String assignOprText = RyCompilerProxy.getAssignOprText(ctx.op.getText());
                assignment_expression = RyCompilerProxy.generateResultExpression(var_text, assignOprText, right_expression);
                break;
        }

        assignment_expression += ";\n";

        // store the variable to the scope
        ScopeManager.addInstanceToScope(scope_name, var_text);
        RyCompilerProxy.node_expression.put(ctx, assignment_expression);
    }

    // dynamic assignment
    public void exitDynamic_assignment(RyParser.Dynamic_assignmentContext ctx) {
        HashMap<String,String> node_info = RyCompilerProxy.node_compile_info.get(ctx);
        String cls_name = node_info["class"];
        String scope_name = node_info["scope"];

        String var_text = RyCompilerProxy.node_expression.get(ctx.getChild(0));
        String dynamic_expression = RyCompilerProxy.node_expression.get(ctx.getChild(2));
        String dynamic_assignment_expression;


        switch(ctx.op.getType()) {
            case RyParser.ASSIGN:
                if (var_text.contains("self")) {
                    // instance variable
                    dynamic_assignment_expression = "${var_text}, ${dynamic_expression})";
                } else {
                    dynamic_assignment_expression = "${var_text} = ${dynamic_expression}";
                }
                break;
            default:
                String assignOprText = RyCompilerProxy.getAssignOprText(ctx.op.getText());
                dynamic_assignment_expression = RyCompilerProxy.generateResultExpression(var_text, assignOprText, dynamic_expression);
                break;
        }

        dynamic_assignment_expression += ";\n";

        ScopeManager.addInstanceToScope(scope_name, var_text);
        RyCompilerProxy.node_expression.put(ctx, dynamic_assignment_expression);
    }

    public void exitFloat_assignment(RyParser.Float_assignmentContext ctx) {
        HashMap<String,String> node_info = RyCompilerProxy.node_compile_info.get(ctx);
        String cls_name = node_info["class"];
        String scope_name = node_info["scope"];

        String var_text = RyCompilerProxy.node_expression.get(ctx.getChild(0));
        String raw_var_text = ctx.var_id.getText();

        String float_result_expression = RyCompilerProxy.node_expression.get(ctx.getChild(2));
        String float_assignment_expression;

        switch(ctx.op.getType()) {
            case RyParser.ASSIGN:
                if (var_text.contains("self")) {
                    // instance variable datum
                    float_assignment_expression = "${var_text}, ${float_result_expression})";
                } else {
                    float_assignment_expression = "${var_text} = ${float_result_expression}";
                }
                break;
            default:
                String assignOprText = RyCompilerProxy.getAssignOprText(ctx.op.getText());
                float_assignment_expression = RyCompilerProxy.generateResultExpression(raw_var_text, assignOprText, float_result_expression);
                break;
        }

        ScopeManager.addInstanceToScope(scope_name, var_text);
        RyCompilerProxy.node_expression.put(ctx, float_assignment_expression);
    }

    public void exitInt_assignment(RyParser.Int_assignmentContext ctx) {
        HashMap<String,String> node_info = RyCompilerProxy.node_compile_info.get(ctx);
        String cls_name = node_info["class"];
        String scope_name = node_info["scope"];

        String var_text = RyCompilerProxy.node_expression.get(ctx.getChild(0));
        String int_result_expression = RyCompilerProxy.node_expression.get(ctx.getChild(2));
        String int_assignment_expression;

        String raw_var_text = ctx.var_id.getText();

        switch(ctx.op.getType()) {
            case RyParser.ASSIGN:
                if (var_text.contains("self")) {
                    // instance variable
                    int_assignment_expression = "${var_text}, ${int_result_expression})";
                } else {
                    int_assignment_expression = "${var_text} = ${int_result_expression}";
                }
                break;
            default:
                String assignOprText = RyCompilerProxy.getAssignOprText(ctx.op.getText());
                int_assignment_expression = RyCompilerProxy.generateResultExpression(raw_var_text, assignOprText, int_result_expression);
                break;
        }

        ScopeManager.addInstanceToScope(scope_name, var_text);
        RyCompilerProxy.node_expression.put(ctx, int_assignment_expression);
    }

    public void exitString_assignment(RyParser.String_assignmentContext ctx) {
        HashMap<String,String> node_info = RyCompilerProxy.node_compile_info.get(ctx);
        String cls_name = node_info["class"];
        String scope_name = node_info["scope"];

        // not getting expression from the node_expression but from the var_id renamed token
        String var_text = RyCompilerProxy.node_expression.get(ctx.getChild(0));
        String raw_var_text = ctx.var_id.getText();
        String string_result_expression = RyCompilerProxy.node_expression.get(ctx.getChild(2));
        String string_assignment_expression;

        switch(ctx.op.getType()) {
            case RyParser.ASSIGN:
                if (var_text.contains("self")) {
                    // instance variable
                    string_assignment_expression = "${var_text}, ${string_result_expression})";
                } else {
                    string_assignment_expression = "${var_text} = ${string_result_expression}";
                }
                break;
            default:
                String assignOprText = RyCompilerProxy.getAssignOprText(ctx.op.getText());
                string_assignment_expression = RyCompilerProxy.generateResultExpression(raw_var_text, assignOprText, string_result_expression);
                break;
        }

        ScopeManager.addInstanceToScope(scope_name, var_text);
        RyCompilerProxy.node_expression.put(ctx, string_assignment_expression);
    }
}

