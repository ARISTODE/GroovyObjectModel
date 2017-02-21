grammar Ry;

prog : expression_list;

expression_list : expression terminator
                | expression_list expression terminator
                | terminator
                ;

expression : class_definition
           | function_definition
           | function_call
           | for_statement
           | while_statement
           | if_statement
           | expression cond_modifier=IF expression
           | unless_statement
           | expression cond_modifier=UNLESS expression
           | comparison_list
           | rvalue
           | return_statement
           | puts_call
           ;

puts_call: puts function_call
         | puts rvalue
         ;

class_definition : CLASS class_name ('<' class_name)? CRLF class_body CRLF? END;
class_name: constant;
class_body: expression_list;

//  function definitions
function_definition : function_header function_body END;

function_header: DEF function_name crlf
               | DEF function_name function_params crlf
               ;

return_statement: RETURN all_result;

function_body: expression_list;

function_name: id;

function_params: LEFT_RBRACKET RIGHT_RBRACKET
               | LEFT_RBRACKET function_definition_param_list RIGHT_RBRACKET
               | function_definition_param_list
               ;

function_definition_param_list: function_definition_param_id
                              | function_definition_param_list COMMA function_definition_param_id
                              ;

function_definition_param_id: id;

// function call
function_call: name=function_name LEFT_RBRACKET params=function_call_param_list RIGHT_RBRACKET
             | name=function_name params=function_call_param_list
             | name=function_name LEFT_RBRACKET RIGHT_RBRACKET
             ;
function_call_param_list: function_call_params;

function_call_params: function_param
                    | function_call_params COMMA function_param
                    ;
function_param: ( function_call_unnamed_param | function_call_named_param);

function_call_unnamed_param: all_result;

function_call_named_param: id op=ASSIGN all_result;

all_result : ( int_result | float_result | string_result | dynamic_result );

while_statement : WHILE cond_expression crlf statement_body END;

for_statement: FOR id IN LEFT_RBRACKET int_t DOTS int_t RIGHT_RBRACKET crlf statement_body END;

elsif_statement : if_elsif_statement;

if_elsif_statement : ELSIF cond_expression crlf statement_body
                   | ELSIF cond_expression crlf statement_body else_token crlf statement_body
                   | ELSIF cond_expression crlf statement_body if_elsif_statement
                   ;

if_statement : IF cond_expression crlf statement_body END
             | IF cond_expression crlf statement_body else_token crlf statement_body END
             | IF cond_expression crlf statement_body elsif_statement END
             ;

unless_statement: UNLESS cond_expression crlf statement_body END
                | UNLESS cond_expression crlf statement_body else_token crlf statement_body END
                | UNLESS cond_expression crlf statement_body elsif_statement END
                ;

all_assignment : ( int_assignment | float_assignment | string_assignment | dynamic_assignment );
cond_expression : comparison_list;

statement_body : statement_expression_list;

statement_expression_list : expression terminator
                          | statement_expression_list expression terminator
                          ;

assignment : var_id=lvalue op=ASSIGN rvalue
           | var_id=lvalue op=( PLUS_ASSIGN | MINUS_ASSIGN | MUL_ASSIGN | DIV_ASSIGN | MOD_ASSIGN | EXP_ASSIGN ) rvalue
           ;

dynamic_assignment : var_id=lvalue op=ASSIGN dynamic_result
                   | var_id=lvalue op=( PLUS_ASSIGN | MINUS_ASSIGN | MUL_ASSIGN | DIV_ASSIGN | MOD_ASSIGN | EXP_ASSIGN ) dynamic_result
                   ;

int_assignment : var_id=lvalue op=ASSIGN int_result
               | var_id=lvalue op=( PLUS_ASSIGN | MINUS_ASSIGN | MUL_ASSIGN | DIV_ASSIGN | MOD_ASSIGN | EXP_ASSIGN ) int_result
               ;

float_assignment : var_id=lvalue op=ASSIGN float_result
                 | var_id=lvalue op=( PLUS_ASSIGN | MINUS_ASSIGN | MUL_ASSIGN | DIV_ASSIGN | MOD_ASSIGN | EXP_ASSIGN ) float_result
                 ;

string_assignment : var_id=lvalue op=ASSIGN string_result
                  | var_id=lvalue op=PLUS_ASSIGN string_result
                  ;

dynamic_result : dynamic_result op=( MUL | DIV | MOD ) int_result
               | int_result op=( MUL | DIV | MOD ) dynamic_result
               | dynamic_result op=( MUL | DIV | MOD ) float_result
               | float_result op=( MUL | DIV | MOD ) dynamic_result
               | dynamic_result op=( MUL | DIV | MOD ) dynamic_result
               | dynamic_result op=MUL string_result
               | string_result op=MUL dynamic_result
               | dynamic_result op=( PLUS | MINUS ) int_result
               | int_result op=( PLUS | MINUS ) dynamic_result
               | dynamic_result op=( PLUS | MINUS )  float_result
               | float_result op=( PLUS | MINUS )  dynamic_result
               | dynamic_result op=( PLUS | MINUS ) dynamic_result
               | LEFT_RBRACKET dynamic_result RIGHT_RBRACKET
               | dynamic
               ;

