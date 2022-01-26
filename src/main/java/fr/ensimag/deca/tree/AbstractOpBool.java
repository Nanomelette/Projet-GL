package fr.ensimag.deca.tree;

import fr.ensimag.deca.context.Type;
import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.ClassDefinition;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.context.EnvironmentExp;

/**
 * <p>Abstract AbstractOpBool class.</p>
 *
 * @author gl20
 * @date 01/01/2022
 * @version $Id: $Id
 */
public abstract class AbstractOpBool extends AbstractBinaryExpr {

    /**
     * <p>Constructor for AbstractOpBool.</p>
     *
     * @param leftOperand a {@link fr.ensimag.deca.tree.AbstractExpr} object
     * @param rightOperand a {@link fr.ensimag.deca.tree.AbstractExpr} object
     */
    public AbstractOpBool(AbstractExpr leftOperand, AbstractExpr rightOperand) {
        super(leftOperand, rightOperand);
    }

    /** {@inheritDoc} */
    @Override
    public Type verifyExpr(DecacCompiler compiler, EnvironmentExp localEnv,
            ClassDefinition currentClass) throws ContextualError {
                Type type1 = this.getLeftOperand().verifyExpr(compiler, localEnv, currentClass);
                Type type2 = this.getRightOperand().verifyExpr(compiler, localEnv, currentClass);
                if (type1.isBoolean() && type2.isBoolean()) {
                    setType(type1);
                    return type1;
                } else {
                    throw new ContextualError("NotBooleanType", getLocation());
                }
    }

}
