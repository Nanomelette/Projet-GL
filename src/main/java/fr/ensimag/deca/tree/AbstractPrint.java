package fr.ensimag.deca.tree;

import fr.ensimag.deca.context.Type;
import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.ClassDefinition;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.context.EnvironmentExp;
import fr.ensimag.deca.tools.IndentPrintStream;
import java.io.PrintStream;
import java.util.Iterator;

import org.apache.commons.lang.Validate;

/**
 * Print statement (print, println, ...).
 *
 * @author gl20
 * @date 01/01/2022
 * @version $Id: $Id
 */
public abstract class AbstractPrint extends AbstractInst {

    private boolean printHex;
    private ListExpr arguments = new ListExpr();
    
    abstract String getSuffix();

    /**
     * <p>Constructor for AbstractPrint.</p>
     *
     * @param printHex a boolean
     * @param arguments a {@link fr.ensimag.deca.tree.ListExpr} object
     */
    public AbstractPrint(boolean printHex, ListExpr arguments) {
        Validate.notNull(arguments);
        this.arguments = arguments;
        this.printHex = printHex;
    }

    /**
     * <p>Getter for the field <code>arguments</code>.</p>
     *
     * @return a {@link fr.ensimag.deca.tree.ListExpr} object
     */
    public ListExpr getArguments() {
        return arguments;
    }

    /** {@inheritDoc} */
    @Override
    protected void verifyInst(DecacCompiler compiler, EnvironmentExp localEnv,
            ClassDefinition currentClass, Type returnType)
            throws ContextualError {
        for (AbstractExpr expr : arguments.getList()){
			Type newType = expr.verifyExpr(compiler, localEnv, currentClass) ;
			if (newType.isString() || newType.isInt() || newType.isFloat()){
				expr.setType(newType);
			}
			else { 
				throw new ContextualError("Print contextual Error", expr.getLocation()) ;
			}
		}
    }

    /** {@inheritDoc} */
    @Override
    protected void codeGenInst(DecacCompiler compiler) {
        for (AbstractExpr a : getArguments().getList()) {
            a.codeGenPrint(compiler, printHex);
        }
    }

    private boolean getPrintHex() {
        return printHex;
    }

    /** {@inheritDoc} */
    @Override
    public void decompile(IndentPrintStream s) {
        s.print("print");
    	s.print(getSuffix()) ;
    	if (this.getPrintHex() == true ) {
            s.print("x");  
        }
    	s.print("(");
        Iterator<AbstractExpr> it = getArguments().getList().iterator();
        if (it.hasNext()) {
            it.next().decompile(s);
        }
        while (it.hasNext()) {
            s.print(", ");
            it.next().decompile(s);
        }
    	s.print(");");
    }

    /** {@inheritDoc} */
    @Override
    protected void iterChildren(TreeFunction f) {
        for (AbstractExpr i : arguments.getList()) {
            i.iter(f);
        }
    }

    /** {@inheritDoc} */
    @Override
    protected void prettyPrintChildren(PrintStream s, String prefix) {
        arguments.prettyPrint(s, prefix, true);
    }

}
