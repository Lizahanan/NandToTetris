
import java.util.HashMap;
import java.io.*;

public class CodeWriter{
    private BufferedWriter writer; //to write the Hack Assembly code
    private int labelCounter; // To generate unique labels for branching

    //constructor 
    public CodeWriter(String fileName) throws IOException {
        writer = new BufferedWriter(new FileWriter(fileName));
        labelCounter = 0;
    }

    // writeArithmetic: Translates an arithmetic and logic command into Hack assembly
    public void writeArithmetic(String command) throws IOException{
        switch (command){
            case "add":
                writeBinaryOp("+");
                break;
            case "sub":
                writeBinaryOp("-");
                break;
            case "neg":     
                writeUnaryOp("-");
                break;           
            case "eq":
                writeComparison("JEQ");
                break;           
            case "gt":
                writeComparison("JGT");
                break; 
            case "lt":
                writeComparison("JLT");
                break;
            case "and":
                writeComparison("&");
                break;
            case "or":
                writeComparison("|");
                break;
            case "not":
                writeComparison("!");
                break;
            default:
                throw new IllegalArgumentException("Invalid arithmetic command: " + command);
        }
    }

    // writePushPop: Translates push or pop commands into Hack assembly
    public void writePushPop(String commandType , String segment , int index) throws IOException{
        switch(commandType){
            case "C_PUSH":
                if (segment.equals("constant")) {
                    writer.write("@" + index + "\n"); // Load the constant
                    writer.write("D=A\n");          // D = constant
                } else {
                    loadSegmentAddress(segment, index);
                    writer.write("D=M\n");          // D = *segment[index]
                }
                pushDToStack();
                break;
        
            case "C_POP":
                loadSegmentAddress(segment, index);
                writer.write("D=A\n");              // D = address
                writer.write("@R13\n");             // Use R13 as a temporary register
                writer.write("M=D\n");              // R13 = address
                popStackToD();
                writer.write("@R13\n");
                writer.write("A=M\n");              // A = address
                writer.write("M=D\n");              // *address = D
                break;

            default:
                throw new IllegalArgumentException("Invalid command type: " + commandType);
        }
    }

    public void close() throws IOException{
        writer.close();
    }

    //helper methods 
    // writeBinaryOp: Writes the assembly code for binary operations
    private void writeBinaryOp(String operation) throws IOException {
       
    }
    // Writes a unary operation (-, !)
    private void writeUnaryOp(String operation) throws IOException {
        
    }

    // Writes a comparison (eq, gt, lt)
    private void writeComparison(String jumpCommand) throws IOException {
        
    }

    // Pushes the value in D onto the stack
    private void pushDToStack() throws IOException {
        
    }

    // Pops the top value of the stack into D
    private void popStackToD() throws IOException {
        
    }

    // Loads the address of a segment[index] into A
    private void loadSegmentAddress(String segment, int index) throws IOException {
        
    }

}