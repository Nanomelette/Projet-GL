package fr.ensimag.deca.context;

import fr.ensimag.deca.tree.Location;
import fr.ensimag.deca.tree.Visibility;

/**
 * Definition of a field (data member of a class).
 *
 * @author gl20
 * @date 01/01/2022
 * @version $Id: $Id
 */
public class FieldDefinition extends ExpDefinition {
    /**
     * <p>Getter for the field <code>index</code>.</p>
     *
     * @return a int
     */
    public int getIndex() {
        return index;
    }

    private int index;
    
    /** {@inheritDoc} */
    @Override
    public boolean isField() {
        return true;
    }

    private final Visibility visibility;
    private final ClassDefinition containingClass;
    
    /**
     * <p>Constructor for FieldDefinition.</p>
     *
     * @param type a {@link fr.ensimag.deca.context.Type} object
     * @param location a {@link fr.ensimag.deca.tree.Location} object
     * @param visibility a {@link fr.ensimag.deca.tree.Visibility} object
     * @param memberOf a {@link fr.ensimag.deca.context.ClassDefinition} object
     * @param index a int
     */
    public FieldDefinition(Type type, Location location, Visibility visibility,
            ClassDefinition memberOf, int index) {
        super(type, location);
        this.visibility = visibility;
        this.containingClass = memberOf;
        this.index = index;
    }
    
    /** {@inheritDoc} */
    @Override
    public FieldDefinition asFieldDefinition(String errorMessage, Location l)
            throws ContextualError {
        return this;
    }

    /**
     * <p>Getter for the field <code>visibility</code>.</p>
     *
     * @return a {@link fr.ensimag.deca.tree.Visibility} object
     */
    public Visibility getVisibility() {
        return visibility;
    }

    /**
     * <p>Getter for the field <code>containingClass</code>.</p>
     *
     * @return a {@link fr.ensimag.deca.context.ClassDefinition} object
     */
    public ClassDefinition getContainingClass() {
        return containingClass;
    }

    /** {@inheritDoc} */
    @Override
    public String getNature() {
        return "field";
    }

    /** {@inheritDoc} */
    @Override
    public boolean isExpression() {
        return true;
    }

}
