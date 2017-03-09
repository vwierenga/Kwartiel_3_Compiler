/**
 * Created by Wilco on 09-Mar-17.
 */
public class Builder extends natoBaseVisitor<Type>{
    @Override
    public Type visitProgram(natoParser.ProgramContext ctx) {
        return super.visitProgram(ctx);
    }

    @Override
    public Type visitPrintStatement(natoParser.PrintStatementContext ctx) {
        return super.visitPrintStatement(ctx);
    }

    @Override
    public Type visitVarDecAndInit(natoParser.VarDecAndInitContext ctx) {
        return super.visitVarDecAndInit(ctx);
    }

    @Override
    public Type visitVarAssignment(natoParser.VarAssignmentContext ctx) {
        return super.visitVarAssignment(ctx);
    }

    @Override
    public Type visitIfStatement(natoParser.IfStatementContext ctx) {
        return super.visitIfStatement(ctx);
    }

    @Override
    public Type visitWhileStatement(natoParser.WhileStatementContext ctx) {
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
    public Type visitOperationParameters(natoParser.OperationParametersContext ctx) {
        return super.visitOperationParameters(ctx);
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
        return super.visitIntExpression(ctx);
    }

    @Override
    public Type visitMessageExpression(natoParser.MessageExpressionContext ctx) {
        return super.visitMessageExpression(ctx);
    }

    @Override
    public Type visitMultiExpression(natoParser.MultiExpressionContext ctx) {
        return super.visitMultiExpression(ctx);
    }

    @Override
    public Type visitModExpression(natoParser.ModExpressionContext ctx) {
        return super.visitModExpression(ctx);
    }

    @Override
    public Type visitSubExpression(natoParser.SubExpressionContext ctx) {
        return super.visitSubExpression(ctx);
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
