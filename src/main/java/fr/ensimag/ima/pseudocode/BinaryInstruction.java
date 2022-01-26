package fr.ensimag.ima.pseudocode;

import java.io.PrintStream;
import org.apache.commons.lang.Validate;

/**
 * Base class for instructions with 2 operands.
 *
 * @author Ensimag
 * @date 01/01/2022
 * @version $Id: $Id
 */
public class BinaryInstruction extends Instruction {
    private Operand operand1, operand2;

    /**
     * <p>Getter for the field <code>operand1</code>.</p>
     *
     * @return a {@link fr.ensimag.ima.pseudocode.Operand} object
     */
    public Operand getOperand1() {
        return operand1;
    }

    /**
     * <p>Getter for the field <code>operand2</code>.</p>
     *
     * @return a {@link fr.ensimag.ima.pseudocode.Operand} object
     */
    public Operand getOperand2() {
        return operand2;
    }

    @Override
    void displayOperands(PrintStream s) {
        s.print(" ");
        s.print(operand1);
        s.print(", ");
        s.print(operand2);
    }

    /**
     * <p>Constructor for BinaryInstruction.</p>
     *
     * @param op1 a {@link fr.ensimag.ima.pseudocode.Operand} object
     * @param op2 a {@link fr.ensimag.ima.pseudocode.Operand} object
     */
    protected BinaryInstruction(Operand op1, Operand op2) {
        Validate.notNull(op1);
        Validate.notNull(op2);
        this.operand1 = op1;
        this.operand2 = op2;
    }
}
