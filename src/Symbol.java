/**
 * Created by Vincent on 3/2/2017.
 */
public class Symbol {
    String name;
    Type type;

    public Symbol(String name, Type type) {
        this.name = name;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public Type getType() {
        return type;
    }
}
