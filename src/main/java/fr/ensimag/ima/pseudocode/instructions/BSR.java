package fr.ensimag.ima.pseudocode.instructions;

import fr.ensimag.ima.pseudocode.DVal;
import fr.ensimag.ima.pseudocode.Label;
import fr.ensimag.ima.pseudocode.LabelOperand;
import fr.ensimag.ima.pseudocode.UnaryInstruction;

/**
 * <p>BSR class.</p>
 *
 * @author Ensimag
 * @date 01/01/2022
 * @version $Id: $Id
 */
public class BSR extends UnaryInstruction {

    /**
     * <p>Constructor for BSR.</p>
     *
     * @param operand a {@link fr.ensimag.ima.pseudocode.DVal} object
     */
    public BSR(DVal operand) {
        super(operand);
    }
    
    /**
     * <p>Constructor for BSR.</p>
     *
     * @param target a {@link fr.ensimag.ima.pseudocode.Label} object
     */
    public BSR(Label target) {
        super(new LabelOperand(target));
    }

}
