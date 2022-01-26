package fr.ensimag.deca.context;

import fr.ensimag.deca.tools.SymbolTable.Symbol;
import fr.ensimag.deca.tree.Location;

/**
 * Type defined by a class.
 *
 * @author gl20
 * @date 01/01/2022
 * @version $Id: $Id
 */
public class ClassType extends Type {
    
    protected ClassDefinition definition;
    
    /**
     * <p>Getter for the field <code>definition</code>.</p>
     *
     * @return a {@link fr.ensimag.deca.context.ClassDefinition} object
     */
    public ClassDefinition getDefinition() {
        return this.definition;
    }
            
    /** {@inheritDoc} */
    @Override
    public ClassType asClassType(String errorMessage, Location l) {
        return this;
    }

    /** {@inheritDoc} */
    @Override
    public boolean isClass() {
        return true;
    }

    /** {@inheritDoc} */
    @Override
    public boolean isClassOrNull() {
        return true;
    }

    /**
     * Standard creation of a type class.
     *
     * @param className a {@link fr.ensimag.deca.tools.SymbolTable.Symbol} object
     * @param location a {@link fr.ensimag.deca.tree.Location} object
     * @param superClass a {@link fr.ensimag.deca.context.ClassDefinition} object
     */
    public ClassType(Symbol className, Location location, ClassDefinition superClass) {
        super(className);
        this.definition = new ClassDefinition(this, location, superClass);
    }

    /**
     * Creates a type representing a class className.
     * (To be used by subclasses only)
     *
     * @param className a {@link fr.ensimag.deca.tools.SymbolTable.Symbol} object
     */
    protected ClassType(Symbol className) {
        super(className);
    }
    
    /**
     * {@inheritDoc}
     *
     * True if this and otherType represent the same type (in the case of
     * classes, this means they represent the same class).
     */
    @Override
    public boolean sameType(Type otherType) {
        if(otherType.isClass()){
            return true;
        }
        return false;
        //throw new UnsupportedOperationException("not yet implemented");
    }

    /**
     * Return true if potentialSuperClass is a superclass of this class.
     *
     * @param potentialSuperClass a {@link fr.ensimag.deca.context.ClassType} object
     * @return a boolean
     */
    public boolean isSubClassOf(ClassType potentialSuperClass) {
        if(this.getName().equals(potentialSuperClass.getName())){
            return true;
        }
        ClassDefinition superClass = definition.getSuperClass();
        if (superClass == null) {
            return false;
        }
        return superClass.getType().isSubClassOf(potentialSuperClass);
    }

/*
    si sametype de definition alors return true 
    sinon on regarde dans superclass avec appel recursif
    sinon false
*/
}
