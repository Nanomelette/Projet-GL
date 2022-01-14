package fr.ensimag.deca.tree;
// import org.apache.log4j.Logger;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.ima.pseudocode.instructions.MUL;



/**
 * @author gl20
 * @date 01/01/2022
 */
public class Multiply extends AbstractOpArith {
    // private static final Logger LOG = Logger.getLogger(Multiply.class);

    public Multiply(AbstractExpr leftOperand, AbstractExpr rightOperand) {
        super(leftOperand, rightOperand);
    }


    @Override
    protected String getOperatorName() {
        return "*";
    }

    @Override
    protected void codeGenInst(DecacCompiler compiler) {
        super.codeGenInst(compiler);
        // <mnemo(op)> <dval(e2)> Rn
        compiler.addInstruction(new MUL(op1, op2));
        compiler.getData().setLastUsedRegister(op2);
    }
    
}
