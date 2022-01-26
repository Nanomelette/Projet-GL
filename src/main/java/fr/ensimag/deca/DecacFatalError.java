package fr.ensimag.deca;

/**
 * Exception raised when something prevents the compilation (e.g.
 * source file unreadable).
 *
 * @author gl20
 * @date 01/01/2022
 * @version $Id: $Id
 */
public class DecacFatalError extends Exception {
    private static final long serialVersionUID = 6141682385316820966L;

    /**
     * <p>Constructor for DecacFatalError.</p>
     *
     * @param message a {@link java.lang.String} object
     */
    public DecacFatalError(String message) {
        super(message);
    }
}
