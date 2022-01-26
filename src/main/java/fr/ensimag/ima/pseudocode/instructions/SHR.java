package fr.ensimag.ima.pseudocode.instructions;

import fr.ensimag.ima.pseudocode.GPRegister;
import fr.ensimag.ima.pseudocode.UnaryInstructionToReg;

/**
 * <p>SHR class.</p>
 *
 * @author Ensimag
 * @date 01/01/2022
 * @version $Id: $Id
 */
public class SHR extends UnaryInstructionToReg {
    /**
     * <p>Constructor for SHR.</p>
     *
     * @param op1 a {@link fr.ensimag.ima.pseudocode.GPRegister} object
     */
    public SHR(GPRegister op1) {
        super(op1);
    }
}
