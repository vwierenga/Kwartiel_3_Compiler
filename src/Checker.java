import org.antlr.v4.runtime.tree.TerminalNode;

import java.util.ArrayList;

/**
 * Created by Laptop-oud on 16-2-2017.
 */
public class Checker extends natoBaseVisitor<Type> {

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
        if (ctx.MESSAGE() != null) {
            for (TerminalNode node : ctx.MESSAGE()) {

            }
        } else {
            throw new CompileException("Line" + ctx.start.getLine() + " Incorrect message after say");
            //return null;
        }
        return new DataType(0);
        //return super.visitPrintStatement(ctx);
    }

    @Override
    public Type visitVarDecAndInit(natoParser.VarDecAndInitContext ctx) {
        DataType dataType = null;
        String variableName = ctx.MESSAGE().getText();

        if (ctx.type().getText().equals("message")) {
            dataType = new DataType(0);
        } else if (ctx.type().getText().equals("confirm")) {
            dataType = new DataType(1);
        } else if (ctx.type().getText().equals("falcon")) {
            dataType = new DataType(2);
        } else {
            throw new CompileException("Line" + ctx.start.getLine() + " expecting a message, confirm or falcon after " + variableName);
            //return null;
        }

        if (ctx.expr != null) {
            DataType expressionDataType = (DataType) visit(ctx.expr);
            if (expressionDataType.getType() != dataType.getType()) {
                throw new CompileException("Line" + ctx.start.getLine() + " datatypes don't match. Expecting a " + ctx.type().getText());
                //return null;
            }
        }

        Scope.getInstance().declareVariable(variableName, dataType);

        return dataType;
    }

    @Override
    public Type visitVarAssignment(natoParser.VarAssignmentContext ctx) {
        Scope scope = Scope.getInstance();
        String msg = ctx.MESSAGE().getText();
        Type expr = visit(ctx.expr);
        Symbol var = scope.lookupVariable(msg);

        if (var == null) {
            throw new CompileException("Line" + ctx.start.getLine() + " " + msg + " is not declared");
            //return null;
        }

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

    //FIXME: Else code doesn't function yet. Grammar seems wrong?
    @Override
    public Type visitIfStmt(natoParser.IfStmtContext ctx) {
        Scope.getInstance().openScope();
        Type logicalExpression = visit(ctx.logicalExpression());
        Type ifstmtCode = visit(ctx.ifCode);

        if (logicalExpression == null || ifstmtCode == null) {
            return null;
        }

        if (ctx.elseCode != null) {
            System.out.println("else code exists");
            Type elsestmtCode = visit(ctx.elseCode);
        } else {
            System.out.println("else code doesn't exist");
        }
        Scope.getInstance().closeScope();
        return null;
    }

    @Override
    public Type visitWhileStmt(natoParser.WhileStmtContext ctx) {
        Scope scope = Scope.getInstance().openScope();
        Type logExpr = visit(ctx.logicalExpression());

        if (logExpr != null) {

        } else {
            throw new RuntimeException();
        }

        scope.closeScope();
        return null;
    }

    @Override
    public Type visitFunctionStmt(natoParser.FunctionStmtContext ctx) {

        if (ctx.operationName == null) {
            return null;
            //TODO: Throw Exception
        }

        DataType payloadType = null;
        if (ctx.payloadType != null) {
            if (ctx.payloadType.getText().equals("message")) {
                payloadType = new DataType(0);
            } else if (ctx.payloadType.getText().equals("confirm")) {
                payloadType = new DataType(1);
            } else if (ctx.payloadType.getText().equals("falcon")) {
                payloadType = new DataType(2);
            } else {
                //TODO: Throw Exception
                return null;
            }
        } else {
            //TODO: Throw Exception
            return null;
        }

        ArrayList<DataType> parameterTypes = new ArrayList<>();

        for (natoParser.TypeContext typeContext : ctx.operationParameters().type()) {
            DataType parameterType = null;
            if (typeContext.getText().equals("message")) {
                parameterType = new DataType(0);
            } else if (typeContext.getText().equals("confirm")) {
                parameterType = new DataType(1);
            } else if (typeContext.getText().equals("falcon")) {
                parameterType = new DataType(2);
            } else {
                //TODO: Throw Exception
                return null;
            }

            parameterTypes.add(parameterType);
        }

        MethodType methodType = new MethodType(payloadType, parameterTypes);
        Scope.getInstance().declareMethod(ctx.operationName.getText(), methodType);
        Scope.getInstance().openScope();
        for (natoParser.StatementContext context : ctx.statement()) {
            Type visitReturn = visit(context);
            if (visitReturn == null) {
                //TODO: Throw Exception
                return null;
            }
        }
        Scope.getInstance().closeScope();

        return methodType;
    }

    @Override
    public Type visitOperationStmt(natoParser.OperationStmtContext ctx) {
        if(ctx.operationName == null) {
            return null;
            //TODO: Throw Exception
        }
        Symbol symbol = Scope.getInstance().lookupMethod(ctx.operationName.getText());
        if (symbol == null){
            return null;
            //TODO: Throw Exception
        }

        return symbol.type;
    }

    @Override
    public Type visitCopyStmt(natoParser.CopyStmtContext ctx) {
        return super.visitCopyStmt(ctx);
    }

    //Not needed
    @Override
    public Type visitType(natoParser.TypeContext ctx) {
        return super.visitType(ctx);
    }

    @Override
    public Type visitMinusExpression(natoParser.MinusExpressionContext ctx) {
        Type exp = visit(ctx.expression());

        try {
            int i = Integer.parseInt(exp.toString());
        } catch (NumberFormatException nfe) {
            throw new NumberFormatException();
        }

        return super.visitMinusExpression(ctx);
    }

    @Override
    public Type visitConfirmExpression(natoParser.ConfirmExpressionContext ctx) {
        return new DataType(1);
    }

    @Override
    public Type visitIntExpression(natoParser.IntExpressionContext ctx) {
        try {
            int testy = Integer.parseInt(ctx.FALCON().getText());
        } catch (NumberFormatException e) {
            //TODO: Throw exception
            return null;
        }

        return new DataType(2);
    }

    @Override
    public Type visitMessageExpression(natoParser.MessageExpressionContext ctx) {
        //return super.visitMessageExpression(ctx);
        return new DataType(0);
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
        DataType x;
        DataType y;
        try {
            x = (DataType) visit(ctx.leftExpr);
            y = (DataType) visit(ctx.rightExpr);
        } catch (ClassCastException cce) {
            //TODO: Throw exception
            return null;
        }

        if ((x.getType() == 2) && (y.getType() == 2)) {
            return new DataType(1);
        } else {
            throw new RuntimeException();
        }
    }

    @Override
    public Type visitOrLogicalExpression(natoParser.OrLogicalExpressionContext ctx) {
        DataType x;
        DataType y;
        try {
            x = (DataType) visit(ctx.leftLogicalExpr);
            y = (DataType) visit(ctx.rightLogicalExpr);
        } catch (ClassCastException cce) {
            throw new ClassCastException();
        }

        if ((x.getType() == 1) && (y.getType() == 1)) {
            return new DataType(1);
        } else {
            throw new RuntimeException();
        }

    }

    @Override
    public Type visitNotLogicalExpression(natoParser.NotLogicalExpressionContext ctx) {
        DataType x;
        DataType y;
        try {
            x = (DataType) visit(ctx.leftLogicalExpr);
            y = (DataType) visit(ctx.rightLogicalExpr);
        } catch (ClassCastException cce) {
            throw new ClassCastException();
        }

        if ((x.getType() == 1) && (y.getType() == 1)) {
            return new DataType(1);
        } else {
            throw new RuntimeException();
        }
    }

    @Override
    public Type visitAndLogicalExpression(natoParser.AndLogicalExpressionContext ctx) {
        DataType x;
        DataType y;
        try {
            x = (DataType) visit(ctx.leftLogicalExpr);
            y = (DataType) visit(ctx.rightLogicalExpr);
        } catch (ClassCastException cce) {
            throw new ClassCastException();
        }

        if ((x.getType() == 1) && (y.getType() == 1)) {
            return new DataType(1);
        } else {
            throw new RuntimeException();
        }
    }
}