dynamic : id
        | function_call_assignment
        ;

function_call_assignment: function_call;

int_result : int_result op=( MUL | DIV | MOD ) int_result
           | int_result op=( PLUS | MINUS ) int_result
           | LEFT_RBRACKET int_result RIGHT_RBRACKET
           | int_t
           ;

float_result : float_result op=( MUL | DIV | MOD ) float_result
             | int_result op=( MUL | DIV | MOD ) float_result
             | float_result op=( MUL | DIV | MOD ) int_result
             | float_result op=( PLUS | MINUS ) float_result
             | int_result op=( PLUS | MINUS )  float_result
             | float_result op=( PLUS | MINUS )  int_result
             | LEFT_RBRACKET float_result RIGHT_RBRACKET
             | float_t
             ;

string_result : string_result op=MUL int_result
              | string_result op=PLUS string_result
              | literal_t
              ;

comparison_list : left=comparison op=AND right=comparison_list
                | left=comparison op=OR right=comparison_list
                | LEFT_RBRACKET comparison_list RIGHT_RBRACKET
                | comparison
                ;

comparison : left=comp_var op=( LESS | GREATER | LESS_EQUAL | GREATER_EQUAL ) right=comp_var
           | left=comp_var op=( EQUAL | NOT_EQUAL ) right=comp_var
           ;

comp_var : all_result
         | id
         ;

lvalue : var
       | var_class
       | var_instance
       | var_global
       | constant
       ;

rvalue : lvalue
       | int_result
       | float_result
       | string_result
       | dynamic_assignment
       | string_assignment
       | float_assignment
       | int_assignment
       | assignment
       | literal_t
       | bool_t
       | float_t
       | int_t
       | nil_t

       | rvalue EXP rvalue

       | NOT rvalue

       | rvalue ( MUL | DIV | MOD ) rvalue
       | rvalue ( PLUS | MINUS ) rvalue

       | rvalue ( LESS | GREATER | LESS_EQUAL | GREATER_EQUAL ) rvalue

       | rvalue ( EQUAL | NOT_EQUAL ) rvalue

       | rvalue ( OR | AND ) rvalue

       | LEFT_RBRACKET rvalue RIGHT_RBRACKET
       ;


literal_t : LITERAL;

float_t : FLOAT;

int_t : INT;

bool_t : TRUE
       | FALSE
       ;

nil_t : NIL;


id : ID;

terminator : terminator SEMICOLON
           | terminator crlf
           | SEMICOLON
           | crlf
           ;

else_token : ELSE;

crlf : CRLF;

fragment ESCAPED_QUOTE : '\\"';
LITERAL : '"' ( ESCAPED_QUOTE | ~('\n'|'\r') )*? '"'
        | '\'' ( ESCAPED_QUOTE | ~('\n'|'\r') )*? '\'';

COMMA : ',';
SEMICOLON : ';';
CRLF : '\n';

var_class : '@@'ID;
var_instance : '@'ID;
var_global : '$'ID;
var: ID;
constant: CONSTANT;

CLASS: 'class';
END: 'end';
DEF: 'def';

IF: 'if';
ELSE : 'else';
ELSIF : 'elsif';
UNLESS : 'unless';
WHILE : 'while';
RETRY : 'retry';
BREAK : 'break';
FOR : 'for';

TRUE : 'true';
FALSE : 'false';

PLUS : '+';
MINUS : '-';
MUL : '*';
DIV : '/';
MOD : '%';
EXP : '**';


ASSIGN : '=';
PLUS_ASSIGN : '+=';
MINUS_ASSIGN : '-=';
MUL_ASSIGN : '*=';
DIV_ASSIGN : '/=';
MOD_ASSIGN : '%=';
EXP_ASSIGN : '**=';


EQUAL : '==';
NOT_EQUAL : '!=';
GREATER : '>';
LESS : '<';
LESS_EQUAL : '<=';
GREATER_EQUAL : '>=';

AND : 'and' | '&&';
OR : 'or' | '||';
NOT : 'not' | '!';

IN: 'in';
DOTS: '..';

LEFT_RBRACKET : '(';
RIGHT_RBRACKET : ')';
LEFT_SBRACKET : '[';
RIGHT_SBRACKET : ']';

NIL : 'nil';
RETURN: 'return';

SL_COMMENT : ('#' ~('\r' | '\n')* '\n') -> skip;
ML_COMMENT : ('=begin' .*? '=end\n') -> skip;
WS : (' '|'\t')+ -> skip;

INT : [0-9]+;
FLOAT : [0-9]*'.'[0-9]+;
CONSTANT: [A-Z_]+[a-zA-Z]*;
ID : [a-zA-Z_][a-zA-Z0-9_]*;


puts: 'puts';