package fr.ensimag.ima.pseudocode;

/**
 * Operand representing a register indirection with offset, e.g. 42(R3).
 *
 * @author Ensimag
 * @date 01/01/2022
 * @version $Id: $Id
 */
public class RegisterOffset extends DAddr {
    /**
     * <p>Getter for the field <code>offset</code>.</p>
     *
     * @return a int
     */
    public int getOffset() {
        return offset;
    }
    /**
     * <p>Getter for the field <code>register</code>.</p>
     *
     * @return a {@link fr.ensimag.ima.pseudocode.Register} object
     */
    public Register getRegister() {
        return register;
    }
    private final int offset;
    private final Register register;
    /**
     * <p>Constructor for RegisterOffset.</p>
     *
     * @param offset a int
     * @param register a {@link fr.ensimag.ima.pseudocode.Register} object
     */
    public RegisterOffset(int offset, Register register) {
        super();
        this.offset = offset;
        this.register = register;
    }
    /** {@inheritDoc} */
    @Override
    public String toString() {
        return offset + "(" + register + ")";
    }
}
