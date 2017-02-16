import org.antlr.v4.runtime.ANTLRFileStream;
import org.antlr.v4.runtime.CommonTokenStream;

import java.io.IOException;

/**
 * Created by Laptop-oud on 16-2-2017.
 */

public class Compiler {
    public static void main (String[] args) {
        try {
            natoLexer lexer = new natoLexer(new ANTLRFileStream("natoSrc/program.nato"));
            CommonTokenStream tokens = new CommonTokenStream(lexer);
            natoParser parser = new natoParser(tokens);
            natoParser.ProgramContext programtree = parser.program();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}


