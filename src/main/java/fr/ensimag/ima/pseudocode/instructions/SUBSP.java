package fr.ensimag.ima.pseudocode.instructions;

import fr.ensimag.ima.pseudocode.ImmediateInteger;
import fr.ensimag.ima.pseudocode.UnaryInstructionImmInt;

/**
 * <p>SUBSP class.</p>
 *
 * @author Ensimag
 * @date 01/01/2022
 * @version $Id: $Id
 */
public class SUBSP extends UnaryInstructionImmInt {

    /**
     * <p>Constructor for SUBSP.</p>
     *
     * @param operand a {@link fr.ensimag.ima.pseudocode.ImmediateInteger} object
     */
    public SUBSP(ImmediateInteger operand) {
        super(operand);
    }

    /**
     * <p>Constructor for SUBSP.</p>
     *
     * @param i a int
     */
    public SUBSP(int i) {
        super(i);
    }

}
