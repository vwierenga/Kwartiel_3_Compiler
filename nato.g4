grammar nato;

program: 'this is ' MESSAGE ENDL (statement ENDL)+ 'over and out';

statement: 'say ' MESSAGE+                      #printStatement
           | type MESSAGE ('<-' expr=expression+)?   #varDecAndInit
           | MESSAGE '<-' expr=expression+           #varAssignment
           | ifStmt                             #ifStatement
           | whileStmt                          #whileStatement
           | functionStmt                       #functionStatement
           | copyStmt                           #copyStatement
           | operationStmt                      #operationStatement
           ;

ifStmt: 'verify ' logicalExpression ENDL (ifCode = statement ENDL)+ ('wrong ' elseCode = statement+)? 'endVerify ';

whileStmt: 'execute all before ' logicalExpression ENDL (statement ENDL)+ 'endExecute ';

functionStmt: 'operation ' operationName=MESSAGE (operationParameters)? ENDL (statement ENDL)+ ('payload is ' payloadType=type MESSAGE ENDL)?  'endOperation ';

operationStmt: 'start operation ' operationName=MESSAGE;

copyStmt: ((type) MESSAGE)? 'copy';

operationParameters: (type MESSAGE)+;

type : ('falcon' | 'message' | 'confirm');
expression:	'('	expression	')'                                                 #parentExpression
		    |	'-'	expression                                                  #minusExpression
		    |	leftExpr=expression	op=('*'	|	'/')	rightExpr=expression    #multiExpression
		    |	leftExpr=expression	op=('+'	|	'-')	rightExpr=expression    #subExpression
		    |   leftExpr=expression	('%')	rightExpr=expression                #modExpression
   		    |	FALCON                                                          #intExpression
   		    |   MESSAGE                                                         #messageExpression
   		    |   CONFIRM                                                         #confirmExpression
		    ;

logicalExpression: leftExpr=expression (not='!'? op=('<' | '<=' | '=' | '>=' | '>')) rightExpr=expression   #parentLogicalExpresssion
                   | leftLogicalExpr=logicalExpression op='OR' rightLogicalExpr=logicalExpression             #orLogicalExpression
                   | leftLogicalExpr=logicalExpression op='AND' rightLogicalExpr=logicalExpression          #andLogicalExpression
                   | leftLogicalExpr=logicalExpression op='NOT' rightLogicalExpr=logicalExpression          #notLogicalExpression
                   ;


ENDL: 'over';
FALCON: [0-9]+;
CONFIRM: 'AFFIRMATIVE' | 'NEGATIVE';
MESSAGE: [A-Za-z0-9]+;
WS: [ \t\r\n]+ -> skip;


/*
True = Affirmative
False = Negative

Gebruikersinvoer gereed = Ready to Copy
Gebruikersinvoer correct = Wilco
Gebruikersinvoer incorrect = Say again

Crash = Mayday, Mayday, Mayday,

Begin = This is <programmanaam> , over
End = Over and Out

Writeout = Say

While = Execute ALL BEFORE (iets = waarde)

GET = Obtain

Function = ROUTINE
Return = Dispatch
*/