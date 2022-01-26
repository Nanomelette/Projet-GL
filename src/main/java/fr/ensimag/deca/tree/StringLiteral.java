package fr.ensimag.deca.tree;

import fr.ensimag.deca.context.Type;
import fr.ensimag.deca.context.StringType;
import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.ClassDefinition;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.context.EnvironmentExp;
import fr.ensimag.deca.tools.IndentPrintStream;
import fr.ensimag.ima.pseudocode.ImmediateString;
import fr.ensimag.ima.pseudocode.instructions.WSTR;
import java.io.PrintStream;
import org.apache.commons.lang.Validate;

// import org.apache.log4j.Logger;

/**
 * String literal
 *
 * @author gl20
 * @date 01/01/2022
 * @version $Id: $Id
 */
public class StringLiteral extends AbstractStringLiteral {

    // private static final Logger LOG = Logger.getLogger(StringLiteral.class);

    /** {@inheritDoc} */
    @Override
    public String getValue() {
        return value;
    }

    private String value;

    /**
     * <p>Constructor for StringLiteral.</p>
     *
     * @param value a {@link java.lang.String} object
     */
    public StringLiteral(String value) {
        Validate.notNull(value);
        this.value = value;
    }

    /** {@inheritDoc} */
    @Override
    public Type verifyExpr(DecacCompiler compiler, EnvironmentExp localEnv,
            ClassDefinition currentClass) throws ContextualError {
            Type type = new StringType(compiler.getSymbolTable().create("String"));
            this.setType(type);
            return type;
    }

    /** {@inheritDoc} */
    @Override
    protected void codeGenPrint(DecacCompiler compiler, boolean printHex) {
        compiler.addInstruction(new WSTR(new ImmediateString(value)));
    }

    /** {@inheritDoc} */
    @Override
    public void decompile(IndentPrintStream s) {
        s.print("\"" + getValue() + "\"");
    }

    /** {@inheritDoc} */
    @Override
    protected void iterChildren(TreeFunction f) {
        // leaf node => nothing to do
    }

    /** {@inheritDoc} */
    @Override
    protected void prettyPrintChildren(PrintStream s, String prefix) {
        // leaf node => nothing to do
    }
    
    @Override
    String prettyPrintNode() {
        return "StringLiteral (" + value + ")";
    }

}
