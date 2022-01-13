package fr.ensimag.deca.tree;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.ContextualError;

/**
 * Param declaration.
 *
 * @author gl20
 * @date 01/01/2022
 */
public abstract class AbstractDeclParam extends Tree {

    /**
     * Pass 2 of [SyntaxeContextuelle]. Verify that the param declaration is OK
     * without looking at its content.
     */
    protected abstract void verifyParam(DecacCompiler compiler)
            throws ContextualError;
}
