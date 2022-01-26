package fr.ensimag.ima.pseudocode.instructions;

import fr.ensimag.ima.pseudocode.BinaryInstructionDValToReg;
import fr.ensimag.ima.pseudocode.DVal;
import fr.ensimag.ima.pseudocode.GPRegister;
import fr.ensimag.ima.pseudocode.ImmediateInteger;

/**
 * <p>LOAD class.</p>
 *
 * @author Ensimag
 * @date 01/01/2022
 * @version $Id: $Id
 */
public class LOAD extends BinaryInstructionDValToReg {

    /**
     * <p>Constructor for LOAD.</p>
     *
     * @param op1 a {@link fr.ensimag.ima.pseudocode.DVal} object
     * @param op2 a {@link fr.ensimag.ima.pseudocode.GPRegister} object
     */
    public LOAD(DVal op1, GPRegister op2) {
        super(op1, op2);
    }

    /**
     * <p>Constructor for LOAD.</p>
     *
     * @param i a int
     * @param r a {@link fr.ensimag.ima.pseudocode.GPRegister} object
     */
    public LOAD(int i, GPRegister r) {
        this(new ImmediateInteger(i), r);
    }

}
