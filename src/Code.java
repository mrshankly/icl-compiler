import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Deque;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Map.Entry;

public class Code {
    private static final int INITIAL_CAPACITY = 100;
    private static final int SB_INITIAL_CAPACITY = 1000;

    private static Code instance = null;

    private long label;
    // Contains the code of the file with the main method.
    private StringBuilder mainCode;
    // Contains the code of all the auxiliary files.
    private Map<String, StringBuilder> auxCodes;
    // Stack of auxiliary files that are currently being compiled.
    private Deque<StringBuilder> openCodes;

    private Code() {
        mainCode = new StringBuilder(SB_INITIAL_CAPACITY);
        auxCodes = new HashMap<String, StringBuilder>(INITIAL_CAPACITY);
        openCodes = new LinkedList<StringBuilder>();
        label = 1;
    }

    // Helper methods.

    private static boolean isDirective(String instruction) {
        return instruction.charAt(0) == '.';
    }

    private static boolean isLabel(String instruction) {
        char first = instruction.charAt(0);
        char last = instruction.charAt(instruction.length() - 1);
        return (first == 'L') && (last == ':');
    }

    private StringBuilder getCurrentSB() {
        StringBuilder current = openCodes.peek();
        if (current == null) {
            current = mainCode;
        }
        return current;
    }

    // Public methods.

    public static Code getInstance() {
        if (instance == null) {
            instance = new Code();
        }
        return instance;
    }

    public String getNewLabel() {
        return "L" + label++;
    }

    public boolean startCode(String filename) {
        StringBuilder sb = new StringBuilder(SB_INITIAL_CAPACITY);
        boolean created = auxCodes.putIfAbsent(filename, sb) == null;

        if (created) {
            openCodes.push(sb);
        }
        return created;
    }

    public void endCode() {
        if (openCodes.size() > 0) {
            openCodes.pop();
        }
    }

    public void emit(String instruction) {
        if (instruction == null || instruction.length() == 0) {
            return;
        }
        if (!isDirective(instruction) && !isLabel(instruction)) {
            instruction = "\t" + instruction;
        }
        getCurrentSB().append(instruction + "\n");
    }

    public void emitIntro(String name) {
        // Create class with the given name.
        mainCode.append(".class public " + name + "\n");
        mainCode.append(".super java/lang/Object\n");

        // Constructor.
        mainCode.append(".method public <init>()V\n");
        mainCode.append("\taload_0\n");
        mainCode.append("\tinvokenonvirtual java/lang/Object/<init>()V\n");
        mainCode.append("\treturn\n");
        mainCode.append(".end method\n");

        // Start of main method.
        mainCode.append(".method public static main([Ljava/lang/String;)V\n");
        mainCode.append("\t; set limits used by this method\n");
        mainCode.append("\t.limit locals 10\n");
        mainCode.append("\t.limit stack 256\n");
        mainCode.append("\t; PrintStream object held in java.lang.out\n");
        mainCode.append("\tgetstatic java/lang/System/out Ljava/io/PrintStream;\n");

        mainCode.append("\t; ---- END OF INTRO CODE\n");
    }

    public void emitEpilogue() {
        mainCode.append("\t; ---- START OF EPILOGUE CODE\n");

        // Convert the value on top of the stack to a string and print it.
        mainCode.append("\t; convert to String;\n");
        mainCode.append("\tinvokestatic java/lang/String/valueOf(I)Ljava/lang/String;\n");
        mainCode.append("\t; call println\n");
        mainCode.append("\tinvokevirtual java/io/PrintStream/println(Ljava/lang/String;)V\n");

        // End of the main method.
        mainCode.append("\treturn\n");
        mainCode.append(".end method\n");
    }

    public void dump(String filename) throws IOException {
        // Write main file.
        BufferedWriter writer = new BufferedWriter(new FileWriter(filename));
        writer.append(mainCode);
        writer.close();

        // Write remaining files.
        for (Map.Entry<String, StringBuilder> entry : auxCodes.entrySet()) {
            writer = new BufferedWriter(new FileWriter(entry.getKey()));
            writer.append(entry.getValue());
            writer.close();
        }
    }
}
