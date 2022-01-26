package fr.ensimag.deca.tree;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.ima.pseudocode.Label;
import fr.ensimag.ima.pseudocode.instructions.BEQ;
import fr.ensimag.ima.pseudocode.instructions.BNE;
import fr.ensimag.ima.pseudocode.instructions.CMP;
import fr.ensimag.ima.pseudocode.instructions.SEQ;

/**
 * Operator "=="
 *
 * @author gl20
 * @date 01/01/2022
 * @version $Id: $Id
 */
public class Equals extends AbstractOpExactCmp {

    /**
     * <p>Constructor for Equals.</p>
     *
     * @param leftOperand a {@link fr.ensimag.deca.tree.AbstractExpr} object
     * @param rightOperand a {@link fr.ensimag.deca.tree.AbstractExpr} object
     */
    public Equals(AbstractExpr leftOperand, AbstractExpr rightOperand) {
        super(leftOperand, rightOperand);
    }


    /** {@inheritDoc} */
    @Override
    protected String getOperatorName() {
        return "==";
    }    
    
    /** {@inheritDoc} */
    @Override
    protected void codeGenInst(DecacCompiler compiler) {
        super.codeGenInst(compiler);
        compiler.addInstruction(new CMP(op1, op2));
        compiler.addInstruction(new SEQ(op2));
        compiler.getData().setLastUsedRegister(op2);
    }

    /** {@inheritDoc} */
    @Override
    protected void codeBoolean(boolean b, Label E, DecacCompiler compiler) {
        super.codeGenInst(compiler);
        compiler.addInstruction(new CMP(op1, op2));
        compiler.addInstruction(b ? new BEQ(E) : new BNE(E));
    }
}
