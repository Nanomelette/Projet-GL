package fr.ensimag.deca;

/**
 * Exception raised when the command-line options are incorrect.
 *
 * @author gl20
 * @date 01/01/2022
 * @version $Id: $Id
 */
public class CLIException extends Exception {
    private static final long serialVersionUID = 6144682285316920966L;

    /**
     * <p>Constructor for CLIException.</p>
     *
     * @param message a {@link java.lang.String} object
     */
    public CLIException(final String message) {
        super(message);
    }
}
