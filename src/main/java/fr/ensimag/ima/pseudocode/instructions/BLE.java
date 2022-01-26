package fr.ensimag.ima.pseudocode.instructions;

import fr.ensimag.ima.pseudocode.BranchInstruction;
import fr.ensimag.ima.pseudocode.Label;

/**
 * <p>BLE class.</p>
 *
 * @author Ensimag
 * @date 01/01/2022
 * @version $Id: $Id
 */
public class BLE extends BranchInstruction {

    /**
     * <p>Constructor for BLE.</p>
     *
     * @param op a {@link fr.ensimag.ima.pseudocode.Label} object
     */
    public BLE(Label op) {
        super(op);
    }

}
