grammar nato;

program: 'this is ' message ENDL  statement+ 'over and out';

statement: ENDL;
ifStmt: 'verify' ('wrong')? ;

ENDL: 'over';
FALCON: [0-9]+;
MESSAGE: [A-Za-z]+


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