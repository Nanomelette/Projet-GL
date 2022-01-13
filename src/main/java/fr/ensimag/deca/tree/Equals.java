package fr.ensimag.deca.tree;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.ima.pseudocode.Label;
import fr.ensimag.ima.pseudocode.instructions.BEQ;
import fr.ensimag.ima.pseudocode.instructions.CMP;
import fr.ensimag.ima.pseudocode.instructions.SEQ;

/**
 *
 * @author gl20
 * @date 01/01/2022
 */
public class Equals extends AbstractOpExactCmp {

    public Equals(AbstractExpr leftOperand, AbstractExpr rightOperand) {
        super(leftOperand, rightOperand);
    }


    @Override
    protected String getOperatorName() {
        return "==";
    }    
    
    @Override
    protected void codeGenInst(DecacCompiler compiler) {
        super.codeGenInst(compiler);
        compiler.addInstruction(new CMP(op1, op2));
        compiler.addInstruction(new SEQ(op2));
        compiler.getData().setLastUsedRegister(op2);
    }

    @Override
    protected void codeBoolean(boolean b, Label E, DecacCompiler compiler) {
        super.codeGenInst(compiler);
        compiler.addInstruction(new CMP(op1, op2));
        compiler.addInstruction(new BEQ(E));
    }
}
