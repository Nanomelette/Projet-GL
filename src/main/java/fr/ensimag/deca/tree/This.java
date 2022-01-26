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
 * Operator "this"
 *
 * @author gl20
 * @date 01/01/2022
 * @version $Id: $Id
 */
public class This extends AbstractExpr {

    private boolean implicit;

    /**
     * <p>Constructor for This.</p>
     *
     * @param bool a boolean
     */
    public This(boolean bool) {
        this.implicit = bool;
    }


    /** {@inheritDoc} */
    @Override
    public Type verifyExpr(DecacCompiler compiler, EnvironmentExp localEnv,
            ClassDefinition currentClass) throws ContextualError {

        if(currentClass == null){
            throw new ContextualError("Current class must not be null", getLocation());
        }
        setType(currentClass.getType());
        return currentClass.getType();
    }


    /** {@inheritDoc} */
    @Override
    public void decompile(IndentPrintStream s) {
        s.print("this");
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
        if(this.implicit){
            return "";
        }
        return "this";
    }

    /** {@inheritDoc} */
    @Override
    protected void codeGenSelect(DecacCompiler compiler) {
        GPRegister register = compiler.getData().getFreeRegister(compiler);
        compiler.addInstruction(new LOAD(new RegisterOffset(-2, Register.LB), register));
        compiler.getData().setLastUsedRegister(register);
    }

    /** {@inheritDoc} */
    @Override
    protected void codeGenInst(DecacCompiler compiler) {
        GPRegister register = compiler.getData().getFreeRegister(compiler);
        compiler.addInstruction(new LOAD(new RegisterOffset(-2, Register.LB), register));
        compiler.getData().setLastUsedRegister(register);
    }

}

