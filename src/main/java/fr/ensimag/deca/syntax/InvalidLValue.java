package fr.ensimag.deca.syntax;

import org.antlr.v4.runtime.ParserRuleContext;

/**
 * Syntax error for an expression that should be an lvalue (ie that can be
 * assigned), but is not.
 *
 * @author gl20
 * @date 01/01/2022
 * @version $Id: $Id
 */
public class InvalidLValue extends DecaRecognitionException {

    private static final long serialVersionUID = 4670163376041273741L;

    /**
     * <p>Constructor for InvalidLValue.</p>
     *
     * @param recognizer a {@link fr.ensimag.deca.syntax.DecaParser} object
     * @param ctx a {@link org.antlr.v4.runtime.ParserRuleContext} object
     */
    public InvalidLValue(DecaParser recognizer, ParserRuleContext ctx) {
        super(recognizer, ctx);
    }

    /** {@inheritDoc} */
    @Override
    public String getMessage() {
        return "left-hand side of assignment is not an lvalue";
    }
}
