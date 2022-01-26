package fr.ensimag.deca.context;

import fr.ensimag.deca.tree.Location;

/**
 * Definition of a variable.
 *
 * @author gl20
 * @date 01/01/2022
 * @version $Id: $Id
 */
public class VariableDefinition extends ExpDefinition {
    /**
     * <p>Constructor for VariableDefinition.</p>
     *
     * @param type a {@link fr.ensimag.deca.context.Type} object
     * @param location a {@link fr.ensimag.deca.tree.Location} object
     */
    public VariableDefinition(Type type, Location location) {
        super(type, location);
    }

    /** {@inheritDoc} */
    @Override
    public String getNature() {
        return "variable";
    }

    /** {@inheritDoc} */
    @Override
    public boolean isExpression() {
        return true;
    }
}
