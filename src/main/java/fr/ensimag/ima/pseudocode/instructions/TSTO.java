package fr.ensimag.ima.pseudocode.instructions;

import fr.ensimag.ima.pseudocode.ImmediateInteger;
import fr.ensimag.ima.pseudocode.UnaryInstructionImmInt;

/**
 * <p>TSTO class.</p>
 *
 * @author Ensimag
 * @date 01/01/2022
 * @version $Id: $Id
 */
public class TSTO extends UnaryInstructionImmInt {
    /**
     * <p>Constructor for TSTO.</p>
     *
     * @param i a {@link fr.ensimag.ima.pseudocode.ImmediateInteger} object
     */
    public TSTO(ImmediateInteger i) {
        super(i);
    }

    /**
     * <p>Constructor for TSTO.</p>
     *
     * @param i a int
     */
    public TSTO(int i) {
        super(i);
    }
}
