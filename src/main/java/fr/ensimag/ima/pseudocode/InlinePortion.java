package fr.ensimag.ima.pseudocode;

import java.io.PrintStream;

/**
 * Portion of IMA assembly code to be dumped verbatim into the
 * generated code.
 *
 * @author Ensimag
 * @date 01/01/2022
 * @version $Id: $Id
 */
public class InlinePortion extends AbstractLine {
    private final String asmCode;
    
    /**
     * <p>Constructor for InlinePortion.</p>
     *
     * @param asmCode a {@link java.lang.String} object
     */
    public InlinePortion(String asmCode) {
        super();
        this.asmCode = asmCode;
    }
    
    @Override
    void display(PrintStream s) {
        s.println(asmCode);
    }

}
