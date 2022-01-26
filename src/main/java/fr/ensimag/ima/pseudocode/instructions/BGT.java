package fr.ensimag.ima.pseudocode.instructions;

import fr.ensimag.ima.pseudocode.BranchInstruction;
import fr.ensimag.ima.pseudocode.Label;

/**
 * <p>BGT class.</p>
 *
 * @author Ensimag
 * @date 01/01/2022
 * @version $Id: $Id
 */
public class BGT extends BranchInstruction {

    /**
     * <p>Constructor for BGT.</p>
     *
     * @param op a {@link fr.ensimag.ima.pseudocode.Label} object
     */
    public BGT(Label op) {
        super(op);
    }

}
