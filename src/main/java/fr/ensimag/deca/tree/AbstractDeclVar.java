package fr.ensimag.deca.tree;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.ClassDefinition;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.context.EnvironmentExp;

/**
 * Variable declaration
 *
 * @author gl20
 * @date 01/01/2022
 * @version $Id: $Id
 */
public abstract class AbstractDeclVar extends Tree {
    
    /**
     * Implements non-terminal "decl_var" of [SyntaxeContextuelle] in pass 3
     *
     * @param compiler contains "env_types" attribute
     * @throws fr.ensimag.deca.context.ContextualError if any.
     */
    protected abstract void verifyDeclVar(DecacCompiler compiler,
            EnvironmentExp localEnv, ClassDefinition currentClass)
            throws ContextualError;

    /**
     * <p>codeGenDeclVarGlob.</p>
     *
     * @param compiler a {@link fr.ensimag.deca.DecacCompiler} object
     */
    protected abstract void codeGenDeclVarGlob(DecacCompiler compiler);
    /**
     * <p>codeGenDeclVarLoc.</p>
     *
     * @param compiler a {@link fr.ensimag.deca.DecacCompiler} object
     */
    protected abstract void codeGenDeclVarLoc(DecacCompiler compiler);
}
