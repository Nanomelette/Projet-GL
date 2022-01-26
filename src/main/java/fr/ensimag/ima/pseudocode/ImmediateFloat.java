package fr.ensimag.ima.pseudocode;

/**
 * Immediate operand containing a float value.
 *
 * @author Ensimag
 * @date 01/01/2022
 * @version $Id: $Id
 */
public class ImmediateFloat extends DVal {
    private float value;

    /**
     * <p>Constructor for ImmediateFloat.</p>
     *
     * @param value a float
     */
    public ImmediateFloat(float value) {
        super();
        this.value = value;
    }

    /** {@inheritDoc} */
    @Override
    public String toString() {
        return "#" + Float.toHexString(value);
    }
}
