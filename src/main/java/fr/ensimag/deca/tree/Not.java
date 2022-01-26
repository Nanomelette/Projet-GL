package fr.ensimag.deca.tree;

import fr.ensimag.deca.context.Type;
import fr.ensimag.ima.pseudocode.Label;
import fr.ensimag.ima.pseudocode.instructions.CMP;
import fr.ensimag.ima.pseudocode.instructions.SEQ;
import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.ClassDefinition;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.context.EnvironmentExp;

/**
 * Operator "!"
 *
 * @author gl20
 * @date 01/01/2022
 * @version $Id: $Id
 */
public class Not extends AbstractUnaryExpr {

    /**
     * <p>Constructor for Not.</p>
     *
     * @param operand a {@link fr.ensimag.deca.tree.AbstractExpr} object
     */
    public Not(AbstractExpr operand) {
        super(operand);
    }

    /** {@inheritDoc} */
    @Override
    public Type verifyExpr(DecacCompiler compiler, EnvironmentExp localEnv,
            ClassDefinition currentClass) throws ContextualError {
                Type type = this.getOperand().verifyExpr(compiler, localEnv, currentClass);
                if (type.isBoolean()) {
                    this.setType(type);
                    return type;
                } else {
                    throw new ContextualError("NotBooleanType", getLocation());
                }
    }


    /** {@inheritDoc} */
    @Override
    protected String getOperatorName() {
        return "!";
    }

    /** {@inheritDoc} */
    @Override
    protected void codeBoolean(boolean b, Label E, DecacCompiler compiler) {
        getOperand().codeBoolean(!b, E, compiler);
    }

    /** {@inheritDoc} */
    @Override
    protected void codeGenInst(DecacCompiler compiler) {
        super.codeGenInst(compiler);
        compiler.addInstruction(new CMP(0, op));
        compiler.addInstruction(new SEQ(op));
    }
}
