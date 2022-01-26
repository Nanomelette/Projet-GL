package fr.ensimag.deca.tree;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.ima.pseudocode.GPRegister;
import fr.ensimag.ima.pseudocode.Label;
import fr.ensimag.ima.pseudocode.instructions.BEQ;
import fr.ensimag.ima.pseudocode.instructions.BRA;
import fr.ensimag.ima.pseudocode.instructions.CMP;
import fr.ensimag.ima.pseudocode.instructions.LOAD;

/**
 * Operator "||"
 *
 * @author gl20
 * @date 01/01/2022
 * @version $Id: $Id
 */
public class Or extends AbstractOpBool {

    /**
     * <p>Constructor for Or.</p>
     *
     * @param leftOperand a {@link fr.ensimag.deca.tree.AbstractExpr} object
     * @param rightOperand a {@link fr.ensimag.deca.tree.AbstractExpr} object
     */
    public Or(AbstractExpr leftOperand, AbstractExpr rightOperand) {
        super(leftOperand, rightOperand);
    }

    /** {@inheritDoc} */
    @Override
    protected String getOperatorName() {
        return "||";
    }

    /** {@inheritDoc} */
    @Override
    protected void codeBoolean(boolean b, Label E, DecacCompiler compiler) {
        Not expr = new Not(new And(new Not(getLeftOperand()), new Not(getRightOperand())));
        expr.codeBoolean(b, E, compiler);
    }

    /** {@inheritDoc} */
    @Override
    protected void codeGenInst(DecacCompiler compiler) {
        super.codeGenInst(compiler);
        Label True = new Label("True"+compiler.getNLabel());
        Label End = new Label("End"+compiler.getNLabel());
        compiler.incrNLabel();
        GPRegister register = compiler.getData().getFreeRegister(compiler);
        compiler.addInstruction(new LOAD(op1, register));
        compiler.addInstruction(new CMP(1, op2));
        compiler.addInstruction(new BEQ(True));
        compiler.addInstruction(new CMP(1, register));
        compiler.addInstruction(new BEQ(True));
        compiler.addInstruction(new LOAD(0, op2));
        compiler.addInstruction(new BRA(End));
        compiler.addLabel(True);
        compiler.addInstruction(new LOAD(1, op2));
        compiler.getData().setLastUsedRegister(op2);
        compiler.addLabel(End);
    }
}
