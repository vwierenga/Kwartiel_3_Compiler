import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Created by Wilco on 09-Mar-17.
 */
public class Builder extends natoBaseVisitor<Type>{

    private int whilelabelCounter = 0;
    private int iflabelCounter = 0;
    private int logicalexpressionlabelCounter = 0;
    private HashMap<Integer, DataType> frameStorage = new HashMap<>();


    @Override
    public Type visitProgram(natoParser.ProgramContext ctx) {
        System.out.println(".class public " + ctx.MESSAGE());
        System.out.println(".super java/lang/Object");
        System.out.println(".method public <init>()V");
        System.out.println("aload_0 ");
        System.out.println("invokenonvirtual java/lang/Object/<init>()V");
        System.out.println("return");
        System.out.println(".end method");
        System.out.println(".method public static main([Ljava/lang/String;)V");
        System.out.println(".limit stack 60     ; Setup the stack size for this method");
        System.out.println(".limit locals 40    ; Setup the number of locals for this method (number of parameters + local variables)");

        super.visitProgram(ctx);
        //Code Here

        System.out.println("return                              ; End the method (important!!!)");
        System.out.println(".end method");

        return null;
    }

    @Override
    public Type visitPrintStatement(natoParser.PrintStatementContext ctx) {
        /*
        getstatic java/lang/System/out Ljava/io/PrintStream;
	    ldc "testy"
	    invokevirtual java/io/PrintStream/println(Ljava/lang/String;)V
         */

        System.out.println("getstatic java/lang/System/out Ljava/io/PrintStream; ");
        System.out.println("ldc \"" + ctx.MESSAGE(0) + "\"");
        System.out.println("invokevirtual java/io/PrintStream/println(Ljava/lang/String;)V");
        return null;
    }

    @Override
    public Type visitVarDecAndInit(natoParser.VarDecAndInitContext ctx) {
        DataType value = (DataType) visit(ctx.expr);
        DataType name = (DataType) visit(ctx.MESSAGE());

        if (value != null) {
            System.out.println("ldc " + value);
            System.out.println("istore " + (frameStorage.size() + 1));
        } else {
            System.out.println("ldc 0");
            System.out.println("istore " + (frameStorage.size() + 1));
        }
        frameStorage.put((frameStorage.size() + 1), name);

        return null;
    }

    @Override
    public Type visitVarAssignment(natoParser.VarAssignmentContext ctx) {
        DataType value = (DataType) visit(ctx.expr);
        DataType name = (DataType) visit(ctx.MESSAGE());
        int valueIndex;

        for (Map.Entry<Integer, DataType> map : frameStorage.entrySet()) {
            if (Objects.equals(map, name)) {
                System.out.println("iload " + map.getKey());
                System.out.println("ldc " + value);
                System.out.println("istore " + map.getKey());
            }
        }

        return null;
    }

    @Override
    public Type visitIfStatement(natoParser.IfStatementContext ctx) {
        return visit(ctx.ifStmt());
    }

    @Override
    public Type visitWhileStatement(natoParser.WhileStatementContext ctx) {
        return visit(ctx.whileStmt());
    }

    @Override
    public Type visitFunctionStatement(natoParser.FunctionStatementContext ctx) {
        return visit(ctx.functionStmt());
    }

    @Override
    public Type visitCopyStatement(natoParser.CopyStatementContext ctx) {
        return visit(ctx.copyStmt());
    }

    @Override
    public Type visitOperationStatement(natoParser.OperationStatementContext ctx) {
        return visit(ctx.operationStmt());
    }

    @Override
    public Type visitIfStmt(natoParser.IfStmtContext ctx) {
        //false == 0
        //true != 0
        visit(ctx.logicalExpression());

        System.out.println("tableswitch 0");
        System.out.println("iffalse" + iflabelCounter);
        System.out.println("iftrue" + iflabelCounter);
        System.out.println("default : iftrue" + iflabelCounter);
        System.out.println("iffalse" + iflabelCounter + ":");
        if(ctx.elseCode != null){
            visit(ctx.elseCode);
        }
        System.out.println("goto endif" + iflabelCounter);
        System.out.println("iftrue" + iflabelCounter + ":");
        visit(ctx.ifCode);
        System.out.println("endif" + iflabelCounter + ":");
        iflabelCounter++;

        //tableswitch 0
        //Label1
        //Label2
        //default : DefaultLabel

        //Label1:
        //... got 0

        //Label2:
        //... got 1

        //DefaultLabel:
        //... got something else

        return null;
    }

    @Override
    public Type visitWhileStmt(natoParser.WhileStmtContext ctx) {

        System.out.println("beginwhile" + whilelabelCounter + ":");
        visit(ctx.logicalExpression());
        System.out.println("tableswitch 0");
        System.out.println("whilefalse" + whilelabelCounter);
        System.out.println("whiletrue" + whilelabelCounter);
        System.out.println("default : whiletrue" + whilelabelCounter);
        System.out.println("whilefalse" + whilelabelCounter + ":");
        System.out.println("goto endwhile" + whilelabelCounter);
        System.out.println("whiletrue" + whilelabelCounter + ":");
        for (natoParser.StatementContext context : ctx.statement()){
            visit(context);
        }
        System.out.println("goto beginwhile" + whilelabelCounter);
        System.out.println("endwhile" + whilelabelCounter + ":");
        whilelabelCounter++;

        return null;
    }

