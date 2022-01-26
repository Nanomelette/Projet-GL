package fr.ensimag.deca.tree;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.context.EnvironmentExp;
import fr.ensimag.deca.context.Type;

/**
 * Param declaration.
 *
 * @author gl20
 * @date 01/01/2022
 * @version $Id: $Id
 */
public abstract class AbstractDeclParam extends Tree {

    /**
     * Pass 2 of [SyntaxeContextuelle]. Verify that the param declaration is OK
     * without looking at its content.
     *
     * @param compiler a {@link fr.ensimag.deca.DecacCompiler} object
     * @return a {@link fr.ensimag.deca.context.Type} object
     * @throws fr.ensimag.deca.context.ContextualError if any.
     */
    protected abstract Type verifyParam(DecacCompiler compiler)
            throws ContextualError;
    
    /**
     * <p>verifyDeclParam.</p>
     *
     * @param compiler a {@link fr.ensimag.deca.DecacCompiler} object
     * @param localEnv a {@link fr.ensimag.deca.context.EnvironmentExp} object
     * @param index a int
     * @throws fr.ensimag.deca.context.ContextualError if any.
     */
    protected abstract void verifyDeclParam(DecacCompiler compiler, EnvironmentExp localEnv, int index)
            throws ContextualError;
}
