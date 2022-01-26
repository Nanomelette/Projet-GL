package fr.ensimag.deca.context;

import fr.ensimag.deca.tree.Location;
import fr.ensimag.ima.pseudocode.DAddr;

/**
 * Definition associated to identifier in expressions.
 *
 * @author gl20
 * @date 01/01/2022
 * @version $Id: $Id
 */
public abstract class ExpDefinition extends Definition {

    /**
     * <p>Setter for the field <code>operand</code>.</p>
     *
     * @param operand a {@link fr.ensimag.ima.pseudocode.DAddr} object
     */
    public void setOperand(DAddr operand) {
        this.operand = operand;
    }

    /**
     * <p>Getter for the field <code>operand</code>.</p>
     *
     * @return a {@link fr.ensimag.ima.pseudocode.DAddr} object
     */
    public DAddr getOperand() {
        return operand;
    }
    private DAddr operand;

    /**
     * <p>Constructor for ExpDefinition.</p>
     *
     * @param type a {@link fr.ensimag.deca.context.Type} object
     * @param location a {@link fr.ensimag.deca.tree.Location} object
     */
    public ExpDefinition(Type type, Location location) {
        super(type, location);
    }

}
