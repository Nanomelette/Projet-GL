package fr.ensimag.deca.tools;

/**
 * Internal error of the compiler. Should never happen.
 *
 * @author gl20
 * @date 01/01/2022
 * @version $Id: $Id
 */
public class DecacInternalError extends RuntimeException {
    private static final long serialVersionUID = -7489681854632778463L;

    /**
     * <p>Constructor for DecacInternalError.</p>
     *
     * @param message a {@link java.lang.String} object
     * @param cause a {@link java.lang.Throwable} object
     */
    public DecacInternalError(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * <p>Constructor for DecacInternalError.</p>
     *
     * @param message a {@link java.lang.String} object
     */
    public DecacInternalError(String message) {
        super(message);
    }

}
