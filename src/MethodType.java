import java.util.ArrayList;

/**
 * Created by Vincent on 3/2/2017.
 */
public class MethodType extends Type {
    DataType returnType;
    ArrayList<DataType> parameters = new ArrayList<>();

    public void addParameter(DataType dataType){
        parameters.add(dataType);
    }

    public MethodType(DataType returnType, ArrayList<DataType> parameters) {
        this.returnType = returnType;
        this.parameters = parameters;
    }
}
