package fr.ensimag.ima.pseudocode;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.LinkedList;

/**
 * Abstract representation of an IMA program, i.e. set of Lines.
 *
 * @author Ensimag
 * @date 01/01/2022
 * @version $Id: $Id
 */
public class IMAProgram {
    private final LinkedList<AbstractLine> lines = new LinkedList<AbstractLine>();

    /**
     * <p>add.</p>
     *
     * @param line a {@link fr.ensimag.ima.pseudocode.AbstractLine} object
     */
    public void add(AbstractLine line) {
        lines.add(line);
    }

    /**
     * <p>addComment.</p>
     *
     * @param s a {@link java.lang.String} object
     */
    public void addComment(String s) {
        lines.add(new Line(s));
    }

    /**
     * <p>addLabel.</p>
     *
     * @param l a {@link fr.ensimag.ima.pseudocode.Label} object
     */
    public void addLabel(Label l) {
        lines.add(new Line(l));
    }

    /**
     * <p>addLabelAtFirst.</p>
     *
     * @param l a {@link fr.ensimag.ima.pseudocode.Label} object
     */
    public void addLabelAtFirst(Label l) {
        lines.addFirst(new Line(l));
    }

    /**
     * <p>addInstruction.</p>
     *
     * @param i a {@link fr.ensimag.ima.pseudocode.Instruction} object
     */
    public void addInstruction(Instruction i) {
        lines.add(new Line(i));
    }

    /**
     * <p>addInstruction.</p>
     *
     * @param i a {@link fr.ensimag.ima.pseudocode.Instruction} object
     * @param s a {@link java.lang.String} object
     */
    public void addInstruction(Instruction i, String s) {
        lines.add(new Line(null, i, s));
    }

    /**
     * Append the content of program p to the current program. The new program
     * and p may or may not share content with this program, so p should not be
     * used anymore after calling this function.
     *
     * @param p a {@link fr.ensimag.ima.pseudocode.IMAProgram} object
     */
    public void append(IMAProgram p) {
        lines.addAll(p.lines);
    }
    
    /**
     * Add a line at the front of the program.
     *
     * @param l a {@link fr.ensimag.ima.pseudocode.Line} object
     */
    public void addFirst(Line l) {
        lines.addFirst(l);
    }

    /**
     * Display the program in a textual form readable by IMA to stream s.
     *
     * @param s a {@link java.io.PrintStream} object
     */
    public void display(PrintStream s) {
        for (AbstractLine l: lines) {
            l.display(s);
        }
    }

    /**
     * Return the program in a textual form readable by IMA as a String.
     *
     * @return a {@link java.lang.String} object
     */
    public String display() {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        PrintStream s = new PrintStream(out);
        display(s);
        return out.toString();
    }

    /**
     * <p>addFirst.</p>
     *
     * @param i a {@link fr.ensimag.ima.pseudocode.Instruction} object
     */
    public void addFirst(Instruction i) {
        addFirst(new Line(i));
    }
    
    /**
     * <p>addFirst.</p>
     *
     * @param i a {@link fr.ensimag.ima.pseudocode.Instruction} object
     * @param comment a {@link java.lang.String} object
     */
    public void addFirst(Instruction i, String comment) {
        addFirst(new Line(null, i, comment));
    }
}
