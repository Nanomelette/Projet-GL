package fr.ensimag.deca.tree;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.ContextualError;

/**
 * Field declaration.
 *
 * @author gl20
 * @date 01/01/2022
 * @version $Id: $Id
 */
public abstract class AbstractDeclField extends Tree {

    /**
     * Pass 2 of [SyntaxeContextuelle]. Verify that the field declaration is OK
     * without looking at its content.
     *
     * @param compiler a {@link fr.ensimag.deca.DecacCompiler} object
     * @param classeSup a {@link fr.ensimag.deca.tree.AbstractIdentifier} object
     * @param classe a {@link fr.ensimag.deca.tree.AbstractIdentifier} object
     * @throws fr.ensimag.deca.context.ContextualError if any.
     */
    protected abstract void verifyField(DecacCompiler compiler, AbstractIdentifier classeSup, AbstractIdentifier classe)
            throws ContextualError;

    /**
     * Pass 2 of [GenCode]
     *
     * @param compiler a {@link fr.ensimag.deca.DecacCompiler} object
     */
    protected abstract void codeGenDeclField(DecacCompiler compiler);

    /**
     * <p>codeGenDeclFieldZero.</p>
     *
     * @param compiler a {@link fr.ensimag.deca.DecacCompiler} object
     */
    protected abstract void codeGenDeclFieldZero(DecacCompiler compiler);
    
    /**
     * <p>verifyFieldBody.</p>
     *
     * @param compiler a {@link fr.ensimag.deca.DecacCompiler} object
     * @param classeSup a {@link fr.ensimag.deca.tree.AbstractIdentifier} object
     * @param classe a {@link fr.ensimag.deca.tree.AbstractIdentifier} object
     * @throws fr.ensimag.deca.context.ContextualError if any.
     */
    public abstract void verifyFieldBody(DecacCompiler compiler, AbstractIdentifier classeSup, AbstractIdentifier classe)
            throws ContextualError;
}
