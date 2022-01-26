package fr.ensimag.ima.pseudocode.instructions;

import fr.ensimag.ima.pseudocode.GPRegister;
import fr.ensimag.ima.pseudocode.UnaryInstructionToReg;

/**
 * <p>SHL class.</p>
 *
 * @author Ensimag
 * @date 01/01/2022
 * @version $Id: $Id
 */
public class SHL extends UnaryInstructionToReg {
    /**
     * <p>Constructor for SHL.</p>
     *
     * @param op1 a {@link fr.ensimag.ima.pseudocode.GPRegister} object
     */
    public SHL(GPRegister op1) {
        super(op1);
    }
}
