package fr.ensimag.deca.tree;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.ContextualError;

/**
 * Entry point for contextual verifications and code generation from outside the package.
 *
 * @author gl20
 * @date 01/01/2022
 * @version $Id: $Id
 */
public abstract class AbstractProgram extends Tree {
    /**
     * <p>verifyProgram.</p>
     *
     * @param compiler a {@link fr.ensimag.deca.DecacCompiler} object
     * @throws fr.ensimag.deca.context.ContextualError if any.
     */
    public abstract void verifyProgram(DecacCompiler compiler) throws ContextualError;
    /**
     * <p>codeGenProgram.</p>
     *
     * @param compiler a {@link fr.ensimag.deca.DecacCompiler} object
     */
    public abstract void codeGenProgram(DecacCompiler compiler) ;

}
