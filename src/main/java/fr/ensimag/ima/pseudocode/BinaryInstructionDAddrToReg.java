package fr.ensimag.ima.pseudocode;

/**
 * Base class for instructions with 2 operands, the first being a
 * DAddr, and the second a Register.
 *
 * @author Ensimag
 * @date 01/01/2022
 * @version $Id: $Id
 */
public class BinaryInstructionDAddrToReg extends BinaryInstructionDValToReg {

    /**
     * <p>Constructor for BinaryInstructionDAddrToReg.</p>
     *
     * @param op1 a {@link fr.ensimag.ima.pseudocode.DAddr} object
     * @param op2 a {@link fr.ensimag.ima.pseudocode.GPRegister} object
     */
    public BinaryInstructionDAddrToReg(DAddr op1, GPRegister op2) {
        super(op1, op2);
    }

}
