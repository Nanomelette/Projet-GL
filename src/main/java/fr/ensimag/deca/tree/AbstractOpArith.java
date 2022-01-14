package fr.ensimag.deca.tree;

import fr.ensimag.deca.context.Type;
import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.ClassDefinition;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.context.EnvironmentExp;

/**
 * Arithmetic binary operations (+, -, /, ...)
 * 
 * @author gl20
 * @date 01/01/2022
 */
public abstract class AbstractOpArith extends AbstractBinaryExpr {

    public AbstractOpArith(AbstractExpr leftOperand, AbstractExpr rightOperand) {
        super(leftOperand, rightOperand);
    }

    @Override
    public Type verifyExpr(DecacCompiler compiler, EnvironmentExp localEnv,
            ClassDefinition currentClass) throws ContextualError {
            Type type1 = this.getLeftOperand().verifyExpr(compiler, localEnv, currentClass);
            Type type2 = this.getRightOperand().verifyExpr(compiler, localEnv, currentClass);
            if ((!type1.isInt() && !type1.isFloat()) || (!type2.isInt() && !type2.isFloat())) {
                throw new ContextualError("NotIntAndNotFloatType", getLocation());
            }
            else {
                if (type1.isFloat()) {
                    setType(type1);
                    return type1;
                } else {
                    setType(type2);
                    return type2;
                }
            }
    }
}
