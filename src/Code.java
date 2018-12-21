import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Deque;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
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

    public static Code getInstance() {
        if (instance == null) {
            instance = new Code();
        }
        return instance;
    }

    // Helper methods.

    private static boolean isDirective(String instruction) {
        return instruction.trim().charAt(0) == '.';
    }

    private static boolean isLabel(String instruction) {
        instruction = instruction.trim();
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
        mainCode.append("\t; initialize SL variable to null\n");
        mainCode.append("\taconst_null\n");
        mainCode.append("\tastore_3\n");
        mainCode.append("\t; ---- END OF INTRO CODE\n");
    }

    public void emitEpilogue() {
        mainCode.append("\t; ---- START OF EPILOGUE CODE\n");
        // End of the main method.
        mainCode.append("\treturn\n");
        mainCode.append(".end method\n");
    }

    public List<String> dump(String filename) throws IOException {
        List<String> outputFiles = new ArrayList<String>(auxCodes.size() + 1);

        // Write main file.
        BufferedWriter writer = new BufferedWriter(new FileWriter(filename));
        writer.append(mainCode);
        writer.close();

        outputFiles.add(filename);
        System.out.println("[compiler] Generated: " + filename);

        // Write remaining files.
        for (Map.Entry<String, StringBuilder> entry : auxCodes.entrySet()) {
            filename = entry.getKey();

            writer = new BufferedWriter(new FileWriter(filename));
            writer.append(entry.getValue());
            writer.close();

            outputFiles.add(filename);
            System.out.println("[compiler] Generated: " + filename);
        }

        return outputFiles;
    }
}
