package fr.ensimag.deca.tree;
// import org.apache.log4j.Logger;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.ima.pseudocode.Label;
import fr.ensimag.ima.pseudocode.instructions.BOV;
import fr.ensimag.ima.pseudocode.instructions.MUL;



/**
 * Operator "*"
 *
 * @author gl20
 * @date 01/01/2022
 * @version $Id: $Id
 */
public class Multiply extends AbstractOpArith {

    /**
     * <p>Constructor for Multiply.</p>
     *
     * @param leftOperand a {@link fr.ensimag.deca.tree.AbstractExpr} object
     * @param rightOperand a {@link fr.ensimag.deca.tree.AbstractExpr} object
     */
    public Multiply(AbstractExpr leftOperand, AbstractExpr rightOperand) {
        super(leftOperand, rightOperand);
    }


    /** {@inheritDoc} */
    @Override
    protected String getOperatorName() {
        return "*";
    }

    /** {@inheritDoc} */
    @Override
    protected void codeGenInst(DecacCompiler compiler) {
        super.codeGenInst(compiler);
        // <mnemo(op)> <dval(e2)> Rn
        compiler.addInstruction(new MUL(op1, op2));
        if (!(compiler.getCompilerOptions().getNoCheck()) && getType().isFloat()) {
            compiler.addInstruction(new BOV(new Label("overflow_error")));
        }
        compiler.getData().setLastUsedRegister(op2);
    }
    
}
