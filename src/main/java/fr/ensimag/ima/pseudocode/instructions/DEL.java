package fr.ensimag.ima.pseudocode.instructions;

import fr.ensimag.ima.pseudocode.GPRegister;
import fr.ensimag.ima.pseudocode.UnaryInstructionToReg;

/**
 * <p>DEL class.</p>
 *
 * @author Ensimag
 * @date 01/01/2022
 * @version $Id: $Id
 */
public class DEL extends UnaryInstructionToReg {

    /**
     * <p>Constructor for DEL.</p>
     *
     * @param op a {@link fr.ensimag.ima.pseudocode.GPRegister} object
     */
    public DEL(GPRegister op) {
        super(op);
    }

}
