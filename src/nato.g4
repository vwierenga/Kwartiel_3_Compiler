grammar nato;

program: 'this is ' MESSAGE ENDL  statement+ 'over and out';

statement: 'say ' MESSAGE+ ENDL
           | 'falcon ' MESSAGE expression+ ENDL
           | 'message ' MESSAGE MESSAGE+ ENDL
           | 'confirm ' MESSAGE CONFIRM ENDL
           | ifStmt
           | whileStmt
           | functionStmt
           | copyStmt
           | operationStmt
           ;

ifStmt: 'verify ' logicalExpression ENDL statement+ ('wrong ' statement+)? 'endVerify ' ENDL;

whileStmt: 'execute all before ' logicalExpression ENDL statement+ 'endExecute ' ENDL;

functionStmt: 'operation ' MESSAGE ((('falcon ' | 'message ' | 'confirm ') MESSAGE)+)? ENDL statement+ ('payload = ' (FALCON | MESSAGE | CONFIRM) ENDL)?  'endOperation ' ENDL;

operationStmt: ('falcon ' MESSAGE | 'message ' MESSAGE | 'confirm ' MESSAGE)? 'start operation ' MESSAGE ENDL;

copyStmt: ('falcon ' | 'message ' | 'confirm ') MESSAGE 'copy' ENDL
        | 'copy' ENDL
        ;

expression:	'('	expression	')'                                                 #parentExpression
		    |	'-'	expression                                                  #minusExpression
		    |	leftExpr=expression	op=('*'	|	'/')	rightExpr=expression    #multiExpression
		    |	leftExpr=expression	op=('+'	|	'-')	rightExpr=expression    #subExpression
		    |   leftExpr=expression	('%')	rightExpr=expression                #modExpression
   		    |	FALCON                                                             #intExpression
		    ;

logicalExpression: expression ('!'? ('<' | '<=' | '=' | '>=' | '>')) expression
                   | logicalExpression 'OR' logicalExpression
                   | logicalExpression 'END' logicalExpression
                   | logicalExpression 'NOT' logicalExpression
                   ;


ENDL: 'over';
FALCON: [0-9]+;
MESSAGE: [A-Za-z]+;
CONFIRM: 'TRUE' | 'FALSE';
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