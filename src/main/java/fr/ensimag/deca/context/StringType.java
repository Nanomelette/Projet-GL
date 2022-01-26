package fr.ensimag.deca.context;

import fr.ensimag.deca.tools.SymbolTable;

/**
 * <p>StringType class.</p>
 *
 * @author Ensimag
 * @date 01/01/2022
 * @version $Id: $Id
 */
public class StringType extends Type {

    /**
     * <p>Constructor for StringType.</p>
     *
     * @param name a {@link fr.ensimag.deca.tools.SymbolTable.Symbol} object
     */
    public StringType(SymbolTable.Symbol name) {
        super(name);
    }

    /** {@inheritDoc} */
    @Override
    public boolean isString() {
        return true;
    }

    /** {@inheritDoc} */
    @Override
    public boolean sameType(Type otherType) {
        return otherType.isString();
    }


}
