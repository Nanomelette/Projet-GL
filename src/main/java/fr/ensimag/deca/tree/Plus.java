package fr.ensimag.deca.tree;
import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.ima.pseudocode.Label;
import fr.ensimag.ima.pseudocode.instructions.ADD;
import fr.ensimag.ima.pseudocode.instructions.BOV;

import org.apache.log4j.Logger;

/**
 * Operator "+"
 *
 * @author gl20
 * @date 01/01/2022
 * @version $Id: $Id
 */
public class Plus extends AbstractOpArith {
    private static final Logger LOG = Logger.getLogger(Plus.class);
    /**
     * <p>Constructor for Plus.</p>
     *
     * @param leftOperand a {@link fr.ensimag.deca.tree.AbstractExpr} object
     * @param rightOperand a {@link fr.ensimag.deca.tree.AbstractExpr} object
     */
    public Plus(AbstractExpr leftOperand, AbstractExpr rightOperand) {
        super(leftOperand, rightOperand);
    }
 

    /** {@inheritDoc} */
    @Override
    protected String getOperatorName() {
        return "+";
    }

    /** {@inheritDoc} */
    @Override
    protected void codeGenInst(DecacCompiler compiler) {
        super.codeGenInst(compiler);
        // <mnemo(op)> <dval(e2)> Rn
        LOG.debug("    Used : " + compiler.getData().getLastUsedRegister());
        compiler.addInstruction(new ADD(op1, op2));
        if (!(compiler.getCompilerOptions().getNoCheck()) && getType().isFloat()) {
            compiler.addInstruction(new BOV(new Label("overflow_error")));
        }
        compiler.getData().setLastUsedRegister(op2);
    }
}
