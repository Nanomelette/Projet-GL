package fr.ensimag.deca.tree;

import fr.ensimag.deca.context.Type;
import fr.ensimag.deca.context.NullType;
import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.ClassDefinition;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.context.EnvironmentExp;
import fr.ensimag.deca.tools.IndentPrintStream;
import fr.ensimag.ima.pseudocode.GPRegister;
import fr.ensimag.ima.pseudocode.NullOperand;
import fr.ensimag.ima.pseudocode.instructions.LOAD;

import java.io.PrintStream;

/**
 *
 * @author gl20
 * @date 01/01/2022
 */
public class Null extends AbstractExpr {

    private Object value;

    public Null() {
        this.value = null;
    }

    public Object getValue() {
        return value;
    }

    @Override
    public Type verifyExpr(DecacCompiler compiler, EnvironmentExp localEnv,
            ClassDefinition currentClass) throws ContextualError {
            Type type = new NullType(compiler.getSymbolTable().create("null"));
            this.setType(type);
            return type;
    }


    @Override
    public void decompile(IndentPrintStream s) {
        s.print("null");
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
        return "Null";
    }

    @Override
    protected void codeGenInst(DecacCompiler compiler) {
        GPRegister register = compiler.getData().getFreeRegister(compiler);
        compiler.addInstruction(new LOAD(new NullOperand(), register));
        compiler.getData().setLastUsedRegister(register);
    }

}
