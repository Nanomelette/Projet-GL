package fr.ensimag.deca.tree;

import fr.ensimag.deca.context.Type;
import fr.ensimag.ima.pseudocode.DAddr;
import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.ClassDefinition;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.context.EnvironmentExp;

/**
 * Initialization (of variable, field, ...)
 *
 * @author gl20
 * @date 01/01/2022
 * @version $Id: $Id
 */
public abstract class AbstractInitialization extends Tree {
    
    /**
     * Implements non-terminal "initialization" of [SyntaxeContextuelle] in pass 3
     *
     * @param compiler contains "env_types" attribute
     * @param t corresponds to the "type" attribute
     * @param localEnv corresponds to the "env_exp" attribute
     * @throws fr.ensimag.deca.context.ContextualError if any.
     */
    protected abstract void verifyInitialization(DecacCompiler compiler,
            Type t, EnvironmentExp localEnv, ClassDefinition currentClass)
            throws ContextualError;

    /**
     * <p>codeGenInitField.</p>
     *
     * @param compiler a {@link fr.ensimag.deca.DecacCompiler} object
     * @param type a {@link fr.ensimag.deca.context.Type} object
     */
    protected abstract void codeGenInitField(DecacCompiler compiler, Type type);

    /**
     * <p>codeGenInitVar.</p>
     *
     * @param compiler a {@link fr.ensimag.deca.DecacCompiler} object
     * @param address a {@link fr.ensimag.ima.pseudocode.DAddr} object
     * @param type a {@link fr.ensimag.deca.context.Type} object
     */
    protected abstract void codeGenInitVar(DecacCompiler compiler, DAddr address, Type type);
}
