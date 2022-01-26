package fr.ensimag.deca.tree;

/**
 * Print operation
 *
 * @author gl20
 * @date 01/01/2022
 * @version $Id: $Id
 */
public class Print extends AbstractPrint {
    /**
     * <p>Constructor for Print.</p>
     *
     * @param arguments arguments passed to the print(...) statement.
     * @param printHex if true, then float should be displayed as hexadecimal (printx)
     */
    public Print(boolean printHex, ListExpr arguments) {
        super(printHex, arguments);
    }

    @Override
    String getSuffix() {
        return "";
    }
}
