package fr.ensimag.deca.tree;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.ContextualError;

/**
 * Field declaration.
 *
 * @author gl20
 * @date 01/01/2022
 */
public abstract class AbstractDeclField extends Tree {

    /**
     * Pass 2 of [SyntaxeContextuelle]. Verify that the field declaration is OK
     * without looking at its content.
     */
    protected abstract void verifyField(DecacCompiler compiler, AbstractIdentifier classeSup, AbstractIdentifier classe)
            throws ContextualError;

    /**
     * Pass 2 of [GenCode]
     */
    protected abstract void codeGenDeclField(DecacCompiler compiler);

    protected abstract void codeGenDeclFieldZero(DecacCompiler compiler);
    
    public abstract void verifyFieldBody(DecacCompiler compiler, AbstractIdentifier classeSup, AbstractIdentifier classe)
            throws ContextualError;
}
