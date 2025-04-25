grammar Config;

config : (importStatement | statement)* EOF;

importStatement : 'import' STRING ';' ;

statement
    : assignment
    | blockDefinition
    ;

assignment : ID '=' value ';'? ;

blockDefinition : ID block ;

block : '{' statement* '}' ;

value
    : STRING                                  #stringValue
    | NUMBER                                  #numberValue
    | array                                   #arrayValue
    | block                                   #blockValue
    | dateValue                               #dateLiteral
    | ID                                      #idReference
    ;

array : '[' value (',' value)* ']' ;

dateValue : 'LocalDate.of(' NUMBER ',' NUMBER ',' NUMBER ')' ;

ID : [a-zA-Z_][a-zA-Z0-9_]* ;
STRING : '"' .*? '"' ;
NUMBER : [-]?[0-9]+ ;
WS : [ \t\r\n]+ -> skip ;