package fr.ensimag.deca.context;

import fr.ensimag.deca.tools.SymbolTable;

/**
 * <p>BooleanType class.</p>
 *
 * @author Ensimag
 * @date 01/01/2022
 * @version $Id: $Id
 */
public class BooleanType extends Type {

    /**
     * <p>Constructor for BooleanType.</p>
     *
     * @param name a {@link fr.ensimag.deca.tools.SymbolTable.Symbol} object
     */
    public BooleanType(SymbolTable.Symbol name) {
        super(name);
    }

    /** {@inheritDoc} */
    @Override
    public boolean isBoolean() {
        return true;
    }

    /** {@inheritDoc} */
    @Override
    public boolean sameType(Type otherType) {
        return otherType.isBoolean();
    }


}

