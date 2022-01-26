package fr.ensimag.ima.pseudocode;

/**
 * Internal error related to IMA code. Should never happen.
 *
 * @author Ensimag
 * @date 01/01/2022
 * @version $Id: $Id
 */
public class IMAInternalError extends RuntimeException {
    /**
     * <p>Constructor for IMAInternalError.</p>
     *
     * @param message a {@link java.lang.String} object
     * @param cause a {@link java.lang.Throwable} object
     */
    public IMAInternalError(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * <p>Constructor for IMAInternalError.</p>
     *
     * @param message a {@link java.lang.String} object
     */
    public IMAInternalError(String message) {
        super(message);
    }

    private static final long serialVersionUID = 3929345355905773360L;

}
