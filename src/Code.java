import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class Code {
    private static final int INITIAL_CAPACITY = 1000;
    private static final int COMMENT_COLUMN = 81;

    private static Code mainInstance = null;

    private StringBuilder code;

    public Code() {
        code = new StringBuilder(INITIAL_CAPACITY);
    }

    public static Code getMain() {
        if (mainInstance == null) {
            mainInstance = new Code();
        }
        return mainInstance;
    }

    public void emit(String instruction) {
        emit(instruction, null);
    }

    public void emit(String instruction, String comment) {
        // Align comment if any is given.
        if (comment != null) {
            int whitespaceLength = COMMENT_COLUMN - 1 - instruction.length();
            if (whitespaceLength < 1) {
                whitespaceLength = 1;
            }
            StringBuilder whitespace = new StringBuilder(whitespaceLength);
            for (int i = 0; i < whitespaceLength; i++) {
                whitespace.append(' ');
            }
            instruction = String.format("%s%s; %s", instruction, whitespace, comment);
        }
        // Add a tab if instruction is not a directive.
        if (instruction.length() > 0 && instruction.charAt(0) != '.') {
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
}
