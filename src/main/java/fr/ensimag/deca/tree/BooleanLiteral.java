package fr.ensimag.deca.tree;

import fr.ensimag.deca.context.Type;
import fr.ensimag.deca.context.BooleanType;
import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.ClassDefinition;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.context.EnvironmentExp;
import fr.ensimag.deca.tools.IndentPrintStream;
import fr.ensimag.ima.pseudocode.DVal;
import fr.ensimag.ima.pseudocode.GPRegister;
import fr.ensimag.ima.pseudocode.ImmediateInteger;
import fr.ensimag.ima.pseudocode.Label;
import fr.ensimag.ima.pseudocode.instructions.BRA;
import fr.ensimag.ima.pseudocode.instructions.LOAD;
import fr.ensimag.ima.pseudocode.instructions.WSTR;

import java.io.PrintStream;

/**
 * <p>BooleanLiteral class.</p>
 *
 * @author gl20
 * @date 01/01/2022
 * @version $Id: $Id
 */
public class BooleanLiteral extends AbstractExpr {

    private boolean value;

    /**
     * <p>Constructor for BooleanLiteral.</p>
     *
     * @param value a boolean
     */
    public BooleanLiteral(boolean value) {
        this.value = value;
    }

    /**
     * <p>Getter for the field <code>value</code>.</p>
     *
     * @return a boolean
     */
    public boolean getValue() {
        return value;
    }

    /** {@inheritDoc} */
    @Override
    public Type verifyExpr(DecacCompiler compiler, EnvironmentExp localEnv,
            ClassDefinition currentClass) throws ContextualError {
            Type type = new BooleanType(compiler.getSymbolTable().create("boolean"));
            this.setType(type);
            return type;
    }


    /** {@inheritDoc} */
    @Override
    public void decompile(IndentPrintStream s) {
        s.print(Boolean.toString(value));
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

    @Override
    String prettyPrintNode() {
        return "BooleanLiteral (" + value + ")";
    }

    /** {@inheritDoc} */
    @Override
    protected void codeGenPrint(DecacCompiler compiler, boolean printHex) {
        if (value) {
            compiler.addInstruction(new WSTR("true"));
        } else {
            compiler.addInstruction(new WSTR("false"));
        }
    }

    /** {@inheritDoc} */
    @Override
    protected DVal getDVal() {
        return new ImmediateInteger(value ? 1 : 0);
    }

    /** {@inheritDoc} */
    @Override
    protected void codeBoolean(boolean b, Label E, DecacCompiler compiler) {
        if (getValue() == b) {
            compiler.addInstruction(new BRA(E));
        }
    }

    /** {@inheritDoc} */
    @Override
    protected void codeGenInst(DecacCompiler compiler) {
        GPRegister lastRegister = compiler.getData().getFreeRegister(compiler);
        compiler.addInstruction(new LOAD(value ? 1 : 0, lastRegister));
        compiler.getData().setLastUsedRegister(lastRegister);
    }


}
