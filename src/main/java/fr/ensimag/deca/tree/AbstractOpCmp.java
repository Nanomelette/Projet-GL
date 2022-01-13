package fr.ensimag.deca.tree;

import fr.ensimag.deca.context.Type;
import fr.ensimag.deca.context.BooleanType;
import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.ClassDefinition;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.context.EnvironmentExp;

/**
 *
 * @author gl20
 * @date 01/01/2022
 */
public abstract class AbstractOpCmp extends AbstractBinaryExpr {

    public AbstractOpCmp(AbstractExpr leftOperand, AbstractExpr rightOperand) {
        super(leftOperand, rightOperand);
    }

    @Override
    public Type verifyExpr(DecacCompiler compiler, EnvironmentExp localEnv,
            ClassDefinition currentClass) throws ContextualError {
                Type type1 = this.getLeftOperand().verifyExpr(compiler, localEnv, currentClass);
                Type type2 = this.getRightOperand().verifyExpr(compiler, localEnv, currentClass);
                if ((!type1.isInt() && !type1.isFloat()) || (!type2.isInt() && !type2.isFloat())) {
                    if (getOperatorName() == "==" || getOperatorName() == "!=") {
                        if (type1.isBoolean() && type2.isBoolean()) {
                            return new BooleanType(compiler.getSymbolTable().create("boolean"));
                        } else {
                            throw new ContextualError("NotIntAndNotFloatType", getLocation());
                        }
                    } else {
                        throw new ContextualError("NotIntAndNotFloatType", getLocation());
                    }
                } else {
                    return new BooleanType(compiler.getSymbolTable().create("boolean"));
                }
    }


}
