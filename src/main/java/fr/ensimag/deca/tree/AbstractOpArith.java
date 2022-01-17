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
                if (type1.isInt()&& type2.isInt()) {
                    setType(type1);
                    return type1;
                } else {
                    if (type1.isInt()) {
                        ConvFloat cf = new ConvFloat(this.getLeftOperand());
                        Type typeCf = cf.verifyExpr(compiler, localEnv, currentClass);
                        cf.setType(typeCf);
                        this.setLeftOperand(cf);
                        setType(type2);
                        return type2;
                    }
                    if (type2.isInt()) {
                        ConvFloat cf = new ConvFloat(this.getRightOperand());
                        Type typeCf = cf.verifyExpr(compiler, localEnv, currentClass);
                        cf.setType(typeCf);
                        this.setRightOperand(cf);
                    }
                    setType(type1);
                    return type1;
                }
            }
    }
}
