package fr.ensimag.deca.tree;

import fr.ensimag.deca.context.Type;
import fr.ensimag.deca.context.BooleanType;
import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.ClassDefinition;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.context.EnvironmentExp;

/**
 * <p>Abstract AbstractOpCmp class.</p>
 *
 * @author gl20
 * @date 01/01/2022
 * @version $Id: $Id
 */
public abstract class AbstractOpCmp extends AbstractBinaryExpr {

    /**
     * <p>Constructor for AbstractOpCmp.</p>
     *
     * @param leftOperand a {@link fr.ensimag.deca.tree.AbstractExpr} object
     * @param rightOperand a {@link fr.ensimag.deca.tree.AbstractExpr} object
     */
    public AbstractOpCmp(AbstractExpr leftOperand, AbstractExpr rightOperand) {
        super(leftOperand, rightOperand);
    }

    /** {@inheritDoc} */
    @Override
    public Type verifyExpr(DecacCompiler compiler, EnvironmentExp localEnv,
            ClassDefinition currentClass) throws ContextualError {
                Type type1 = this.getLeftOperand().verifyExpr(compiler, localEnv, currentClass);
                Type type2 = this.getRightOperand().verifyExpr(compiler, localEnv, currentClass);
                if ((!type1.isInt() && !type1.isFloat()) || (!type2.isInt() && !type2.isFloat())) {
                    if (getOperatorName() == "==" || getOperatorName() == "!=") {
                        if (type1.isBoolean() && type2.isBoolean()) {
                            Type type = new BooleanType(compiler.getSymbolTable().create("boolean"));
                            this.setType(type);
                            return type;
                        } else {
                            if ((type1.isNull() || type1.isClass()) && (type2.isNull() || type2.isClass())) {
                                Type type = new BooleanType(compiler.getSymbolTable().create("boolean"));
                                this.setType(type);
                                return type;
                            } else {
                                throw new ContextualError("NotIntAndNotFloatType", getLocation());
                            }
                        }
                    } else {
                        throw new ContextualError("NotIntAndNotFloatType", getLocation());
                    }
                } else {
                    if (type1.isInt() && type2.isFloat()) {
                        ConvFloat cf = new ConvFloat(this.getLeftOperand());
                        Type typeCf = cf.verifyExpr(compiler, localEnv, currentClass);
                        cf.setType(typeCf);
                        this.setLeftOperand(cf);
                    }
                    if (type1.isFloat() && type2.isInt()) {
                        ConvFloat cf = new ConvFloat(this.getRightOperand());
                        Type typeCf = cf.verifyExpr(compiler, localEnv, currentClass);
                        cf.setType(typeCf);
                        this.setRightOperand(cf);
                    }
                    Type type = new BooleanType(compiler.getSymbolTable().create("boolean"));
                    this.setType(type);
                    return type;
                }
    }


}
