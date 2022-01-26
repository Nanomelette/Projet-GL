package fr.ensimag.ima.pseudocode.instructions;

import fr.ensimag.ima.pseudocode.BinaryInstructionDAddrToReg;
import fr.ensimag.ima.pseudocode.DAddr;
import fr.ensimag.ima.pseudocode.GPRegister;

/**
 * <p>LEA class.</p>
 *
 * @author Ensimag
 * @date 01/01/2022
 * @version $Id: $Id
 */
public class LEA extends BinaryInstructionDAddrToReg {

    /**
     * <p>Constructor for LEA.</p>
     *
     * @param op1 a {@link fr.ensimag.ima.pseudocode.DAddr} object
     * @param op2 a {@link fr.ensimag.ima.pseudocode.GPRegister} object
     */
    public LEA(DAddr op1, GPRegister op2) {
        super(op1, op2);
    }

}
