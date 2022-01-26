package fr.ensimag.ima.pseudocode;

/**
 * <p>BranchInstruction class.</p>
 *
 * @author Ensimag
 * @date 01/01/2022
 * @version $Id: $Id
 */
public class BranchInstruction extends UnaryInstruction {

    /**
     * <p>Constructor for BranchInstruction.</p>
     *
     * @param op a {@link fr.ensimag.ima.pseudocode.Label} object
     */
    public BranchInstruction(Label op) {
        super(op);
    }

}
