grammar test;
expr: NUMBER
	| '-' expr
	| '(' expr ')'
	| expr ('*'|'/') expr
	| expr ('+'|'-') expr
	;
NUMBER: [0-9]+;