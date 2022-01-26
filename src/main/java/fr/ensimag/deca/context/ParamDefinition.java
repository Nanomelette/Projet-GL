package fr.ensimag.deca.context;

import fr.ensimag.deca.tree.Location;

/**
 * Definition of a method parameter.
 *
 * @author gl20
 * @date 01/01/2022
 * @version $Id: $Id
 */
public class ParamDefinition extends ExpDefinition {

    private int index;

    /**
     * <p>Constructor for ParamDefinition.</p>
     *
     * @param type a {@link fr.ensimag.deca.context.Type} object
     * @param location a {@link fr.ensimag.deca.tree.Location} object
     */
    public ParamDefinition(Type type, Location location) {
        super(type, location);
    }

    /**
     * <p>Setter for the field <code>index</code>.</p>
     *
     * @param index a int
     */
    public void setIndex(int index) {
        this.index = index;
    }

    /**
     * <p>Getter for the field <code>index</code>.</p>
     *
     * @return a int
     */
    public int getIndex() {
        return index;
    }

    
    /** {@inheritDoc} */
    @Override
    public String getNature() {
        return "parameter";
    }

    /** {@inheritDoc} */
    @Override
    public boolean isExpression() {
        return true;
    }

    /** {@inheritDoc} */
    @Override
    public boolean isParam() {
        return true;
    }

}
