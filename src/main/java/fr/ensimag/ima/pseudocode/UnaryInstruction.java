package fr.ensimag.ima.pseudocode;

import java.io.PrintStream;
import org.apache.commons.lang.Validate;

/**
 * Instruction with a single operand.
 *
 * @author Ensimag
 * @date 01/01/2022
 * @version $Id: $Id
 */
public abstract class UnaryInstruction extends Instruction {
    private Operand operand;

    @Override
    void displayOperands(PrintStream s) {
        s.print(" ");
        s.print(operand);
    }

    /**
     * <p>Constructor for UnaryInstruction.</p>
     *
     * @param operand a {@link fr.ensimag.ima.pseudocode.Operand} object
     */
    protected UnaryInstruction(Operand operand) {
        Validate.notNull(operand);
        this.operand = operand;
    }

    /**
     * <p>Getter for the field <code>operand</code>.</p>
     *
     * @return a {@link fr.ensimag.ima.pseudocode.Operand} object
     */
    public Operand getOperand() {
        return operand;
    }

}
