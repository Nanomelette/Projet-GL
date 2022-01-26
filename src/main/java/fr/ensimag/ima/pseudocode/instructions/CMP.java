package fr.ensimag.ima.pseudocode.instructions;

import fr.ensimag.ima.pseudocode.BinaryInstructionDValToReg;
import fr.ensimag.ima.pseudocode.DVal;
import fr.ensimag.ima.pseudocode.GPRegister;
import fr.ensimag.ima.pseudocode.ImmediateInteger;

/**
 * <p>CMP class.</p>
 *
 * @author Ensimag
 * @date 01/01/2022
 * @version $Id: $Id
 */
public class CMP extends BinaryInstructionDValToReg {

    /**
     * <p>Constructor for CMP.</p>
     *
     * @param op1 a {@link fr.ensimag.ima.pseudocode.DVal} object
     * @param op2 a {@link fr.ensimag.ima.pseudocode.GPRegister} object
     */
    public CMP(DVal op1, GPRegister op2) {
        super(op1, op2);
    }

    /**
     * <p>Constructor for CMP.</p>
     *
     * @param val a int
     * @param op2 a {@link fr.ensimag.ima.pseudocode.GPRegister} object
     */
    public CMP(int val, GPRegister op2) {
        this(new ImmediateInteger(val), op2);
    }

}
