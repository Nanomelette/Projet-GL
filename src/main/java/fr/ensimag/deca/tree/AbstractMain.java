package fr.ensimag.deca.tree;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.ContextualError;

/**
 * Main block of a Deca program.
 *
 * @author gl20
 * @date 01/01/2022
 * @version $Id: $Id
 */
public abstract class AbstractMain extends Tree {

    /**
     * <p>codeGenMain.</p>
     *
     * @param compiler a {@link fr.ensimag.deca.DecacCompiler} object
     */
    protected abstract void codeGenMain(DecacCompiler compiler);
    
    /**
     * <p>vTableInitialization.</p>
     *
     * @param compiler a {@link fr.ensimag.deca.DecacCompiler} object
     * @param classes a {@link fr.ensimag.deca.tree.ListDeclClass} object
     */
    protected abstract void vTableInitialization(DecacCompiler compiler, ListDeclClass classes);

    /**
     * Implements non-terminal "main" of [SyntaxeContextuelle] in pass 3
     *
     * @param compiler a {@link fr.ensimag.deca.DecacCompiler} object
     * @throws fr.ensimag.deca.context.ContextualError if any.
     */
    protected abstract void verifyMain(DecacCompiler compiler) throws ContextualError;
}
