package fr.ensimag.deca.tree;

import java.io.Serializable;

/**
 * Location in a file (File, line, positionInLine).
 *
 * @author gl20
 * @date 01/01/2022
 * @version $Id: $Id
 */
public class Location implements Serializable {
    /*
     * Location implements Serializable because it appears as a field
     * of LocationException, which is serializable.
     */
    private static final long serialVersionUID = -2906437663480660298L;

    /** Constant <code>NO_SOURCE_NAME="&lt;no source file&gt;"</code> */
    public static final String NO_SOURCE_NAME = "<no source file>";
    /** Constant <code>BUILTIN</code> */
    public static final Location BUILTIN = new Location(-1, -1, NO_SOURCE_NAME);

    /**
     * {@inheritDoc}
     *
     * Display the (line, positionInLine) as a String. The file is not
     * displayed.
     */
    @Override
    public String toString() {
        if (this == BUILTIN) {
            return "[builtin]";
        } else {
            return "[" + line + ", " + positionInLine + "]";
        }
    }

    /**
     * <p>Getter for the field <code>line</code>.</p>
     *
     * @return a int
     */
    public int getLine() {
        return line;
    }

    /**
     * <p>Getter for the field <code>positionInLine</code>.</p>
     *
     * @return a int
     */
    public int getPositionInLine() {
        return positionInLine;
    }

    /**
     * <p>Getter for the field <code>filename</code>.</p>
     *
     * @return a {@link java.lang.String} object
     */
    public String getFilename() {
        if (filename != null) {
            return filename;
        } else {
            // we're probably reading from stdin
            return NO_SOURCE_NAME;
        }
    }

    private final int line;
    private final int positionInLine;
    private final String filename;

    /**
     * <p>Constructor for Location.</p>
     *
     * @param line a int
     * @param positionInLine a int
     * @param filename a {@link java.lang.String} object
     */
    public Location(int line, int positionInLine, String filename) {
        super();
        this.line = line;
        this.positionInLine = positionInLine;
        this.filename = filename;
    }

}
