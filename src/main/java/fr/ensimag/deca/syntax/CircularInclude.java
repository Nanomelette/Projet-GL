package fr.ensimag.deca.syntax;

import org.antlr.v4.runtime.IntStream;

/**
 * Exception raised when a chain of #include is circular, i.e. a file
 * includes a file which has already been included.
 *
 * @author gl20
 * @date 01/01/2022
 * @version $Id: $Id
 */
public class CircularInclude extends DecaRecognitionException {
    private static final long serialVersionUID = -3517868082633812254L;
    private final String name;

    /**
     * <p>Constructor for CircularInclude.</p>
     *
     * @param name a {@link java.lang.String} object
     * @param recognizer a {@link fr.ensimag.deca.syntax.AbstractDecaLexer} object
     * @param input a {@link org.antlr.v4.runtime.IntStream} object
     */
    public CircularInclude(String name, AbstractDecaLexer recognizer, IntStream input) {
        super(recognizer, input);
        this.name = name;
    }

    /** {@inheritDoc} */
    @Override
    public String getMessage() {
        return "Circular include for file " + name;
    }
}
