package fr.ensimag.deca.context;

import fr.ensimag.deca.tree.Location;

/**
 * Definition of an identifier.
 *
 * @author gl20
 * @date 01/01/2022
 * @version $Id: $Id
 */
public abstract class Definition {
    /** {@inheritDoc} */
    @Override
    public String toString() {
        String res;
        res = getNature();
        if (location == Location.BUILTIN) {
            res += " (builtin)";
        } else {
            res += " defined at " + location;
        }
        res += ", type=" + type;
        return res;
    }

    /**
     * <p>getNature.</p>
     *
     * @return a {@link java.lang.String} object
     */
    public abstract String getNature();

    /**
     * <p>Constructor for Definition.</p>
     *
     * @param type a {@link fr.ensimag.deca.context.Type} object
     * @param location a {@link fr.ensimag.deca.tree.Location} object
     */
    public Definition(Type type, Location location) {
        super();
        this.location = location;
        this.type = type;
    }

    /**
     * <p>Getter for the field <code>type</code>.</p>
     *
     * @return a {@link fr.ensimag.deca.context.Type} object
     */
    public Type getType() {
        return type;
    }

    /**
     * <p>Getter for the field <code>location</code>.</p>
     *
     * @return a {@link fr.ensimag.deca.tree.Location} object
     */
    public Location getLocation() {
        return location;
    }

    /**
     * <p>Setter for the field <code>location</code>.</p>
     *
     * @param location a {@link fr.ensimag.deca.tree.Location} object
     */
    public void setLocation(Location location) {
        this.location = location;
    }

    private Location location;
    private Type type;
    /**
     * <p>isField.</p>
     *
     * @return a boolean
     */
    public boolean isField() {
        return false;
    }
    
    /**
     * <p>isMethod.</p>
     *
     * @return a boolean
     */
    public boolean isMethod() {
        return false;
    }

    /**
     * <p>isClass.</p>
     *
     * @return a boolean
     */
    public boolean isClass() {
        return false;
    }

    /**
     * <p>isParam.</p>
     *
     * @return a boolean
     */
    public boolean isParam() {
        return false;
    }

    /**
     * Return the same object, as type MethodDefinition, if possible. Throws
     * ContextualError(errorMessage, l) otherwise.
     *
     * @param errorMessage a {@link java.lang.String} object
     * @param l a {@link fr.ensimag.deca.tree.Location} object
     * @return a {@link fr.ensimag.deca.context.MethodDefinition} object
     * @throws fr.ensimag.deca.context.ContextualError if any.
     */
    public MethodDefinition asMethodDefinition(String errorMessage, Location l)
            throws ContextualError {
        throw new ContextualError(errorMessage, l);
    }
    
    /**
     * Return the same object, as type FieldDefinition, if possible. Throws
     * ContextualError(errorMessage, l) otherwise.
     *
     * @param errorMessage a {@link java.lang.String} object
     * @param l a {@link fr.ensimag.deca.tree.Location} object
     * @return a {@link fr.ensimag.deca.context.FieldDefinition} object
     * @throws fr.ensimag.deca.context.ContextualError if any.
     */
    public FieldDefinition asFieldDefinition(String errorMessage, Location l)
            throws ContextualError {
        throw new ContextualError(errorMessage, l);
    }

    /**
     * <p>isExpression.</p>
     *
     * @return a boolean
     */
    public abstract boolean isExpression();

}
