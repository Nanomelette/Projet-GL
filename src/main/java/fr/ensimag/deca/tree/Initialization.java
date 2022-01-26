package fr.ensimag.deca.tree;

import fr.ensimag.deca.context.Type;
import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.ClassDefinition;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.context.EnvironmentExp;
import fr.ensimag.deca.tools.IndentPrintStream;
import fr.ensimag.ima.pseudocode.DAddr;
import fr.ensimag.ima.pseudocode.Register;
import fr.ensimag.ima.pseudocode.instructions.LOAD;
import fr.ensimag.ima.pseudocode.instructions.STORE;

import java.io.PrintStream;
import org.apache.commons.lang.Validate;

/**
 * Initialisation of a field/variable
 *
 * @author gl20
 * @date 01/01/2022
 * @version $Id: $Id
 */
public class Initialization extends AbstractInitialization {

    /**
     * <p>Getter for the field <code>expression</code>.</p>
     *
     * @return a {@link fr.ensimag.deca.tree.AbstractExpr} object
     */
    public AbstractExpr getExpression() {
        return expression;
    }

    private AbstractExpr expression;

    /**
     * <p>Setter for the field <code>expression</code>.</p>
     *
     * @param expression a {@link fr.ensimag.deca.tree.AbstractExpr} object
     */
    public void setExpression(AbstractExpr expression) {
        Validate.notNull(expression);
        this.expression = expression;
    }

    /**
     * <p>Constructor for Initialization.</p>
     *
     * @param expression a {@link fr.ensimag.deca.tree.AbstractExpr} object
     */
    public Initialization(AbstractExpr expression) {
        Validate.notNull(expression);
        this.expression = expression;
    }

    /** {@inheritDoc} */
    @Override
    protected void verifyInitialization(DecacCompiler compiler, Type t,
            EnvironmentExp localEnv, ClassDefinition currentClass)
            throws ContextualError {
        this.setExpression(this.expression.verifyRValue(compiler, localEnv, currentClass, t));
        if(this.expression.getType() == null && t.isClass()){
            this.expression.setType(t);
        }
    }


    /** {@inheritDoc} */
    @Override
    public void decompile(IndentPrintStream s) {
        if(this.getExpression()!=null){
            s.print(" = ");
            this.getExpression().decompile(s);
        }
    }

    /** {@inheritDoc} */
    @Override
    protected
    void iterChildren(TreeFunction f) {
        expression.iter(f);
    }

    /** {@inheritDoc} */
    @Override
    protected void prettyPrintChildren(PrintStream s, String prefix) {
        expression.prettyPrint(s, prefix, false);
    }

    /** {@inheritDoc} */
    @Override
    protected void codeGenInitField(DecacCompiler compiler, Type type) {
        expression.codeGenInst(compiler);
        compiler.addInstruction(new LOAD(compiler.getData().getLastUsedRegister(), Register.R0));
    }

    /** {@inheritDoc} */
    protected void codeGenInitVar(DecacCompiler compiler, DAddr address, Type type) {
        expression.codeGenInst(compiler);
        compiler.addInstruction(new STORE(compiler.getData().getLastUsedRegister(), address));
    };
}
