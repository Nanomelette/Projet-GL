package fr.ensimag.deca.tree;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.ima.pseudocode.GPRegister;
import fr.ensimag.ima.pseudocode.Label;
import fr.ensimag.ima.pseudocode.instructions.BEQ;
import fr.ensimag.ima.pseudocode.instructions.BRA;
import fr.ensimag.ima.pseudocode.instructions.CMP;
import fr.ensimag.ima.pseudocode.instructions.LOAD;

/**
 * <p>And class.</p>
 *
 * @author gl20
 * @date 01/01/2022
 * @version $Id: $Id
 */
public class And extends AbstractOpBool {

    /**
     * <p>Constructor for And.</p>
     *
     * @param leftOperand a {@link fr.ensimag.deca.tree.AbstractExpr} object
     * @param rightOperand a {@link fr.ensimag.deca.tree.AbstractExpr} object
     */
    public And(AbstractExpr leftOperand, AbstractExpr rightOperand) {
        super(leftOperand, rightOperand);
    }

    /** {@inheritDoc} */
    @Override
    protected String getOperatorName() {
        return "&&";
    }

    /** {@inheritDoc} */
    @Override
    protected void codeGenInst(DecacCompiler compiler) {
        super.codeGenInst(compiler);
        Label False = new Label("False"+compiler.getNLabel());
        Label End = new Label("End"+compiler.getNLabel());
        GPRegister register = compiler.getData().getFreeRegister(compiler);
        compiler.addInstruction(new LOAD(op1, register));
        compiler.addInstruction(new CMP(0, op2));
        compiler.addInstruction(new BEQ(False));
        compiler.addInstruction(new CMP(0, register));
        compiler.addInstruction(new BEQ(False));
        compiler.addInstruction(new LOAD(1, op2));
        compiler.addInstruction(new BRA(End));
        compiler.addLabel(False);
        compiler.addInstruction(new LOAD(0, op2));
        compiler.getData().setLastUsedRegister(op2);
        compiler.addLabel(End);
        compiler.incrNLabel();
    }

    /** {@inheritDoc} */
    @Override
    protected void codeBoolean(boolean b, Label E, DecacCompiler compiler) {
        if (b) {
            Label E_fin = new Label("E_fin."+compiler.getNLabel());
            getLeftOperand().codeBoolean(false, E_fin, compiler);
            getRightOperand().codeBoolean(true, E, compiler);
            compiler.addLabel(E_fin);
        } else {
            getLeftOperand().codeBoolean(false, E, compiler);
            getRightOperand().codeBoolean(false, E, compiler);
        }
    }
}
