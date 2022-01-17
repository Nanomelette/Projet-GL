package fr.ensimag.deca.tree;

import fr.ensimag.deca.context.Type;
import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.ClassDefinition;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.context.EnvironmentExp;

/**
 * @author gl20
 * @date 01/01/2022
 */
public class UnaryMinus extends AbstractUnaryExpr {

    public UnaryMinus(AbstractExpr operand) {
        super(operand);
    }

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


    @Override
    protected String getOperatorName() {
        return "-";
    }

}
