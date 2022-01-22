package fr.ensimag.deca.tree;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.ima.pseudocode.GPRegister;
import fr.ensimag.ima.pseudocode.ImmediateFloat;
import fr.ensimag.ima.pseudocode.Label;
import fr.ensimag.ima.pseudocode.instructions.BEQ;
import fr.ensimag.ima.pseudocode.instructions.BOV;
import fr.ensimag.ima.pseudocode.instructions.CMP;
import fr.ensimag.ima.pseudocode.instructions.DIV;
import fr.ensimag.ima.pseudocode.instructions.LOAD;
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
        GPRegister freeRegister = compiler.getData().getFreeRegister(compiler);
        // Division par zéro
        compiler.addInstruction(new LOAD(op1, freeRegister));
        if (this.getType().isInt()) {
            compiler.addInstruction(new CMP(0, freeRegister));
            compiler.addInstruction(new BEQ(new Label("zero_division")));
        } else {
            compiler.addInstruction(new CMP(new ImmediateFloat(0), freeRegister));
            compiler.addInstruction(new BOV(new Label("overflow_error")));
        }

        if (this.getType().isFloat()) {
            compiler.addInstruction(new DIV(op1, op2)); // Cas des flottants
        } else {
            compiler.addInstruction(new QUO(op1, op2)); // Cas des entiers
        }

        // Débordement arithmétique
        if (!(compiler.getCompilerOptions().getNoCheck())) {
            compiler.addInstruction(new BOV(new Label("overflow_error")));
        }
        compiler.getData().setLastUsedRegister(op2);
    }

}
