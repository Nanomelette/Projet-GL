package fr.ensimag.ima.pseudocode;

/**
 * Base class for instructions with 2 operands, the first being a
 * DVal, and the second a Register.
 *
 * @author Ensimag
 * @date 01/01/2022
 * @version $Id: $Id
 */
public class BinaryInstructionDValToReg extends BinaryInstruction {
    /**
     * <p>Constructor for BinaryInstructionDValToReg.</p>
     *
     * @param op1 a {@link fr.ensimag.ima.pseudocode.DVal} object
     * @param op2 a {@link fr.ensimag.ima.pseudocode.GPRegister} object
     */
    public BinaryInstructionDValToReg(DVal op1, GPRegister op2) {
        super(op1, op2);
    }
}
