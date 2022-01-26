package fr.ensimag.ima.pseudocode.instructions;

import fr.ensimag.ima.pseudocode.BranchInstruction;
import fr.ensimag.ima.pseudocode.Label;

/**
 * <p>BRA class.</p>
 *
 * @author Ensimag
 * @date 01/01/2022
 * @version $Id: $Id
 */
public class BRA extends BranchInstruction {

    /**
     * <p>Constructor for BRA.</p>
     *
     * @param op a {@link fr.ensimag.ima.pseudocode.Label} object
     */
    public BRA(Label op) {
        super(op);
    }

}
