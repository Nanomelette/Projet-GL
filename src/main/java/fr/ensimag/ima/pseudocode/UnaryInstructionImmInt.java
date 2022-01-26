package fr.ensimag.ima.pseudocode;

/**
 * <p>Abstract UnaryInstructionImmInt class.</p>
 *
 * @author Ensimag
 * @date 01/01/2022
 * @version $Id: $Id
 */
public abstract class UnaryInstructionImmInt extends UnaryInstruction {

    /**
     * <p>Constructor for UnaryInstructionImmInt.</p>
     *
     * @param operand a {@link fr.ensimag.ima.pseudocode.ImmediateInteger} object
     */
    protected UnaryInstructionImmInt(ImmediateInteger operand) {
        super(operand);
    }

    /**
     * <p>Constructor for UnaryInstructionImmInt.</p>
     *
     * @param i a int
     */
    protected UnaryInstructionImmInt(int i) {
        super(new ImmediateInteger(i));
    }

}
