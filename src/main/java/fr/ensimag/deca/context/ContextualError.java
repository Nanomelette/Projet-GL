package fr.ensimag.deca.context;

import fr.ensimag.deca.tree.Location;
import fr.ensimag.deca.tree.LocationException;

/**
 * Exception raised when a contextual error is found.
 *
 * @author gl20
 * @date 01/01/2022
 * @version $Id: $Id
 */
public class ContextualError extends LocationException {
    private static final long serialVersionUID = -8122514996569278575L;

    /**
     * <p>Constructor for ContextualError.</p>
     *
     * @param message a {@link java.lang.String} object
     * @param location a {@link fr.ensimag.deca.tree.Location} object
     */
    public ContextualError(String message, Location location) {
        super(message, location);
    }
}
