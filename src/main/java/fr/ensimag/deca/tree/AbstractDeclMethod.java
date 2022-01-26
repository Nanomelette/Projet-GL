package fr.ensimag.deca.tree;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.ContextualError;

/**
 * Method declaration.
 *
 * @author gl20
 * @date 01/01/2022
 * @version $Id: $Id
 */
public abstract class AbstractDeclMethod extends Tree {

    /**
     * Pass 2 of [SyntaxeContextuelle]. Verify that the method declaration is OK
     * without looking at its content.
     *
     * @param compiler a {@link fr.ensimag.deca.DecacCompiler} object
     * @param classeSup a {@link fr.ensimag.deca.tree.AbstractIdentifier} object
     * @param classe a {@link fr.ensimag.deca.tree.AbstractIdentifier} object
     * @throws fr.ensimag.deca.context.ContextualError if any.
     */
    protected abstract void verifyMethod(DecacCompiler compiler, AbstractIdentifier classeSup, AbstractIdentifier classe)
            throws ContextualError;

    /**
     * <p>getName.</p>
     *
     * @return a {@link fr.ensimag.deca.tree.AbstractIdentifier} object
     */
    public abstract AbstractIdentifier getName();

    /**
     * <p>codeGenDeclMethod.</p>
     *
     * @param compiler a {@link fr.ensimag.deca.DecacCompiler} object
     */
    protected abstract void codeGenDeclMethod(DecacCompiler compiler);
    
    /**
     * <p>verifyMethodBody.</p>
     *
     * @param compiler a {@link fr.ensimag.deca.DecacCompiler} object
     * @param classeSup a {@link fr.ensimag.deca.tree.AbstractIdentifier} object
     * @param classe a {@link fr.ensimag.deca.tree.AbstractIdentifier} object
     * @throws fr.ensimag.deca.context.ContextualError if any.
     */
    protected abstract void verifyMethodBody(DecacCompiler compiler, AbstractIdentifier classeSup, AbstractIdentifier classe)
            throws ContextualError;
}
