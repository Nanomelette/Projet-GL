package fr.ensimag.deca.tree;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.ima.pseudocode.Label;
import fr.ensimag.ima.pseudocode.instructions.BOV;
import fr.ensimag.ima.pseudocode.instructions.DIV;
import fr.ensimag.ima.pseudocode.instructions.QUO;

/**
 *
 * @author gl20
 * @date 01/01/2022
 */
public class Divide extends AbstractOpArith {
    public Divide(AbstractExpr leftOperand, AbstractExpr rightOperand) {
        super(leftOperand, rightOperand);
    }


    @Override
    protected String getOperatorName() {
        return "/";
    }

    @Override
    protected void codeGenInst(DecacCompiler compiler) {
        super.codeGenInst(compiler);
        if (this.getType().isFloat()) {
            compiler.addInstruction(new DIV(op1, op2)); // Cas des flottants
        } else {
            compiler.addInstruction(new QUO(op1, op2)); // Cas des entiers
        }
        if (!(compiler.getCompilerOptions().getNoCheck())) {
            compiler.addInstruction(new BOV(new Label("zero_division")));
        }
        compiler.getData().setLastUsedRegister(op2);
    }

}
