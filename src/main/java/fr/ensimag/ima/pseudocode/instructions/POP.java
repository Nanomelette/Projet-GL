package fr.ensimag.ima.pseudocode.instructions;

import fr.ensimag.ima.pseudocode.GPRegister;
import fr.ensimag.ima.pseudocode.UnaryInstructionToReg;

/**
 * <p>POP class.</p>
 *
 * @author Ensimag
 * @date 01/01/2022
 * @version $Id: $Id
 */
public class POP extends UnaryInstructionToReg {

    /**
     * <p>Constructor for POP.</p>
     *
     * @param op a {@link fr.ensimag.ima.pseudocode.GPRegister} object
     */
    public POP(GPRegister op) {
        super(op);
    }

}
