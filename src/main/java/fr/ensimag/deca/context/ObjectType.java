package fr.ensimag.deca.context;

import fr.ensimag.deca.tools.SymbolTable;

/**
 * <p>ObjectType class.</p>
 *
 * @author oscarmaggiori
 * @version $Id: $Id
 */
public class ObjectType extends Type {

    /**
     * <p>Constructor for ObjectType.</p>
     *
     * @param name a {@link fr.ensimag.deca.tools.SymbolTable.Symbol} object
     */
    public ObjectType(SymbolTable.Symbol name) {
        super(name);
    }

    /** {@inheritDoc} */
    @Override
    public boolean isObject() {
        return true;
    }

    /** {@inheritDoc} */
    @Override
    public boolean sameType(Type otherType) {
        return otherType.isInt();
    }



}
