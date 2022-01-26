package fr.ensimag.ima.pseudocode.instructions;

import fr.ensimag.ima.pseudocode.BinaryInstruction;
import fr.ensimag.ima.pseudocode.DAddr;
import fr.ensimag.ima.pseudocode.Register;

/**
 * <p>STORE class.</p>
 *
 * @author Ensimag
 * @date 01/01/2022
 * @version $Id: $Id
 */
public class STORE extends BinaryInstruction {
    /**
     * <p>Constructor for STORE.</p>
     *
     * @param op1 a {@link fr.ensimag.ima.pseudocode.Register} object
     * @param op2 a {@link fr.ensimag.ima.pseudocode.DAddr} object
     */
    public STORE(Register op1, DAddr op2) {
        super(op1, op2);
    }
}
