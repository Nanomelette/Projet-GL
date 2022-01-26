package fr.ensimag.ima.pseudocode.instructions;

import fr.ensimag.ima.pseudocode.BinaryInstructionDValToReg;
import fr.ensimag.ima.pseudocode.DVal;
import fr.ensimag.ima.pseudocode.GPRegister;
import fr.ensimag.ima.pseudocode.ImmediateInteger;

/**
 * <p>NEW class.</p>
 *
 * @author Ensimag
 * @date 01/01/2022
 * @version $Id: $Id
 */
public class NEW extends BinaryInstructionDValToReg {

    /**
     * <p>Constructor for NEW.</p>
     *
     * @param size a {@link fr.ensimag.ima.pseudocode.DVal} object
     * @param destination a {@link fr.ensimag.ima.pseudocode.GPRegister} object
     */
    public NEW(DVal size, GPRegister destination) {
        super(size, destination);
    }

    /**
     * <p>Constructor for NEW.</p>
     *
     * @param size a int
     * @param op2 a {@link fr.ensimag.ima.pseudocode.GPRegister} object
     */
    public NEW(int size, GPRegister op2) {
        super(new ImmediateInteger(size), op2);
    }

}
