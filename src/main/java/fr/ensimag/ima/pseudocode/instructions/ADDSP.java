package fr.ensimag.ima.pseudocode.instructions;

import fr.ensimag.ima.pseudocode.ImmediateInteger;
import fr.ensimag.ima.pseudocode.UnaryInstructionImmInt;

/**
 * Add a value to stack pointer.
 *
 * @author Ensimag
 * @date 01/01/2022
 * @version $Id: $Id
 */
public class ADDSP extends UnaryInstructionImmInt {

    /**
     * <p>Constructor for ADDSP.</p>
     *
     * @param operand a {@link fr.ensimag.ima.pseudocode.ImmediateInteger} object
     */
    public ADDSP(ImmediateInteger operand) {
        super(operand);
    }

    /**
     * <p>Constructor for ADDSP.</p>
     *
     * @param i a int
     */
    public ADDSP(int i) {
        super(i);
    }

}
