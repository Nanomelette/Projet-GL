package fr.ensimag.deca.tree;
import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.ima.pseudocode.instructions.SUB;


/**
 * @author gl20
 * @date 01/01/2022
 */
public class Minus extends AbstractOpArith {
    public Minus(AbstractExpr leftOperand, AbstractExpr rightOperand) {
        super(leftOperand, rightOperand);
    }


    @Override
    protected String getOperatorName() {
        return "-";
    }

    @Override
    protected void codeGenInst(DecacCompiler compiler) {
        super.codeGenInst(compiler);
        // TODO : vérifier l'ordre de op1 et op2, si ça fait 
        // op1 - op2 ou op2 - op1.
        compiler.addInstruction(new SUB(op1, op2));
        compiler.getMemory().setRegisterPointer(op2.getNumber());
    }
}
