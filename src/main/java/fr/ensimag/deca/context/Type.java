package fr.ensimag.deca.context;

import fr.ensimag.deca.tools.SymbolTable.Symbol;
import fr.ensimag.deca.tree.Location;

/**
 * Deca Type (internal representation of the compiler)
 *
 * @author gl20
 * @date 01/01/2022
 * @version $Id: $Id
 */
public abstract class Type {


    /**
     * True if this and otherType represent the same type (in the case of
     * classes, this means they represent the same class).
     *
     * @param otherType a {@link fr.ensimag.deca.context.Type} object
     * @return a boolean
     */
    public abstract boolean sameType(Type otherType);

    private final Symbol name;

    /**
     * <p>Constructor for Type.</p>
     *
     * @param name a {@link fr.ensimag.deca.tools.SymbolTable.Symbol} object
     */
    public Type(Symbol name) {
        this.name = name;
    }

    /**
     * <p>Getter for the field <code>name</code>.</p>
     *
     * @return a {@link fr.ensimag.deca.tools.SymbolTable.Symbol} object
     */
    public Symbol getName() {
        return name;
    }

    /** {@inheritDoc} */
    @Override
    public String toString() {
        return getName().toString();
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
     * <p>isInt.</p>
     *
     * @return a boolean
     */
    public boolean isInt() {
        return false;
    }

    /**
     * <p>isFloat.</p>
     *
     * @return a boolean
     */
    public boolean isFloat() {
        return false;
    }

    /**
     * <p>isBoolean.</p>
     *
     * @return a boolean
     */
    public boolean isBoolean() {
        return false;
    }

    /**
     * <p>isVoid.</p>
     *
     * @return a boolean
     */
    public boolean isVoid() {
        return false;
    }

    /**
     * <p>isString.</p>
     *
     * @return a boolean
     */
    public boolean isString() {
        return false;
    }

    /**
     * <p>isNull.</p>
     *
     * @return a boolean
     */
    public boolean isNull() {
        return false;
    }

    /**
     * <p>isClassOrNull.</p>
     *
     * @return a boolean
     */
    public boolean isClassOrNull() {
        return false;
    }

    /**
     * <p>isObject.</p>
     *
     * @return a boolean
     */
    public boolean isObject() {
        return false;
    }

    /**
     * Returns the same object, as type ClassType, if possible. Throws
     * ContextualError(errorMessage, l) otherwise.
     *
     * Can be seen as a cast, but throws an explicit contextual error when the
     * cast fails.
     *
     * @param errorMessage a {@link java.lang.String} object
     * @param l a {@link fr.ensimag.deca.tree.Location} object
     * @return a {@link fr.ensimag.deca.context.ClassType} object
     * @throws fr.ensimag.deca.context.ContextualError if any.
     */
    public ClassType asClassType(String errorMessage, Location l)
            throws ContextualError {
        throw new ContextualError(errorMessage, l);
    }

}
