grammar nato;

program: 'this is ' MESSAGE ENDL  statement+ 'over and out';

statement: 'say ' MESSAGE+ ENDL;

expression:	'('	expression	')'                                                 #parentExpression
		    |	'-'	expression                                                  #minusExpression
		    |	leftExpr=expression	op=('*'	|	'/')	rightExpr=expression    #multiExpression
		    |	leftExpr=expression	op=('+'	|	'-')	rightExpr=expression    #subExpression
		    |   leftExpr=expression	('%')	rightExpr=expression                #modExpression
   		    |	INT                                                             #intExpression
		;


ifStmt: 'verify' ('wrong')? ;


ENDL: 'over';
FALCON: [0-9]+;
MESSAGE: [A-Za-z]+;
CONFIRM: 'TRUE' | 'FALSE';
WS: ' '+ -> skip;


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