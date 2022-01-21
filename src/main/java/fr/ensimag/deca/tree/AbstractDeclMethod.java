package fr.ensimag.deca.tree;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.ContextualError;

/**
 * Method declaration.
 *
 * @author gl20
 * @date 01/01/2022
 */
public abstract class AbstractDeclMethod extends Tree {

    /**
     * Pass 2 of [SyntaxeContextuelle]. Verify that the method declaration is OK
     * without looking at its content.
     */
    protected abstract void verifyMethod(DecacCompiler compiler, AbstractIdentifier classeSup, AbstractIdentifier classe)
            throws ContextualError;
}
