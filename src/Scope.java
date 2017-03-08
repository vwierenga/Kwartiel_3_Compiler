import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

/**
 * Created by Vincent on 3/2/2017.
 */
public class Scope {
    private static Scope ourInstance = new Scope();
    Scope parentScope;
    Map<String, Symbol> symbolTable;

    public static Scope getInstance() {
        return ourInstance;
    }

    private Scope() {
        symbolTable = new HashMap<>();
    }

    private Scope(Scope parentScope) {
        this.parentScope = parentScope;
        symbolTable = new HashMap<>();
    }

    public Scope openScope(){
        return ourInstance = new Scope(ourInstance);
    }

    public Scope closeScope(){
        return ourInstance = ourInstance.parentScope;
    }

    public Symbol lookupVariable(String name){
        Symbol variable = symbolTable.get(name);

        if ((variable == null || !(variable.type instanceof DataType)) && parentScope != null){
            variable = parentScope.lookupVariable(name);
        }
        if (variable != null && !(variable.type instanceof DataType)){
            variable = null;
        }

        return variable;
    }

    public Symbol lookupMethod(String name){
        Symbol method = symbolTable.get(name);

        if ((method == null || !(method.type instanceof MethodType)) && parentScope != null){
            method = parentScope.lookupMethod(name);
        }
        if (method != null && !(method.type instanceof DataType)){
            method = null;
        }

        return method;
    }

    public Symbol declareVariable(String name, DataType type) {
        Symbol symbol = new Symbol(name, type);
        if (this.lookupVariable(name) == null) {
            symbolTable.put(name, symbol);
        }
        return symbol;
    }

    public Symbol declareMethod(String name, DataType returnType, ArrayList<DataType> parameters){
        Symbol symbol = new Symbol(name, new MethodType(returnType, parameters));
        if (this.lookupMethod(name) == null) {
            symbolTable.put(name, symbol);
        }
        return symbol;
    }
}
