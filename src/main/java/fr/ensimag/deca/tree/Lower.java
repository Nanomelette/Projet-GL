package fr.ensimag.deca.tree;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.ima.pseudocode.Label;
import fr.ensimag.ima.pseudocode.instructions.BGE;
import fr.ensimag.ima.pseudocode.instructions.BLT;
import fr.ensimag.ima.pseudocode.instructions.CMP;
import fr.ensimag.ima.pseudocode.instructions.SLT;

/**
 * Operator "x < y"
 *
 * @author gl20
 * @date 01/01/2022
 * @version $Id: $Id
 */
public class Lower extends AbstractOpIneq {

    /**
     * <p>Constructor for Lower.</p>
     *
     * @param leftOperand a {@link fr.ensimag.deca.tree.AbstractExpr} object
     * @param rightOperand a {@link fr.ensimag.deca.tree.AbstractExpr} object
     */
    public Lower(AbstractExpr leftOperand, AbstractExpr rightOperand) {
        super(leftOperand, rightOperand);
    }


    /** {@inheritDoc} */
    @Override
    protected String getOperatorName() {
        return "<";
    }

    /** {@inheritDoc} */
    @Override
    protected void codeGenInst(DecacCompiler compiler) {
        super.codeGenInst(compiler);
        compiler.addInstruction(new CMP(op1, op2));
        compiler.addInstruction(new SLT(op2));
        compiler.getData().setLastUsedRegister(op2);
    }

    /** {@inheritDoc} */
    @Override
    protected void codeBoolean(boolean b, Label E, DecacCompiler compiler) {
        super.codeGenInst(compiler);
        compiler.addInstruction(new CMP(op1, op2));
        compiler.addInstruction(b ? new BLT(E) : new BGE(E));
    }
}
