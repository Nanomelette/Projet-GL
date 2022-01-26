package fr.ensimag.ima.pseudocode.instructions;

import fr.ensimag.ima.pseudocode.GPRegister;
import fr.ensimag.ima.pseudocode.UnaryInstructionToReg;

/**
 * <p>SEQ class.</p>
 *
 * @author Ensimag
 * @date 01/01/2022
 * @version $Id: $Id
 */
public class SEQ extends UnaryInstructionToReg {

    /**
     * <p>Constructor for SEQ.</p>
     *
     * @param op a {@link fr.ensimag.ima.pseudocode.GPRegister} object
     */
    public SEQ(GPRegister op) {
        super(op);
    }

}
