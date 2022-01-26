package fr.ensimag.ima.pseudocode;

import org.apache.commons.lang.Validate;

/**
 * Label used as operand
 *
 * @author Ensimag
 * @date 01/01/2022
 * @version $Id: $Id
 */
public class LabelOperand extends DVal {
    /**
     * <p>Getter for the field <code>label</code>.</p>
     *
     * @return a {@link fr.ensimag.ima.pseudocode.Label} object
     */
    public Label getLabel() {
        return label;
    }

    private Label label;
    
    /**
     * <p>Constructor for LabelOperand.</p>
     *
     * @param label a {@link fr.ensimag.ima.pseudocode.Label} object
     */
    public LabelOperand(Label label) {
        super();
        Validate.notNull(label);
        this.label = label;
    }

    /** {@inheritDoc} */
    @Override
    public String toString() {
        return label.toString();
    }

}
