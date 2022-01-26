package fr.ensimag.ima.pseudocode;

/**
 * Immediate operand representing a string.
 *
 * @author Ensimag
 * @date 01/01/2022
 * @version $Id: $Id
 */
public class ImmediateString extends Operand {
    private String value;

    /**
     * <p>Constructor for ImmediateString.</p>
     *
     * @param value a {@link java.lang.String} object
     */
    public ImmediateString(String value) {
        super();
        this.value = value;
    }

    /** {@inheritDoc} */
    @Override
    public String toString() {
        return "\"" + value.replace("\"", "\"\"") + "\"";
    }
}
