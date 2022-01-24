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
 */
public class Assign extends AbstractBinaryExpr {

    @Override
    public AbstractLValue getLeftOperand() {
        // The cast succeeds by construction, as the leftOperand has been set
        // as an AbstractLValue by the constructor.
        return (AbstractLValue)super.getLeftOperand();
    }

    public Assign(AbstractLValue leftOperand, AbstractExpr rightOperand) {
        super(leftOperand, rightOperand);
    }

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


    @Override
    protected String getOperatorName() {
        return "=";
    }

    @Override
    protected void codeGenInst(DecacCompiler compiler) {
        getRightOperand().codeGenInst(compiler);
        GPRegister register = compiler.getData().getLastUsedRegister();
        getLeftOperand().codeGenAssign(compiler, register);
    }

}
