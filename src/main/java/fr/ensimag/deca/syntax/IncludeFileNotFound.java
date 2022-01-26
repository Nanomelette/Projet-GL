package fr.ensimag.deca.syntax;

import org.antlr.v4.runtime.IntStream;

/**
 * Exception raised when a #include is found for a file that cannot be found or opened.
 *
 * @author gl20
 * @date 01/01/2022
 * @version $Id: $Id
 */
public class IncludeFileNotFound extends DecaRecognitionException {
    private final String name;

    /**
     * <p>Constructor for IncludeFileNotFound.</p>
     *
     * @param name a {@link java.lang.String} object
     * @param recognizer a {@link fr.ensimag.deca.syntax.AbstractDecaLexer} object
     * @param input a {@link org.antlr.v4.runtime.IntStream} object
     */
    public IncludeFileNotFound(String name, AbstractDecaLexer recognizer, IntStream input) {
        super(recognizer, input);
        this.name = name;
    }
    
    /**
     * <p>Getter for the field <code>name</code>.</p>
     *
     * @return a {@link java.lang.String} object
     */
    public String getName() {
        return name;
    }

    /** {@inheritDoc} */
    @Override
    public String getMessage() {
        return name + ": include file not found";
    }
    
    private static final long serialVersionUID = -8541996188279897766L;

}
