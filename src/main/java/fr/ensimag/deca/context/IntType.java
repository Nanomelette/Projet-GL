package fr.ensimag.deca.context;

import fr.ensimag.deca.tools.SymbolTable;

/**
 * <p>IntType class.</p>
 *
 * @author Ensimag
 * @date 01/01/2022
 * @version $Id: $Id
 */
public class IntType extends Type {

    /**
     * <p>Constructor for IntType.</p>
     *
     * @param name a {@link fr.ensimag.deca.tools.SymbolTable.Symbol} object
     */
    public IntType(SymbolTable.Symbol name) {
        super(name);
    }

    /** {@inheritDoc} */
    @Override
    public boolean isInt() {
        return true;
    }

    /** {@inheritDoc} */
    @Override
    public boolean sameType(Type otherType) {
        return otherType.isInt();
    }


}
