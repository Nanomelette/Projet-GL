package fr.ensimag.deca.context;

import fr.ensimag.deca.tree.Location;

/**
 * Definition of a method parameter.
 *
 * @author gl20
 * @date 01/01/2022
 */
public class ParamDefinition extends ExpDefinition {

    public ParamDefinition(Type type, Location location, int index) {
        super(type, location);
    }

    public int getIndex() {
        return index;
    }

    private int index;
    
    @Override
    public String getNature() {
        return "parameter";
    }

    @Override
    public boolean isExpression() {
        return true;
    }

    @Override
    public boolean isParam() {
        return true;
    }

}
