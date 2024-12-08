
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
    // writeBinaryOp: Writes the assembly code for binary operations (+, -, &, |)
    private void writeBinaryOp(String operation) throws IOException {
        popStackToD();
        writer.write("A=A-1\n"); // Decrement the stack pointer
        writer.write("M=M" + operation + "D\n"); // M = M operation D
    }
    // Writes a unary operation (-, !)
    private void writeUnaryOp(String operation) throws IOException {
        writer.write("@SP\n");
        writer.write("A=M-1\n"); //point to the top of the stack
        writer.write("M=" + operation + "M\n"); // M = operation M
    }

    // Writes a comparison (eq, gt, lt)
    private void writeComparison(String jumpCommand) throws IOException {
        popStackToD();
        writer.write("A=A-1\n"); // Decrement the stack pointer
        writer.write("D=M-D\n"); // D = M - D
        writer.write("@TRUE" + labelCounter + "\n");
        writer.write("D;" + jumpCommand + "\n"); // Jump if condition is true
        writer.write("@SP\n");
        writer.write("A=M-1\n"); // Point to the top of the stack
        writer.write("M=0\n"); // M = false
        writer.write("@CONTINUE" + labelCounter + "\n");
        writer.write("0;JMP\n");
        writer.write("(TRUE" + labelCounter + ")\n");
        writer.write("@SP\n");
        writer.write("A=M-1\n"); // Point to the top of the stack
        writer.write("M=-1\n"); // M = true
        writer.write("(CONTINUE" + labelCounter + ")\n");
        labelCounter++;
    }

    // Pushes the value in D onto the stack
    private void pushDToStack() throws IOException {
        writer.write("@SP\n");
        writer.write("A=M\n"); // Point to the stack pointer
        writer.write("M=D\n"); // *SP = D
        writer.write("@SP\n");
        writer.write("M=M+1\n"); // Increment the stack pointer
        
    }

    // Pops the top value of the stack into D
    private void popStackToD() throws IOException {
        writer.write("@SP\n");
        writer.write("AM=M-1\n"); // Decrement the stack pointer and point to the top
        writer.write("D=M\n"); // D = *SP
        
    }

    // Loads the address of a segment[index] into A
    private void loadSegmentAddress(String segment) throws IOException {
        switch (segment) {
            case "local":
                writer.write("@LCL\n");
                break;
            case "argument":
                writer.write("@ARG\n");
                break;
            case "this":
                writer.write("@THIS\n");
                break;
            case "that":
                writer.write("@THAT\n");
                break;
            case "pointer":
                writer.write("@3\n");
                break;
            case "temp":
                writer.write("@5\n");
                break;
            case "static":
                writer.write("@16\n");
                break;
            default:
                throw new IllegalArgumentException("Invalid segment: " + segment);
        }

        
    }

}