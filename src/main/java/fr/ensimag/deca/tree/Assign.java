package fr.ensimag.deca.tree;

import fr.ensimag.deca.context.Type;
import fr.ensimag.ima.pseudocode.GPRegister;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.ClassDefinition;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.context.EnvironmentExp;

/**
 * Assignment, i.e. lvalue = expr.
 *
 * @author gl20
 * @date 01/01/2022
 * @version $Id: $Id
 */
public class Assign extends AbstractBinaryExpr {

    /** {@inheritDoc} */
    @Override
    public AbstractLValue getLeftOperand() {
        // The cast succeeds by construction, as the leftOperand has been set
        // as an AbstractLValue by the constructor.
        return (AbstractLValue)super.getLeftOperand();
    }

    /**
     * <p>Constructor for Assign.</p>
     *
     * @param leftOperand a {@link fr.ensimag.deca.tree.AbstractLValue} object
     * @param rightOperand a {@link fr.ensimag.deca.tree.AbstractExpr} object
     */
    public Assign(AbstractLValue leftOperand, AbstractExpr rightOperand) {
        super(leftOperand, rightOperand);
    }

    /** {@inheritDoc} */
    @Override
    public Type verifyExpr(DecacCompiler compiler, EnvironmentExp localEnv,
            ClassDefinition currentClass) throws ContextualError {
        Type type = this.getLeftOperand().verifyExpr(compiler, localEnv, currentClass);
        if (type.isNull()) {
            throw new ContextualError("Can't assign a null value", this.getLocation());
        }
        this.setRightOperand(this.getRightOperand().verifyRValue(compiler, localEnv, currentClass, type));
        this.setType(type);
        return getType();
    }


    /** {@inheritDoc} */
    @Override
    protected String getOperatorName() {
        return "=";
    }

    /** {@inheritDoc} */
    @Override
    protected void codeGenInst(DecacCompiler compiler) {
        getRightOperand().codeGenInst(compiler);
        GPRegister register = compiler.getData().getLastUsedRegister();
        getLeftOperand().codeGenAssign(compiler, register);
    }

}
