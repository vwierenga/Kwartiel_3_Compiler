/**
 * Created by Laptop-oud on 16-2-2017.
 */
public class Checker extends natoBaseVisitor<Type>{

// override methods

    public enum type {
        MESSAGE, CONFIRM, FALCON
    }


    @Override
    public Type visitProgram(natoParser.ProgramContext ctx) {
        Scope scope = Scope.getInstance();
        return super.visitProgram(ctx);
        //return new MethodType(null, null);
    }

    @Override
    public Type visitPrintStatement(natoParser.PrintStatementContext ctx) {
        return super.visitPrintStatement(ctx);
    }

    @Override
    public Type visitVarDecAndInit(natoParser.VarDecAndInitContext ctx) {
        DataType dataType = null;
        String variableName = ctx.MESSAGE().getText();

        if (ctx.type().getText().equals("message")){
            dataType = new DataType(0);
        } else if (ctx.type().getText().equals("confirm")){
            dataType = new DataType(1);
        } else if(ctx.type().getText().equals("falcon")) {
            dataType = new DataType(2);
        } else {
            //TODO: Throw error
            return null;
        }

        if(ctx.expr != null){
            DataType expressionDataType = (DataType) visit(ctx.expr);
            if (expressionDataType.getType() != dataType.getType()){
                //TODO: Throw exception datatypes don't match
            }
        }

        Scope.getInstance().declareVariable(variableName, dataType);

        return dataType;
    }

    @Override
    public Type visitVarAssignment(natoParser.VarAssignmentContext ctx) {
        Scope scope = Scope.getInstance();
        Type msg = visit(ctx.MESSAGE());
        Type expr = visit(ctx.expr);
        Symbol var = scope.lookupVariable(msg.toString());

        if (var.type.toString().equals("FALCON")) {

            try {
                int i = Integer.parseInt(expr.toString());
            } catch (NumberFormatException nfe) {
                throw new NumberFormatException();
            }
            return super.visitVarAssignment(ctx);

        } else if (var.type.toString().equals("MESSAGE")) {
            return super.visitVarAssignment(ctx);

        } else if (var.type.toString().equals("CONFIRM") && expr.toString().equals("AFFIRMATIVE") || expr.toString().equals("NEGATIVE")) {
            return super.visitVarAssignment(ctx);
        }

        throw new RuntimeException();
    }

    //FIXME: Else code doesn't function yet. Grammar seems wrong?
    @Override
    public Type visitIfStatement(natoParser.IfStatementContext ctx) {
        Scope.getInstance().openScope();
        Type logicalExpression = visit(ctx.ifStmt().logicalExpression());
        Type ifstmtCode = visit(ctx.ifStmt().ifCode);

        if (logicalExpression == null || ifstmtCode == null){
            return null;
        }

        if(ctx.ifStmt().elseCode != null){
            System.out.println("else code exists");
            Type elsestmtCode = visit(ctx.ifStmt().elseCode);
        } else {
            System.out.println("else code doesn't exist");
        }
        Scope.getInstance().closeScope();
        return null;
    }

    @Override
    public Type visitWhileStatement(natoParser.WhileStatementContext ctx) {

        Scope scope = Scope.getInstance().openScope();
        Type logExpr = visit(ctx.whileStmt().logicalExpression());

        if (logExpr != null) {

        } else {
            throw new RuntimeException();
        }

        scope.closeScope();
        return super.visitWhileStatement(ctx);
    }

    @Override
    public Type visitFunctionStatement(natoParser.FunctionStatementContext ctx) {
        return super.visitFunctionStatement(ctx);
    }

    @Override
    public Type visitCopyStatement(natoParser.CopyStatementContext ctx) {
        return super.visitCopyStatement(ctx);
    }

    @Override
    public Type visitOperationStatement(natoParser.OperationStatementContext ctx) {
        return super.visitOperationStatement(ctx);
    }

    @Override
    public Type visitIfStmt(natoParser.IfStmtContext ctx) {
        return super.visitIfStmt(ctx);
    }

    @Override
    public Type visitWhileStmt(natoParser.WhileStmtContext ctx) {
        return super.visitWhileStmt(ctx);
    }

    @Override
    public Type visitFunctionStmt(natoParser.FunctionStmtContext ctx) {
        return super.visitFunctionStmt(ctx);
    }

    @Override
    public Type visitOperationStmt(natoParser.OperationStmtContext ctx) {
        return super.visitOperationStmt(ctx);
    }

    @Override
    public Type visitCopyStmt(natoParser.CopyStmtContext ctx) {
        return super.visitCopyStmt(ctx);
    }

    @Override
    public Type visitType(natoParser.TypeContext ctx) {
        return super.visitType(ctx);
    }

    @Override
    public Type visitMinusExpression(natoParser.MinusExpressionContext ctx) {
        return super.visitMinusExpression(ctx);
    }

    @Override
    public Type visitConfirmExpression(natoParser.ConfirmExpressionContext ctx) {
        return super.visitConfirmExpression(ctx);
    }

    @Override
    public Type visitIntExpression(natoParser.IntExpressionContext ctx) {
        try {
            int testy = Integer.parseInt(ctx.FALCON().getText());
            System.out.println("Falcon: " + testy);
        } catch (NumberFormatException e){
            //TODO: Throw exception
            return null;
        }

        return new DataType(2);
    }

    @Override
    public Type visitMessageExpression(natoParser.MessageExpressionContext ctx) {
        return super.visitMessageExpression(ctx);
    }

    @Override
    public Type visitMultiExpression(natoParser.MultiExpressionContext ctx) {
        Type x = visit(ctx.leftExpr);
        Type y = visit(ctx.rightExpr);

        String mod = ctx.op.getText();
        if (x.getClass().toString().equals("Falcon") && y.getClass().toString().equals("Falcon")) {
            return super.visitMultiExpression(ctx);
        } else {
            throw new RuntimeException();
        }
    }

    @Override
    public Type visitModExpression(natoParser.ModExpressionContext ctx) {
        Type x = visit(ctx.leftExpr);
        Type y = visit(ctx.rightExpr);

        if (x.getClass().toString().equals("Falcon") && y.getClass().toString().equals("Falcon")) {
            return super.visitModExpression(ctx);
        } else {
            throw new RuntimeException();
        }

    }

    @Override
    public Type visitSubExpression(natoParser.SubExpressionContext ctx) {
        //System.out.println( ctx.leftExpr.getClass().toString());
        Type left = visit(ctx.leftExpr);
        Type right = visit(ctx.rightExpr);

        if (!(ctx.op.getText().equals("-") || left == null || right == null)) {
            //TODO: Throw Exception
            return null;
        }

        return new DataType(3);
    }

    @Override
    public Type visitParentExpression(natoParser.ParentExpressionContext ctx) {
        return super.visitParentExpression(ctx);
    }

    @Override
    public Type visitParentLogicalExpresssion(natoParser.ParentLogicalExpresssionContext ctx) {
        return super.visitParentLogicalExpresssion(ctx);
    }

    @Override
    public Type visitOrLogicalExpression(natoParser.OrLogicalExpressionContext ctx) {
        return super.visitOrLogicalExpression(ctx);
    }

    @Override
    public Type visitNotLogicalExpression(natoParser.NotLogicalExpressionContext ctx) {
        return super.visitNotLogicalExpression(ctx);
    }

    @Override
    public Type visitAndLogicalExpression(natoParser.AndLogicalExpressionContext ctx) {
        return super.visitAndLogicalExpression(ctx);
    }
}

