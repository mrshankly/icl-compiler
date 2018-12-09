import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class Code {
    private static final int INITIAL_CAPACITY = 1000;

    private static Code mainInstance = null;

    private StringBuilder code;
    private int label;

    public Code() {
        code = new StringBuilder(INITIAL_CAPACITY);
        label = 1;
    }

    public static Code getMain() {
        if (mainInstance == null) {
            mainInstance = new Code();
        }
        return mainInstance;
    }

    public String getNewLabel() {
        return "L" + label++;
    }

    public void emit(String instruction) {
        if (instruction == null || instruction.length() == 0) {
            return;
        }
        if (!isDirective(instruction) && !isLabel(instruction)) {
            instruction = "\t" + instruction;
        }
        code.append(instruction + "\n");
    }

    public void emitIntro(String name) {
        // Create class with the given name.
        code.append(".class public " + name + "\n");
        code.append(".super java/lang/Object\n");

        // Constructor.
        code.append(".method public <init>()V\n");
        code.append("\taload_0\n");
        code.append("\tinvokenonvirtual java/lang/Object/<init>()V\n");
        code.append("\treturn\n");
        code.append(".end method\n");

        // Start of main method.
        code.append(".method public static main([Ljava/lang/String;)V\n");
        code.append("\t; set limits used by this method\n");
        code.append("\t.limit locals 10\n");
        code.append("\t.limit stack 256\n");
        code.append("\t; PrintStream object held in java.lang.out\n");
        code.append("\tgetstatic java/lang/System/out Ljava/io/PrintStream;\n");

        code.append("\t; ---- END OF INTRO CODE\n");
    }

    public void emitEpilogue() {
        code.append("\t; ---- START OF EPILOGUE CODE\n");

        // Convert the value on top of the stack to a string and print it.
        code.append("\t; convert to String;\n");
        code.append("\tinvokestatic java/lang/String/valueOf(I)Ljava/lang/String;\n");
        code.append("\t; call println\n");
        code.append("\tinvokevirtual java/io/PrintStream/println(Ljava/lang/String;)V\n");

        // End of the main method.
        code.append("\treturn\n");
        code.append(".end method\n");
    }

    public void dump(String filename) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(filename));
        writer.append(code);
        writer.close();
    }

    // Helper functions.
    private static boolean isDirective(String instruction) {
        return instruction.charAt(0) == '.';
    }

    private static boolean isLabel(String instruction) {
        char first = instruction.charAt(0);
        char last = instruction.charAt(instruction.length() - 1);
        return (first == 'L') && (last == ':');
    }
}
