package fr.ensimag.deca.tree;
import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.ima.pseudocode.Label;
import fr.ensimag.ima.pseudocode.instructions.ADD;
import fr.ensimag.ima.pseudocode.instructions.BOV;

import org.apache.log4j.Logger;

/**
 * @author gl20
 * @date 01/01/2022
 */
public class Plus extends AbstractOpArith {
    private static final Logger LOG = Logger.getLogger(Plus.class);
    public Plus(AbstractExpr leftOperand, AbstractExpr rightOperand) {
        super(leftOperand, rightOperand);
    }
 

    @Override
    protected String getOperatorName() {
        return "+";
    }

    @Override
    protected void codeGenInst(DecacCompiler compiler) {
        super.codeGenInst(compiler);
        LOG.debug("    Used : " + compiler.getData().getLastUsedRegister());
        compiler.addInstruction(new ADD(op1, op2));
        if (!(compiler.getCompilerOptions().getNoCheck()) && getType().isFloat()) {
            compiler.addInstruction(new BOV(new Label("overflow_error")));
        }
        compiler.getData().setLastUsedRegister(op2);
    }
}
