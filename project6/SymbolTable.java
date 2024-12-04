
import java.util.HashMap;
import java.util.Map;

public class SymbolTable {
    private Map<String,Integer> table;

    //initializer creates an empty symbol table 
    public SymbolTable(){
        table = new HashMap<>();

        //we add predefined symbols 
        
        
    }

    public void  addEntry(String symbol, int address){
        table.put(symbol, address);
    }
}
