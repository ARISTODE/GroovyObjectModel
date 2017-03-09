package compiler.listeners

import compiler.RyBaseListener
import compiler.RyCompilerProxy
import compiler.RyParser

class RyProgListener extends RyBaseListener {
    public void enterProg(RyParser.ProgContext ctx) {

    }

    public void exitProg(RyParser.ProgContext ctx) {
        String all_expressions = RyCompilerProxy.node_expression.get(ctx.getChild(0));
        RyCompilerProxy.printToOutStream(all_expressions);
        RyCompilerProxy.stack_out_stream.push(RyCompilerProxy.class_stream);
        RyCompilerProxy.stack_out_stream.push(RyCompilerProxy.expression_stream);
        RyCompilerProxy.stack_out_stream.push(RyCompilerProxy.function_stream);
        RyCompilerProxy.stack_out_stream.push(RyCompilerProxy.error_stream);
    }
}