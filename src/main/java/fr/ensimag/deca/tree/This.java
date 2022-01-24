package fr.ensimag.deca.tree;
import fr.ensimag.deca.context.Type;
import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.ClassDefinition;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.context.EnvironmentExp;
import fr.ensimag.deca.tools.IndentPrintStream;
import fr.ensimag.ima.pseudocode.GPRegister;
import fr.ensimag.ima.pseudocode.Register;
import fr.ensimag.ima.pseudocode.RegisterOffset;
import fr.ensimag.ima.pseudocode.instructions.LOAD;

import java.io.PrintStream;

/**
 *
 * @author gl20
 * @date 01/01/2022
 */
public class This extends AbstractExpr {

    private boolean implicit;

    public This(boolean bool) {
        this.implicit = bool;
    }


    @Override
    public Type verifyExpr(DecacCompiler compiler, EnvironmentExp localEnv,
            ClassDefinition currentClass) throws ContextualError {

        if(currentClass == null){
            throw new ContextualError("Current class must not be null", getLocation());
        }
        setType(currentClass.getType());
        return currentClass.getType();
    }


    @Override
    public void decompile(IndentPrintStream s) {
        s.print("this");
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
        if(this.implicit){
            return "";
        }
        return "this";
    }

    @Override
    protected void codeGenSelect(DecacCompiler compiler) {
        GPRegister register = compiler.getData().getFreeRegister(compiler);
        compiler.addInstruction(new LOAD(new RegisterOffset(-2, Register.LB), register));
        compiler.getData().setLastUsedRegister(register);
    }

    @Override
    protected void codeGenInst(DecacCompiler compiler) {
        GPRegister register = compiler.getData().getFreeRegister(compiler);
        compiler.addInstruction(new LOAD(new RegisterOffset(-2, Register.LB), register));
        compiler.getData().setLastUsedRegister(register);
    }

}

