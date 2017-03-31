package compiler;// Generated from Ry.g4 by ANTLR 4.5
import org.antlr.v4.runtime.misc.NotNull;
import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link RyParser}.
 */
public interface RyListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link RyParser#prog}.
	 * @param ctx the parse tree
	 */
	void enterProg(RyParser.ProgContext ctx);
	/**
	 * Exit a parse tree produced by {@link RyParser#prog}.
	 * @param ctx the parse tree
	 */
	void exitProg(RyParser.ProgContext ctx);
	/**
	 * Enter a parse tree produced by {@link RyParser#expression_list}.
	 * @param ctx the parse tree
	 */
	void enterExpression_list(RyParser.Expression_listContext ctx);
	/**
	 * Exit a parse tree produced by {@link RyParser#expression_list}.
	 * @param ctx the parse tree
	 */
	void exitExpression_list(RyParser.Expression_listContext ctx);
	/**
	 * Enter a parse tree produced by {@link RyParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterExpression(RyParser.ExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link RyParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitExpression(RyParser.ExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link RyParser#class_definition}.
	 * @param ctx the parse tree
	 */
	void enterClass_definition(RyParser.Class_definitionContext ctx);
	/**
	 * Exit a parse tree produced by {@link RyParser#class_definition}.
	 * @param ctx the parse tree
	 */
	void exitClass_definition(RyParser.Class_definitionContext ctx);
	/**
	 * Enter a parse tree produced by {@link RyParser#super_class_name}.
	 * @param ctx the parse tree
	 */
	void enterSuper_class_name(RyParser.Super_class_nameContext ctx);
	/**
	 * Exit a parse tree produced by {@link RyParser#super_class_name}.
	 * @param ctx the parse tree
	 */
	void exitSuper_class_name(RyParser.Super_class_nameContext ctx);
	/**
	 * Enter a parse tree produced by {@link RyParser#class_name}.
	 * @param ctx the parse tree
	 */
	void enterClass_name(RyParser.Class_nameContext ctx);
	/**
	 * Exit a parse tree produced by {@link RyParser#class_name}.
	 * @param ctx the parse tree
	 */
	void exitClass_name(RyParser.Class_nameContext ctx);
	/**
	 * Enter a parse tree produced by {@link RyParser#class_body}.
	 * @param ctx the parse tree
	 */
	void enterClass_body(RyParser.Class_bodyContext ctx);
	/**
	 * Exit a parse tree produced by {@link RyParser#class_body}.
	 * @param ctx the parse tree
	 */
	void exitClass_body(RyParser.Class_bodyContext ctx);
	/**
	 * Enter a parse tree produced by {@link RyParser#object_definition}.
	 * @param ctx the parse tree
	 */
	void enterObject_definition(RyParser.Object_definitionContext ctx);
	/**
	 * Exit a parse tree produced by {@link RyParser#object_definition}.
	 * @param ctx the parse tree
	 */
	void exitObject_definition(RyParser.Object_definitionContext ctx);
	/**
	 * Enter a parse tree produced by {@link RyParser#function_definition}.
	 * @param ctx the parse tree
	 */
	void enterFunction_definition(RyParser.Function_definitionContext ctx);
	/**
	 * Exit a parse tree produced by {@link RyParser#function_definition}.
	 * @param ctx the parse tree
	 */
	void exitFunction_definition(RyParser.Function_definitionContext ctx);
	/**
	 * Enter a parse tree produced by {@link RyParser#function_header}.
	 * @param ctx the parse tree
	 */
	void enterFunction_header(RyParser.Function_headerContext ctx);
	/**
	 * Exit a parse tree produced by {@link RyParser#function_header}.
	 * @param ctx the parse tree
	 */
	void exitFunction_header(RyParser.Function_headerContext ctx);
	/**
	 * Enter a parse tree produced by {@link RyParser#return_statement}.
	 * @param ctx the parse tree
	 */
	void enterReturn_statement(RyParser.Return_statementContext ctx);
	/**
	 * Exit a parse tree produced by {@link RyParser#return_statement}.
	 * @param ctx the parse tree
	 */
	void exitReturn_statement(RyParser.Return_statementContext ctx);
	/**
	 * Enter a parse tree produced by {@link RyParser#function_body}.
	 * @param ctx the parse tree
	 */
	void enterFunction_body(RyParser.Function_bodyContext ctx);
	/**
	 * Exit a parse tree produced by {@link RyParser#function_body}.
	 * @param ctx the parse tree
	 */
	void exitFunction_body(RyParser.Function_bodyContext ctx);
	/**
	 * Enter a parse tree produced by {@link RyParser#function_name}.
	 * @param ctx the parse tree
	 */
	void enterFunction_name(RyParser.Function_nameContext ctx);
	/**
	 * Exit a parse tree produced by {@link RyParser#function_name}.
	 * @param ctx the parse tree
	 */
	void exitFunction_name(RyParser.Function_nameContext ctx);
	/**
	 * Enter a parse tree produced by {@link RyParser#function_params}.
	 * @param ctx the parse tree
	 */
	void enterFunction_params(RyParser.Function_paramsContext ctx);
	/**
	 * Exit a parse tree produced by {@link RyParser#function_params}.
	 * @param ctx the parse tree
	 */
	void exitFunction_params(RyParser.Function_paramsContext ctx);
	/**
	 * Enter a parse tree produced by {@link RyParser#function_definition_param_list}.
	 * @param ctx the parse tree
	 */
	void enterFunction_definition_param_list(RyParser.Function_definition_param_listContext ctx);
	/**
	 * Exit a parse tree produced by {@link RyParser#function_definition_param_list}.
	 * @param ctx the parse tree
	 */
	void exitFunction_definition_param_list(RyParser.Function_definition_param_listContext ctx);
	/**
	 * Enter a parse tree produced by {@link RyParser#function_definition_param_id}.
	 * @param ctx the parse tree
	 */
	void enterFunction_definition_param_id(RyParser.Function_definition_param_idContext ctx);
	/**
	 * Exit a parse tree produced by {@link RyParser#function_definition_param_id}.
	 * @param ctx the parse tree
	 */
	void exitFunction_definition_param_id(RyParser.Function_definition_param_idContext ctx);
	/**
	 * Enter a parse tree produced by {@link RyParser#function_call}.
	 * @param ctx the parse tree
	 */
	void enterFunction_call(RyParser.Function_callContext ctx);
	/**
	 * Exit a parse tree produced by {@link RyParser#function_call}.
	 * @param ctx the parse tree
	 */
	void exitFunction_call(RyParser.Function_callContext ctx);
	/**
	 * Enter a parse tree produced by {@link RyParser#class_function_call}.
	 * @param ctx the parse tree
	 */
	void enterClass_function_call(RyParser.Class_function_callContext ctx);
	/**
	 * Exit a parse tree produced by {@link RyParser#class_function_call}.
	 * @param ctx the parse tree
	 */
	void exitClass_function_call(RyParser.Class_function_callContext ctx);
	/**
	 * Enter a parse tree produced by {@link RyParser#function_call_param_list}.
	 * @param ctx the parse tree
	 */
	void enterFunction_call_param_list(RyParser.Function_call_param_listContext ctx);
	/**
	 * Exit a parse tree produced by {@link RyParser#function_call_param_list}.
	 * @param ctx the parse tree
	 */
	void exitFunction_call_param_list(RyParser.Function_call_param_listContext ctx);
	/**
	 * Enter a parse tree produced by {@link RyParser#function_call_params}.
	 * @param ctx the parse tree
	 */
	void enterFunction_call_params(RyParser.Function_call_paramsContext ctx);
	/**
	 * Exit a parse tree produced by {@link RyParser#function_call_params}.
	 * @param ctx the parse tree
	 */
	void exitFunction_call_params(RyParser.Function_call_paramsContext ctx);
	/**
	 * Enter a parse tree produced by {@link RyParser#function_param}.
	 * @param ctx the parse tree
	 */
	void enterFunction_param(RyParser.Function_paramContext ctx);
	/**
	 * Exit a parse tree produced by {@link RyParser#function_param}.
	 * @param ctx the parse tree
	 */
	void exitFunction_param(RyParser.Function_paramContext ctx);
	/**
	 * Enter a parse tree produced by {@link RyParser#function_call_unnamed_param}.
	 * @param ctx the parse tree
	 */
	void enterFunction_call_unnamed_param(RyParser.Function_call_unnamed_paramContext ctx);
	/**
	 * Exit a parse tree produced by {@link RyParser#function_call_unnamed_param}.
	 * @param ctx the parse tree
	 */
	void exitFunction_call_unnamed_param(RyParser.Function_call_unnamed_paramContext ctx);
	/**
	 * Enter a parse tree produced by {@link RyParser#function_call_named_param}.
	 * @param ctx the parse tree
	 */
	void enterFunction_call_named_param(RyParser.Function_call_named_paramContext ctx);
	/**
	 * Exit a parse tree produced by {@link RyParser#function_call_named_param}.
	 * @param ctx the parse tree
	 */
	void exitFunction_call_named_param(RyParser.Function_call_named_paramContext ctx);
	/**
	 * Enter a parse tree produced by {@link RyParser#all_result}.
	 * @param ctx the parse tree
	 */
	void enterAll_result(RyParser.All_resultContext ctx);
	/**
	 * Exit a parse tree produced by {@link RyParser#all_result}.
	 * @param ctx the parse tree
	 */
	void exitAll_result(RyParser.All_resultContext ctx);
	/**
	 * Enter a parse tree produced by {@link RyParser#while_statement}.
	 * @param ctx the parse tree
	 */
	void enterWhile_statement(RyParser.While_statementContext ctx);
	/**
	 * Exit a parse tree produced by {@link RyParser#while_statement}.
	 * @param ctx the parse tree
	 */
	void exitWhile_statement(RyParser.While_statementContext ctx);
	/**
	 * Enter a parse tree produced by {@link RyParser#for_statement}.
	 * @param ctx the parse tree
	 */
	void enterFor_statement(RyParser.For_statementContext ctx);
	/**
	 * Exit a parse tree produced by {@link RyParser#for_statement}.
	 * @param ctx the parse tree
	 */
	void exitFor_statement(RyParser.For_statementContext ctx);
	/**
	 * Enter a parse tree produced by {@link RyParser#elsif_statement}.
	 * @param ctx the parse tree
	 */
	void enterElsif_statement(RyParser.Elsif_statementContext ctx);
	/**
	 * Exit a parse tree produced by {@link RyParser#elsif_statement}.
	 * @param ctx the parse tree
	 */
	void exitElsif_statement(RyParser.Elsif_statementContext ctx);
	/**
	 * Enter a parse tree produced by {@link RyParser#if_elsif_statement}.
	 * @param ctx the parse tree
	 */
	void enterIf_elsif_statement(RyParser.If_elsif_statementContext ctx);
	/**
	 * Exit a parse tree produced by {@link RyParser#if_elsif_statement}.
	 * @param ctx the parse tree
	 */
	void exitIf_elsif_statement(RyParser.If_elsif_statementContext ctx);
	/**
	 * Enter a parse tree produced by {@link RyParser#if_statement}.
	 * @param ctx the parse tree
	 */
	void enterIf_statement(RyParser.If_statementContext ctx);
	/**
	 * Exit a parse tree produced by {@link RyParser#if_statement}.
	 * @param ctx the parse tree
	 */
	void exitIf_statement(RyParser.If_statementContext ctx);
	/**
	 * Enter a parse tree produced by {@link RyParser#unless_statement}.
	 * @param ctx the parse tree
	 */
	void enterUnless_statement(RyParser.Unless_statementContext ctx);
	/**
	 * Exit a parse tree produced by {@link RyParser#unless_statement}.
	 * @param ctx the parse tree
	 */
	void exitUnless_statement(RyParser.Unless_statementContext ctx);
	/**
	 * Enter a parse tree produced by {@link RyParser#all_assignment}.
	 * @param ctx the parse tree
	 */
	void enterAll_assignment(RyParser.All_assignmentContext ctx);
	/**
	 * Exit a parse tree produced by {@link RyParser#all_assignment}.
	 * @param ctx the parse tree
	 */
	void exitAll_assignment(RyParser.All_assignmentContext ctx);
	/**
	 * Enter a parse tree produced by {@link RyParser#cond_expression}.
	 * @param ctx the parse tree
	 */
	void enterCond_expression(RyParser.Cond_expressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link RyParser#cond_expression}.
	 * @param ctx the parse tree
	 */
	void exitCond_expression(RyParser.Cond_expressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link RyParser#statement_body}.
	 * @param ctx the parse tree
	 */
	void enterStatement_body(RyParser.Statement_bodyContext ctx);
	/**
	 * Exit a parse tree produced by {@link RyParser#statement_body}.
	 * @param ctx the parse tree
	 */
	void exitStatement_body(RyParser.Statement_bodyContext ctx);
	/**
	 * Enter a parse tree produced by {@link RyParser#statement_expression_list}.
	 * @param ctx the parse tree
	 */
	void enterStatement_expression_list(RyParser.Statement_expression_listContext ctx);
	/**
	 * Exit a parse tree produced by {@link RyParser#statement_expression_list}.
	 * @param ctx the parse tree
	 */
	void exitStatement_expression_list(RyParser.Statement_expression_listContext ctx);
	/**
	 * Enter a parse tree produced by {@link RyParser#assignment}.
	 * @param ctx the parse tree
	 */
	void enterAssignment(RyParser.AssignmentContext ctx);
	/**
	 * Exit a parse tree produced by {@link RyParser#assignment}.
	 * @param ctx the parse tree
	 */
	void exitAssignment(RyParser.AssignmentContext ctx);
	/**
	 * Enter a parse tree produced by {@link RyParser#dynamic_assignment}.
	 * @param ctx the parse tree
	 */
	void enterDynamic_assignment(RyParser.Dynamic_assignmentContext ctx);
	/**
	 * Exit a parse tree produced by {@link RyParser#dynamic_assignment}.
	 * @param ctx the parse tree
	 */
	void exitDynamic_assignment(RyParser.Dynamic_assignmentContext ctx);
	/**
	 * Enter a parse tree produced by {@link RyParser#int_assignment}.
	 * @param ctx the parse tree
	 */
	void enterInt_assignment(RyParser.Int_assignmentContext ctx);
	/**
	 * Exit a parse tree produced by {@link RyParser#int_assignment}.
	 * @param ctx the parse tree
	 */
	void exitInt_assignment(RyParser.Int_assignmentContext ctx);
	/**
	 * Enter a parse tree produced by {@link RyParser#float_assignment}.
	 * @param ctx the parse tree
	 */
	void enterFloat_assignment(RyParser.Float_assignmentContext ctx);
	/**
	 * Exit a parse tree produced by {@link RyParser#float_assignment}.
	 * @param ctx the parse tree
	 */
	void exitFloat_assignment(RyParser.Float_assignmentContext ctx);
	/**
	 * Enter a parse tree produced by {@link RyParser#string_assignment}.
	 * @param ctx the parse tree
	 */
	void enterString_assignment(RyParser.String_assignmentContext ctx);
	/**
	 * Exit a parse tree produced by {@link RyParser#string_assignment}.
	 * @param ctx the parse tree
	 */
	void exitString_assignment(RyParser.String_assignmentContext ctx);
	/**
	 * Enter a parse tree produced by {@link RyParser#dynamic_result}.
	 * @param ctx the parse tree
	 */
	void enterDynamic_result(RyParser.Dynamic_resultContext ctx);
	/**
	 * Exit a parse tree produced by {@link RyParser#dynamic_result}.
	 * @param ctx the parse tree
	 */
	void exitDynamic_result(RyParser.Dynamic_resultContext ctx);
	/**
	 * Enter a parse tree produced by {@link RyParser#dynamic}.
	 * @param ctx the parse tree
	 */
	void enterDynamic(RyParser.DynamicContext ctx);
	/**
	 * Exit a parse tree produced by {@link RyParser#dynamic}.
	 * @param ctx the parse tree
	 */
	void exitDynamic(RyParser.DynamicContext ctx);
	/**
	 * Enter a parse tree produced by {@link RyParser#function_call_assignment}.
	 * @param ctx the parse tree
	 */
	void enterFunction_call_assignment(RyParser.Function_call_assignmentContext ctx);
	/**
	 * Exit a parse tree produced by {@link RyParser#function_call_assignment}.
	 * @param ctx the parse tree
	 */
	void exitFunction_call_assignment(RyParser.Function_call_assignmentContext ctx);
	/**
	 * Enter a parse tree produced by {@link RyParser#int_result}.
	 * @param ctx the parse tree
	 */
	void enterInt_result(RyParser.Int_resultContext ctx);
	/**
	 * Exit a parse tree produced by {@link RyParser#int_result}.
	 * @param ctx the parse tree
	 */
	void exitInt_result(RyParser.Int_resultContext ctx);
	/**
	 * Enter a parse tree produced by {@link RyParser#float_result}.
	 * @param ctx the parse tree
	 */
	void enterFloat_result(RyParser.Float_resultContext ctx);
	/**
	 * Exit a parse tree produced by {@link RyParser#float_result}.
	 * @param ctx the parse tree
	 */
	void exitFloat_result(RyParser.Float_resultContext ctx);
	/**
	 * Enter a parse tree produced by {@link RyParser#string_result}.
	 * @param ctx the parse tree
	 */
	void enterString_result(RyParser.String_resultContext ctx);
	/**
	 * Exit a parse tree produced by {@link RyParser#string_result}.
	 * @param ctx the parse tree
	 */
	void exitString_result(RyParser.String_resultContext ctx);
	/**
	 * Enter a parse tree produced by {@link RyParser#comparison_list}.
	 * @param ctx the parse tree
	 */
	void enterComparison_list(RyParser.Comparison_listContext ctx);
	/**
	 * Exit a parse tree produced by {@link RyParser#comparison_list}.
	 * @param ctx the parse tree
	 */
	void exitComparison_list(RyParser.Comparison_listContext ctx);
	/**
	 * Enter a parse tree produced by {@link RyParser#comparison}.
	 * @param ctx the parse tree
	 */
	void enterComparison(RyParser.ComparisonContext ctx);
	/**
	 * Exit a parse tree produced by {@link RyParser#comparison}.
	 * @param ctx the parse tree
	 */
	void exitComparison(RyParser.ComparisonContext ctx);
	/**
	 * Enter a parse tree produced by {@link RyParser#comp_var}.
	 * @param ctx the parse tree
	 */
	void enterComp_var(RyParser.Comp_varContext ctx);
	/**
	 * Exit a parse tree produced by {@link RyParser#comp_var}.
	 * @param ctx the parse tree
	 */
	void exitComp_var(RyParser.Comp_varContext ctx);
	/**
	 * Enter a parse tree produced by {@link RyParser#lvalue}.
	 * @param ctx the parse tree
	 */
	void enterLvalue(RyParser.LvalueContext ctx);
	/**
	 * Exit a parse tree produced by {@link RyParser#lvalue}.
	 * @param ctx the parse tree
	 */
	void exitLvalue(RyParser.LvalueContext ctx);
	/**
	 * Enter a parse tree produced by {@link RyParser#rvalue}.
	 * @param ctx the parse tree
	 */
	void enterRvalue(RyParser.RvalueContext ctx);
	/**
	 * Exit a parse tree produced by {@link RyParser#rvalue}.
	 * @param ctx the parse tree
	 */
	void exitRvalue(RyParser.RvalueContext ctx);
	/**
	 * Enter a parse tree produced by {@link RyParser#literal_t}.
	 * @param ctx the parse tree
	 */
	void enterLiteral_t(RyParser.Literal_tContext ctx);
	/**
	 * Exit a parse tree produced by {@link RyParser#literal_t}.
	 * @param ctx the parse tree
	 */
	void exitLiteral_t(RyParser.Literal_tContext ctx);
	/**
	 * Enter a parse tree produced by {@link RyParser#float_t}.
	 * @param ctx the parse tree
	 */
	void enterFloat_t(RyParser.Float_tContext ctx);
	/**
	 * Exit a parse tree produced by {@link RyParser#float_t}.
	 * @param ctx the parse tree
	 */
	void exitFloat_t(RyParser.Float_tContext ctx);
	/**
	 * Enter a parse tree produced by {@link RyParser#int_t}.
	 * @param ctx the parse tree
	 */
	void enterInt_t(RyParser.Int_tContext ctx);
	/**
	 * Exit a parse tree produced by {@link RyParser#int_t}.
	 * @param ctx the parse tree
	 */
	void exitInt_t(RyParser.Int_tContext ctx);
	/**
	 * Enter a parse tree produced by {@link RyParser#bool_t}.
	 * @param ctx the parse tree
	 */
	void enterBool_t(RyParser.Bool_tContext ctx);
	/**
	 * Exit a parse tree produced by {@link RyParser#bool_t}.
	 * @param ctx the parse tree
	 */
	void exitBool_t(RyParser.Bool_tContext ctx);
	/**
	 * Enter a parse tree produced by {@link RyParser#nil_t}.
	 * @param ctx the parse tree
	 */
	void enterNil_t(RyParser.Nil_tContext ctx);
	/**
	 * Exit a parse tree produced by {@link RyParser#nil_t}.
	 * @param ctx the parse tree
	 */
	void exitNil_t(RyParser.Nil_tContext ctx);
	/**
	 * Enter a parse tree produced by {@link RyParser#terminator}.
	 * @param ctx the parse tree
	 */
	void enterTerminator(RyParser.TerminatorContext ctx);
	/**
	 * Exit a parse tree produced by {@link RyParser#terminator}.
	 * @param ctx the parse tree
	 */
	void exitTerminator(RyParser.TerminatorContext ctx);
	/**
	 * Enter a parse tree produced by {@link RyParser#else_token}.
	 * @param ctx the parse tree
	 */
	void enterElse_token(RyParser.Else_tokenContext ctx);
	/**
	 * Exit a parse tree produced by {@link RyParser#else_token}.
	 * @param ctx the parse tree
	 */
	void exitElse_token(RyParser.Else_tokenContext ctx);
	/**
	 * Enter a parse tree produced by {@link RyParser#crlf}.
	 * @param ctx the parse tree
	 */
	void enterCrlf(RyParser.CrlfContext ctx);
	/**
	 * Exit a parse tree produced by {@link RyParser#crlf}.
	 * @param ctx the parse tree
	 */
	void exitCrlf(RyParser.CrlfContext ctx);
	/**
	 * Enter a parse tree produced by {@link RyParser#var_class}.
	 * @param ctx the parse tree
	 */
	void enterVar_class(RyParser.Var_classContext ctx);
	/**
	 * Exit a parse tree produced by {@link RyParser#var_class}.
	 * @param ctx the parse tree
	 */
	void exitVar_class(RyParser.Var_classContext ctx);
	/**
	 * Enter a parse tree produced by {@link RyParser#var_instance}.
	 * @param ctx the parse tree
	 */
	void enterVar_instance(RyParser.Var_instanceContext ctx);
	/**
	 * Exit a parse tree produced by {@link RyParser#var_instance}.
	 * @param ctx the parse tree
	 */
	void exitVar_instance(RyParser.Var_instanceContext ctx);
	/**
	 * Enter a parse tree produced by {@link RyParser#var_global}.
	 * @param ctx the parse tree
	 */
	void enterVar_global(RyParser.Var_globalContext ctx);
	/**
	 * Exit a parse tree produced by {@link RyParser#var_global}.
	 * @param ctx the parse tree
	 */
	void exitVar_global(RyParser.Var_globalContext ctx);
	/**
	 * Enter a parse tree produced by {@link RyParser#var}.
	 * @param ctx the parse tree
	 */
	void enterVar(RyParser.VarContext ctx);
	/**
	 * Exit a parse tree produced by {@link RyParser#var}.
	 * @param ctx the parse tree
	 */
	void exitVar(RyParser.VarContext ctx);
	/**
	 * Enter a parse tree produced by {@link RyParser#constant}.
	 * @param ctx the parse tree
	 */
	void enterConstant(RyParser.ConstantContext ctx);
	/**
	 * Exit a parse tree produced by {@link RyParser#constant}.
	 * @param ctx the parse tree
	 */
	void exitConstant(RyParser.ConstantContext ctx);
}
