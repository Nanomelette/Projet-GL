package fr.ensimag.ima.pseudocode.instructions;

import fr.ensimag.ima.pseudocode.BranchInstruction;
import fr.ensimag.ima.pseudocode.Label;

/**
 * <p>BLT class.</p>
 *
 * @author Ensimag
 * @date 01/01/2022
 * @version $Id: $Id
 */
public class BLT extends BranchInstruction {

    /**
     * <p>Constructor for BLT.</p>
     *
     * @param op a {@link fr.ensimag.ima.pseudocode.Label} object
     */
    public BLT(Label op) {
        super(op);
    }

}
