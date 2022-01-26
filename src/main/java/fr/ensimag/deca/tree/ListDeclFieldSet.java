package fr.ensimag.deca.tree;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.tools.IndentPrintStream;

/**
 * List of set of field declaration.
 *
 * @author oscarmaggiori
 * @version $Id: $Id
 */
public class ListDeclFieldSet extends TreeList<AbstractDeclFieldSet>{


    /** {@inheritDoc} */
    @Override
    public void decompile(IndentPrintStream s) {
        // nothing
    }

    /**
     * <p>codeGenListDeclFieldSet.</p>
     *
     * @param compiler a {@link fr.ensimag.deca.DecacCompiler} object
     */
    protected void codeGenListDeclFieldSet(DecacCompiler compiler) {
        for (AbstractDeclFieldSet declField : getList()) {
            declField.codeGenDeclFieldSet(compiler);
        }
    }

    /**
     * <p>codeGenListDeclFieldSetZero.</p>
     *
     * @param compiler a {@link fr.ensimag.deca.DecacCompiler} object
     */
    protected void codeGenListDeclFieldSetZero(DecacCompiler compiler) {
        for (AbstractDeclFieldSet declField : getList()) {
            declField.codeGenDeclFieldSetZero(compiler);
        }
    }

}
