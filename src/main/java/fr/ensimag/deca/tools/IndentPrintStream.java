package fr.ensimag.deca.tools;

import java.io.PrintStream;

/**
 * <p>IndentPrintStream class.</p>
 *
 * @author gl20
 * @date 01/01/2022
 * @version $Id: $Id
 */
public class IndentPrintStream {
    private PrintStream stream;
    /**
     * <p>Constructor for IndentPrintStream.</p>
     *
     * @param stream a {@link java.io.PrintStream} object
     */
    public IndentPrintStream(PrintStream stream) {
        this.stream = stream;
    }
    private int indent = 0;
    private boolean indented = false;

    private void printIndent() {
        if (indented) {
            return;
        }
        for (int i = 0; i < indent; i++) {
            stream.print("\t");
        }
        indented = true;
    }
    /**
     * <p>print.</p>
     *
     * @param s a {@link java.lang.String} object
     */
    public void print(String s) {
        printIndent();
        stream.print(s);
    }

    /**
     * <p>println.</p>
     */
    public void println() {
        stream.println();
        indented = false;
    }

    /**
     * <p>println.</p>
     *
     * @param s a {@link java.lang.String} object
     */
    public void println(String s) {
        print(s);
        println();
    }

    /**
     * <p>indent.</p>
     */
    public void indent() {
        indent++;
    }

    /**
     * <p>unindent.</p>
     */
    public void unindent() {
        indent--;
    }

    /**
     * <p>print.</p>
     *
     * @param charAt a char
     */
    public void print(char charAt) {
        printIndent();
        stream.print(charAt);
    }
}
