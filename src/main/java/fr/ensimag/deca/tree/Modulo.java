package fr.ensimag.deca.tree;

import fr.ensimag.deca.context.Type;
import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.ClassDefinition;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.context.EnvironmentExp;

/**
 *
 * @author gl20
 * @date 01/01/2022
 */
public class Modulo extends AbstractOpArith {

    public Modulo(AbstractExpr leftOperand, AbstractExpr rightOperand) {
        super(leftOperand, rightOperand);
    }

    @Override
    public Type verifyExpr(DecacCompiler compiler, EnvironmentExp localEnv,
            ClassDefinition currentClass) throws ContextualError {
                Type type1 = this.getLeftOperand().verifyExpr(compiler, localEnv, currentClass);
                Type type2 = this.getRightOperand().verifyExpr(compiler, localEnv, currentClass);
                if (type1.isInt() && type2.isInt()) {
                    return type1;
                } else {
                    throw new ContextualError("NotBooleanType", getLocation());
                }
    }


    @Override
    protected String getOperatorName() {
        return "%";
    }

}
