package fr.ensimag.ima.pseudocode;

import java.io.PrintStream;

/**
 * Line of code in an IMA program.
 *
 * @author Ensimag
 * @date 01/01/2022
 * @version $Id: $Id
 */
public class Line extends AbstractLine {
    /**
     * <p>Constructor for Line.</p>
     *
     * @param label a {@link fr.ensimag.ima.pseudocode.Label} object
     * @param instruction a {@link fr.ensimag.ima.pseudocode.Instruction} object
     * @param comment a {@link java.lang.String} object
     */
    public Line(Label label, Instruction instruction, String comment) {
        super();
        checkComment(comment);
        this.label = label;
        this.instruction = instruction;
        this.comment = comment;
    }

    /**
     * <p>Constructor for Line.</p>
     *
     * @param instruction a {@link fr.ensimag.ima.pseudocode.Instruction} object
     */
    public Line(Instruction instruction) {
        super();
        this.instruction = instruction;
    }

    /**
     * <p>Constructor for Line.</p>
     *
     * @param comment a {@link java.lang.String} object
     */
    public Line(String comment) {
        super();
        checkComment(comment);
        this.comment = comment;
    }

    /**
     * <p>Constructor for Line.</p>
     *
     * @param label a {@link fr.ensimag.ima.pseudocode.Label} object
     */
    public Line(Label label) {
        super();
        this.label = label;
    }

    private void checkComment(final String s) {
        if (s == null) {
            return;
        }
        if (s.contains("\n")) {
            throw new IMAInternalError("Comment '" + s + "'contains newline character");
        }
        if (s.contains("\r")) {
            throw new IMAInternalError("Comment '" + s + "'contains carriage return character");
        }
    }
    private Instruction instruction;
    private String comment;
    private Label label;

    @Override
    void display(PrintStream s) {
        boolean tab = false;
        if (label != null) {
            s.print(label);
                        s.print(":");
            tab = true;
        }
        if (instruction != null) {
            s.print("\t");
            instruction.display(s);
            tab = true;
        }
        if (comment != null) {
            if (tab) {
                            s.print("\t");
                        }
            s.print("; " + comment);
        }
        s.println();
    }

    /**
     * <p>Setter for the field <code>instruction</code>.</p>
     *
     * @param instruction a {@link fr.ensimag.ima.pseudocode.Instruction} object
     */
    public void setInstruction(Instruction instruction) {
        this.instruction = instruction;
    }

    /**
     * <p>Getter for the field <code>instruction</code>.</p>
     *
     * @return a {@link fr.ensimag.ima.pseudocode.Instruction} object
     */
    public Instruction getInstruction() {
        return instruction;
    }

    /**
     * <p>Setter for the field <code>comment</code>.</p>
     *
     * @param comment a {@link java.lang.String} object
     */
    public void setComment(String comment) {
        this.comment = comment;
    }

    /**
     * <p>Getter for the field <code>comment</code>.</p>
     *
     * @return a {@link java.lang.String} object
     */
    public String getComment() {
        return comment;
    }

    /**
     * <p>Setter for the field <code>label</code>.</p>
     *
     * @param label a {@link fr.ensimag.ima.pseudocode.Label} object
     */
    public void setLabel(Label label) {
        this.label = label;
    }

    /**
     * <p>Getter for the field <code>label</code>.</p>
     *
     * @return a {@link fr.ensimag.ima.pseudocode.Label} object
     */
    public Label getLabel() {
        return label;
    }
}
