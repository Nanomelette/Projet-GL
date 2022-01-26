package fr.ensimag.ima.pseudocode;

/**
 * Immediate operand representing an integer.
 *
 * @author Ensimag
 * @date 01/01/2022
 * @version $Id: $Id
 */
public class ImmediateInteger extends DVal {
    private int value;

    /**
     * <p>Constructor for ImmediateInteger.</p>
     *
     * @param value a int
     */
    public ImmediateInteger(int value) {
        super();
        this.value = value;
    }

    /** {@inheritDoc} */
    @Override
    public String toString() {
        return "#" + value;
    }
}