    @Override
    public Type visitFunctionStmt(natoParser.FunctionStmtContext ctx) {
        return null;
    }

    @Override
    public Type visitOperationStmt(natoParser.OperationStmtContext ctx) {
        return null;
    }

    @Override
    public Type visitCopyStmt(natoParser.CopyStmtContext ctx) {
        return null;
    }

    @Override
    public Type visitOperationParameters(natoParser.OperationParametersContext ctx) {
        return null;
    }

    @Override
    public Type visitType(natoParser.TypeContext ctx) {
        return null;
    }

    @Override
    public Type visitMinusExpression(natoParser.MinusExpressionContext ctx) {
        visit(ctx.expression());
        System.out.println("ineg");

        return null;
    }

    @Override
    public Type visitConfirmExpression(natoParser.ConfirmExpressionContext ctx) {
        return null;
    }

    @Override
    public Type visitIntExpression(natoParser.IntExpressionContext ctx) {
        System.out.println("ldc " + ctx.getText());

        return null;
    }

    @Override
    public Type visitMessageExpression(natoParser.MessageExpressionContext ctx) {
        return super.visitMessageExpression(ctx);
    }

    @Override
    public Type visitMultiExpression(natoParser.MultiExpressionContext ctx) {
        visit(ctx.leftExpr);
        visit(ctx.rightExpr);

        if (ctx.op.getText().equals("*")){
            System.out.println("imul");
        } else if (ctx.op.getText().equals("/")){
            System.out.println("idiv");
        }

        return null;
    }

    @Override
    public Type visitModExpression(natoParser.ModExpressionContext ctx) {
        visit(ctx.leftExpr);
        visit(ctx.rightExpr);

        System.out.println("irem");

        return null;
    }

    @Override
    public Type visitSubExpression(natoParser.SubExpressionContext ctx) {
        visit(ctx.leftExpr);
        visit(ctx.rightExpr);

        if (ctx.op.getText().equals("+")){
            System.out.println("iadd");
        } else if (ctx.op.getText().equals("-")){
            System.out.println("isub");
        }

        return null;
    }

    @Override
    public Type visitParentExpression(natoParser.ParentExpressionContext ctx) {
        visit(ctx.expression());

        return null;
    }

    @Override
    public Type visitParentLogicalExpresssion(natoParser.ParentLogicalExpresssionContext ctx) {
        visit(ctx.leftExpr);
        visit(ctx.rightExpr);

        //false == 0
        //true != 0

        //System.out.println("icmpg"); //Compare the two ints on the stack

        // The integer result on the stack is:
        //     0 if local left equals right
        //     -1 if local left is less than right
        //     1 if local left is greater than right

        //If the int on the stack is 0, jump to Label1.
        //If it is 1, jump to Label2.
        //Otherwise jump to DefaultLabel.

        //'<' | '<=' | '=' | '>=' | '>'
        //if_icmpTYPE
        //TYPE EQ = == NE = != GT = > GE = >= LT = < LE <=

        if(ctx.op.getText().equals("<")){
            System.out.println("if_icmplt then" + logicalexpressionlabelCounter);
        } else if(ctx.op.getText().equals("<=")) {
            System.out.println("if_icmple then" + logicalexpressionlabelCounter);
        } else if(ctx.op.getText().equals("=")) {
            System.out.println("if_icmpeq then" + logicalexpressionlabelCounter);
        } else if(ctx.op.getText().equals(">=")) {
            System.out.println("if_icmpge then" + logicalexpressionlabelCounter);
        } else if(ctx.op.getText().equals(">")){
            System.out.println("if_icmpgt then" + logicalexpressionlabelCounter);
        }


        System.out.println("else" + logicalexpressionlabelCounter + ":");
        if(ctx.not != null){
            System.out.println("ldc 1");
        } else {
            System.out.println("ldc 0");
        }
        System.out.println("goto endlogicalexpression" + logicalexpressionlabelCounter);
        System.out.println("then" + logicalexpressionlabelCounter + ":");
        if(ctx.not != null){
            System.out.println("ldc 0");
        } else {
            System.out.println("ldc 1");
        }
        System.out.println("endlogicalexpression" + logicalexpressionlabelCounter + ":");

        logicalexpressionlabelCounter++;


        //tableswitch 0
        //Label1
        //Label2
        //default : DefaultLabel

        //Label1:
        //... got 0

        //Label2:
        //... got 1

        //DefaultLabel:
        //... got something else




        return null;
    }

    @Override
    public Type visitOrLogicalExpression(natoParser.OrLogicalExpressionContext ctx) {
        visit(ctx.leftLogicalExpr);
        visit(ctx.rightLogicalExpr);



        return null;
    }

    @Override
    public Type visitNotLogicalExpression(natoParser.NotLogicalExpressionContext ctx) {
        visit(ctx.leftLogicalExpr);
        visit(ctx.rightLogicalExpr);

        return null;
    }

    @Override
    public Type visitAndLogicalExpression(natoParser.AndLogicalExpressionContext ctx) {
        visit(ctx.leftLogicalExpr);
        visit(ctx.rightLogicalExpr);

        return null;
    }
}
