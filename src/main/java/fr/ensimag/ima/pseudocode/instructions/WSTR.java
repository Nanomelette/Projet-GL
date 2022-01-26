package fr.ensimag.ima.pseudocode.instructions;

import fr.ensimag.ima.pseudocode.ImmediateString;
import fr.ensimag.ima.pseudocode.UnaryInstruction;

/**
 * <p>WSTR class.</p>
 *
 * @author Ensimag
 * @date 01/01/2022
 * @version $Id: $Id
 */
public class WSTR extends UnaryInstruction {
    /**
     * <p>Constructor for WSTR.</p>
     *
     * @param op a {@link fr.ensimag.ima.pseudocode.ImmediateString} object
     */
    public WSTR(ImmediateString op) {
        super(op);
    }
    
    /**
     * <p>Constructor for WSTR.</p>
     *
     * @param message a {@link java.lang.String} object
     */
    public WSTR(String message) {
        super(new ImmediateString(message));
    }
    
}
