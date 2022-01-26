package fr.ensimag.ima.pseudocode.instructions;

import fr.ensimag.ima.pseudocode.BranchInstruction;
import fr.ensimag.ima.pseudocode.Label;

/**
 * <p>BGE class.</p>
 *
 * @author Ensimag
 * @date 01/01/2022
 * @version $Id: $Id
 */
public class BGE extends BranchInstruction {

    /**
     * <p>Constructor for BGE.</p>
     *
     * @param op a {@link fr.ensimag.ima.pseudocode.Label} object
     */
    public BGE(Label op) {
        super(op);
    }

}
