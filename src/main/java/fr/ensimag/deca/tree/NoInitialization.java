package fr.ensimag.deca.tree;

import fr.ensimag.deca.context.Type;
import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.ClassDefinition;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.context.EnvironmentExp;
import fr.ensimag.deca.tools.IndentPrintStream;
import fr.ensimag.ima.pseudocode.DAddr;
import fr.ensimag.ima.pseudocode.ImmediateFloat;
import fr.ensimag.ima.pseudocode.NullOperand;
import fr.ensimag.ima.pseudocode.Register;
import fr.ensimag.ima.pseudocode.instructions.LOAD;
import fr.ensimag.ima.pseudocode.instructions.STORE;

import java.io.PrintStream;

/**
 * Absence of initialization (e.g. "int x;" as opposed to "int x =
 * 42;").
 *
 * @author gl20
 * @date 01/01/2022
 * @version $Id: $Id
 */
public class NoInitialization extends AbstractInitialization {

    /** {@inheritDoc} */
    @Override
    protected void verifyInitialization(DecacCompiler compiler, Type t,
            EnvironmentExp localEnv, ClassDefinition currentClass)
            throws ContextualError {
        //nothing
    }


    /**
     * {@inheritDoc}
     *
     * Node contains no real information, nothing to check.
     */
    @Override
    protected void checkLocation() {
        // nothing
    }

    /** {@inheritDoc} */
    @Override
    public void decompile(IndentPrintStream s) {
        // nothing
    }

    /** {@inheritDoc} */
    @Override
    protected void iterChildren(TreeFunction f) {
        // leaf node => nothing to do
    }

    /** {@inheritDoc} */
    @Override
    protected void prettyPrintChildren(PrintStream s, String prefix) {
        // leaf node => nothing to do
    }

    /** {@inheritDoc} */
    protected void codeGenInitField(DecacCompiler compiler, Type type) {
        codeGenInitAux(compiler, type);
    };

    /** {@inheritDoc} */
    protected void codeGenInitVar(DecacCompiler compiler, DAddr address, Type type) {
        codeGenInitAux(compiler, type);
        compiler.addInstruction(new STORE(Register.R0, address));
    };

    /**
     * <p>codeGenInitAux.</p>
     *
     * @param compiler a {@link fr.ensimag.deca.DecacCompiler} object
     * @param type a {@link fr.ensimag.deca.context.Type} object
     */
    protected void codeGenInitAux(DecacCompiler compiler, Type type) {
        if (type.isInt() || type.isBoolean()) {
            compiler.addInstruction(new LOAD(0, Register.R0));
        } else if (type.isFloat()) {
            compiler.addInstruction(new LOAD(new ImmediateFloat(0), Register.R0));
        } else {
            compiler.addInstruction(new LOAD(new NullOperand(), Register.R0));
        }
    }
}
