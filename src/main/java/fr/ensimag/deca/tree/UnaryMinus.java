package fr.ensimag.deca.tree;

import fr.ensimag.deca.context.Type;
import fr.ensimag.ima.pseudocode.DVal;
import fr.ensimag.ima.pseudocode.Label;
import fr.ensimag.ima.pseudocode.instructions.BOV;
import fr.ensimag.ima.pseudocode.instructions.OPP;
import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.ClassDefinition;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.context.EnvironmentExp;

/**
 * Operator unary "-"
 *
 * @author gl20
 * @date 01/01/2022
 * @version $Id: $Id
 */
public class UnaryMinus extends AbstractUnaryExpr {

    /**
     * <p>Constructor for UnaryMinus.</p>
     *
     * @param operand a {@link fr.ensimag.deca.tree.AbstractExpr} object
     */
    public UnaryMinus(AbstractExpr operand) {
        super(operand);
    }

    /** {@inheritDoc} */
    @Override
    public Type verifyExpr(DecacCompiler compiler, EnvironmentExp localEnv,
            ClassDefinition currentClass) throws ContextualError {
                Type type = this.getOperand().verifyExpr(compiler, localEnv, currentClass);
                if (type.isInt() || type.isFloat()) {
                    this.setType(type);
                    return type;
                } else {
                    throw new ContextualError("NotIntOrFloatType", getLocation());
                }
    }


    /** {@inheritDoc} */
    @Override
    protected String getOperatorName() {
        return "-";
    }

    /** {@inheritDoc} */
    @Override
    protected void codeGenInst(DecacCompiler compiler) {
        super.codeGenInst(compiler);
        DVal val = this.getDVal();
        if (val != null) {
            compiler.addInstruction(new OPP(val, op));
        } else {
            compiler.addInstruction(new OPP(op, op));
        }
        if (!(compiler.getCompilerOptions().getNoCheck()) && getType().isFloat()) {
            compiler.addInstruction(new BOV(new Label("overflow_error")));
        }
        compiler.getData().setLastUsedRegister(op);
    }

}
