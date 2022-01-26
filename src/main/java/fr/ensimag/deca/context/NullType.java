package fr.ensimag.deca.context;

import fr.ensimag.deca.tools.SymbolTable;

/**
 * <p>NullType class.</p>
 *
 * @author Ensimag
 * @date 01/01/2022
 * @version $Id: $Id
 */
public class NullType extends Type {

    /**
     * <p>Constructor for NullType.</p>
     *
     * @param name a {@link fr.ensimag.deca.tools.SymbolTable.Symbol} object
     */
    public NullType(SymbolTable.Symbol name) {
        super(name);
    }

    /** {@inheritDoc} */
    @Override
    public boolean sameType(Type otherType) {
        return otherType.isNull();
    }

    /** {@inheritDoc} */
    @Override
    public boolean isNull() {
        return true;
    }

    /** {@inheritDoc} */
    @Override
    public boolean isClassOrNull() {
        return true;
    }


}
