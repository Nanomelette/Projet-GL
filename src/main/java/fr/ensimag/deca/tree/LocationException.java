package fr.ensimag.deca.tree;

import java.io.PrintStream;

/**
 * Exception corresponding to an error at a particular location in a file.
 *
 * @author gl20
 * @date 01/01/2022
 * @version $Id: $Id
 */
public class LocationException extends Exception {
    /**
     * <p>Getter for the field <code>location</code>.</p>
     *
     * @return a {@link fr.ensimag.deca.tree.Location} object
     */
    public Location getLocation() {
        return location;
    }

    /**
     * <p>display.</p>
     *
     * @param s a {@link java.io.PrintStream} object
     */
    public void display(PrintStream s) {
        Location loc = getLocation();
        String line;
        String column;
        if (loc == null) {
            line = "<unknown>";
            column = "";
        } else {
            line = Integer.toString(loc.getLine());
            column = ":" + loc.getPositionInLine();
        }
        s.println(location.getFilename() + ":" + line + column + ": " + getMessage());
    }

    private static final long serialVersionUID = 7628400022855935597L;
    protected Location location;

    /**
     * <p>Constructor for LocationException.</p>
     *
     * @param message a {@link java.lang.String} object
     * @param location a {@link fr.ensimag.deca.tree.Location} object
     */
    public LocationException(String message, Location location) {
        super(message);
        assert(location == null || location.getFilename() != null);
        this.location = location;
    }

}
