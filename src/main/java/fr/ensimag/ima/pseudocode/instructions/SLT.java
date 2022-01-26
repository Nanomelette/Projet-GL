package fr.ensimag.ima.pseudocode.instructions;

import fr.ensimag.ima.pseudocode.GPRegister;
import fr.ensimag.ima.pseudocode.UnaryInstructionToReg;

/**
 * <p>SLT class.</p>
 *
 * @author Ensimag
 * @date 01/01/2022
 * @version $Id: $Id
 */
public class SLT extends UnaryInstructionToReg {

    /**
     * <p>Constructor for SLT.</p>
     *
     * @param op a {@link fr.ensimag.ima.pseudocode.GPRegister} object
     */
    public SLT(GPRegister op) {
        super(op);
    }

}
