package fr.ensimag.ima.pseudocode.instructions;

import fr.ensimag.ima.pseudocode.GPRegister;
import fr.ensimag.ima.pseudocode.UnaryInstructionToReg;

/**
 * <p>SGT class.</p>
 *
 * @author Ensimag
 * @date 01/01/2022
 * @version $Id: $Id
 */
public class SGT extends UnaryInstructionToReg {

    /**
     * <p>Constructor for SGT.</p>
     *
     * @param op a {@link fr.ensimag.ima.pseudocode.GPRegister} object
     */
    public SGT(GPRegister op) {
        super(op);
    }

}
