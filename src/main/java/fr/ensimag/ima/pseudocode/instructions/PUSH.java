package fr.ensimag.ima.pseudocode.instructions;

import fr.ensimag.ima.pseudocode.Register;
import fr.ensimag.ima.pseudocode.UnaryInstruction;

/**
 * <p>PUSH class.</p>
 *
 * @author Ensimag
 * @date 01/01/2022
 * @version $Id: $Id
 */
public class PUSH extends UnaryInstruction {
    /**
     * <p>Constructor for PUSH.</p>
     *
     * @param op1 a {@link fr.ensimag.ima.pseudocode.Register} object
     */
    public PUSH(Register op1) {
        super(op1);
    }
}
