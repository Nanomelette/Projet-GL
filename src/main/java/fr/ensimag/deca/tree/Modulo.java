package fr.ensimag.deca.tree;

import fr.ensimag.deca.context.Type;
import fr.ensimag.ima.pseudocode.Label;
import fr.ensimag.ima.pseudocode.instructions.BOV;
import fr.ensimag.ima.pseudocode.instructions.REM;
import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.ClassDefinition;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.context.EnvironmentExp;

/**
 * Operator "%"
 *
 * @author gl20
 * @date 01/01/2022
 * @version $Id: $Id
 */
public class Modulo extends AbstractOpArith {

    /**
     * <p>Constructor for Modulo.</p>
     *
     * @param leftOperand a {@link fr.ensimag.deca.tree.AbstractExpr} object
     * @param rightOperand a {@link fr.ensimag.deca.tree.AbstractExpr} object
     */
    public Modulo(AbstractExpr leftOperand, AbstractExpr rightOperand) {
        super(leftOperand, rightOperand);
    }

    /** {@inheritDoc} */
    @Override
    public Type verifyExpr(DecacCompiler compiler, EnvironmentExp localEnv,
            ClassDefinition currentClass) throws ContextualError {
                Type type1 = this.getLeftOperand().verifyExpr(compiler, localEnv, currentClass);
                Type type2 = this.getRightOperand().verifyExpr(compiler, localEnv, currentClass);
                if (type1.isInt() && type2.isInt()) {
                    this.setType(type1);
                    return type1;
                } else {
                    throw new ContextualError("NotIntType", getLocation());
                }
    }


    /** {@inheritDoc} */
    @Override
    protected String getOperatorName() {
        return "%";
    }

    /** {@inheritDoc} */
    @Override
    protected void codeGenInst(DecacCompiler compiler) {
        super.codeGenInst(compiler);
        compiler.addInstruction(new REM(op1, op2));
        if (!(compiler.getCompilerOptions().getNoCheck())) {
            compiler.addInstruction(new BOV(new Label("zero_division")));
        }
        compiler.getData().setLastUsedRegister(op2);
    }

}
