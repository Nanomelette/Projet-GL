package fr.ensimag.ima.pseudocode.instructions;

import fr.ensimag.ima.pseudocode.DAddr;
import fr.ensimag.ima.pseudocode.UnaryInstruction;

/**
 * <p>PEA class.</p>
 *
 * @author Ensimag
 * @date 01/01/2022
 * @version $Id: $Id
 */
public class PEA extends UnaryInstruction {

    /**
     * <p>Constructor for PEA.</p>
     *
     * @param operand a {@link fr.ensimag.ima.pseudocode.DAddr} object
     */
    public PEA(DAddr operand) {
        super(operand);
    }

}
