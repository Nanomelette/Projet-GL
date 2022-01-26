package fr.ensimag.deca.tree;


/**
 * <p>Abstract AbstractOpExactCmp class.</p>
 *
 * @author gl20
 * @date 01/01/2022
 * @version $Id: $Id
 */
public abstract class AbstractOpExactCmp extends AbstractOpCmp {

    /**
     * <p>Constructor for AbstractOpExactCmp.</p>
     *
     * @param leftOperand a {@link fr.ensimag.deca.tree.AbstractExpr} object
     * @param rightOperand a {@link fr.ensimag.deca.tree.AbstractExpr} object
     */
    public AbstractOpExactCmp(AbstractExpr leftOperand, AbstractExpr rightOperand) {
        super(leftOperand, rightOperand);
    }
}
