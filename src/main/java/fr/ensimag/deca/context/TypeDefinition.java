package fr.ensimag.deca.context;

import fr.ensimag.deca.tree.Location;

/**
 * Definition of a Deca type (builtin or class).
 *
 * @author gl20
 * @date 01/01/2022
 * @version $Id: $Id
 */
public class TypeDefinition extends Definition {

    /**
     * <p>Constructor for TypeDefinition.</p>
     *
     * @param type a {@link fr.ensimag.deca.context.Type} object
     * @param location a {@link fr.ensimag.deca.tree.Location} object
     */
    public TypeDefinition(Type type, Location location) {
        super(type, location);
    }

    /** {@inheritDoc} */
    @Override
    public String getNature() {
        return "type";
    }

    /** {@inheritDoc} */
    @Override
    public boolean isExpression() {
        return false;
    }

}
