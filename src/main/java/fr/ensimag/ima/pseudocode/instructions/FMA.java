package fr.ensimag.ima.pseudocode.instructions;

import fr.ensimag.ima.pseudocode.BinaryInstructionDValToReg;
import fr.ensimag.ima.pseudocode.DVal;
import fr.ensimag.ima.pseudocode.GPRegister;

/**
 * <p>FMA class.</p>
 *
 * @author Ensimag
 * @date 01/01/2022
 * @version $Id: $Id
 */
public class FMA extends BinaryInstructionDValToReg {
    /**
     * <p>Constructor for FMA.</p>
     *
     * @param op1 a {@link fr.ensimag.ima.pseudocode.DVal} object
     * @param op2 a {@link fr.ensimag.ima.pseudocode.GPRegister} object
     */
    public FMA(DVal op1, GPRegister op2) {
        super(op1, op2);
    }
}
