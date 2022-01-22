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
 *
 * @author gl20
 * @date 01/01/2022
 */
public class BooleanLiteral extends AbstractExpr {

    private boolean value;

    public BooleanLiteral(boolean value) {
        this.value = value;
    }

    public boolean getValue() {
        return value;
    }

    @Override
    public Type verifyExpr(DecacCompiler compiler, EnvironmentExp localEnv,
            ClassDefinition currentClass) throws ContextualError {
            Type type = new BooleanType(compiler.getSymbolTable().create("boolean"));
            this.setType(type);
            return type;
    }


    @Override
    public void decompile(IndentPrintStream s) {
        s.print(Boolean.toString(value));
    }

    @Override
    protected void iterChildren(TreeFunction f) {
        // leaf node => nothing to do
    }

    @Override
    protected void prettyPrintChildren(PrintStream s, String prefix) {
        // leaf node => nothing to do
    }

    @Override
    String prettyPrintNode() {
        return "BooleanLiteral (" + value + ")";
    }

    @Override
    protected void codeGenPrint(DecacCompiler compiler) {
        if (value) {
            compiler.addInstruction(new WSTR("true"));
        } else {
            compiler.addInstruction(new WSTR("false"));
        }
    }

    @Override
    protected DVal getDVal() {
        return new ImmediateInteger(value ? 1 : 0);
    }

    @Override
    protected void codeBoolean(boolean b, Label E, DecacCompiler compiler) {
        if (getValue() == b) {
            compiler.addInstruction(new BRA(E));
        }
        // codeGenInst(compiler);
        // compiler.addInstruction(new CMP(new ImmediateInteger(b ? 1 : 0), compiler.getData().getLastUsedRegister()));
        // compiler.addInstruction(new BEQ(E));
    }

    @Override
    protected void codeGenInst(DecacCompiler compiler) {
        GPRegister lastRegister = compiler.getData().getFreeRegister(compiler);
        compiler.addInstruction(new LOAD(value ? 1 : 0, lastRegister));
        compiler.getData().setLastUsedRegister(lastRegister);
    }


}
