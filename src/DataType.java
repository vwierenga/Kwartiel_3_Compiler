/**
 * Created by Vincent on 3/2/2017.
 */
public class DataType extends Type {
    public enum type {
        MESSAGE, CONFIRM, FALCON
    }

    int type;

    public DataType(int type) {
        this.type = type;
    }

    public int getType() {
        return type;
    }
}
