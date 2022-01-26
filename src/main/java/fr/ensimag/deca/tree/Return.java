package fr.ensimag.deca.tree;

import fr.ensimag.deca.context.Type;
import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.ClassDefinition;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.context.EnvironmentExp;
import fr.ensimag.deca.tools.IndentPrintStream;
import fr.ensimag.ima.pseudocode.Register;
import fr.ensimag.ima.pseudocode.instructions.BRA;
import fr.ensimag.ima.pseudocode.instructions.LOAD;

import java.io.PrintStream;
import org.apache.commons.lang.Validate;

/**
 * Operator "return"
 *
 * @author oscarmaggiori
 * @version $Id: $Id
 */
public class Return extends AbstractInst {

    private AbstractExpr e;

    /**
     * <p>Constructor for Return.</p>
     *
     * @param condition a {@link fr.ensimag.deca.tree.AbstractExpr} object
     */
    public Return(AbstractExpr condition) {
        Validate.notNull(condition);
        this.e = condition;
    }


    /** {@inheritDoc} */
    @Override
    protected void codeGenInst(DecacCompiler compiler) {
        e.codeGenInst(compiler);
        compiler.addComment("return de la méthode");
        compiler.addInstruction(
            new LOAD(
                compiler.getData().getLastUsedRegister(),
                Register.R0
            )
        );
        compiler.addInstruction(
            new BRA(compiler.getData().getLabelReturn())
        );
    }

    /** {@inheritDoc} */
    @Override
    protected void verifyInst(DecacCompiler compiler, EnvironmentExp localEnv,
            ClassDefinition currentClass, Type returnType)
            throws ContextualError {

            if(returnType.isVoid()){
                throw new ContextualError("returned type can't be void", getLocation());
            }
            Type typeE = e.verifyExpr(compiler, localEnv, currentClass);
            Type type = compiler.assignCompatible(compiler, typeE, returnType);
            if (type == null) {
                throw new ContextualError("unexpected return type", e.getLocation());
            }
            e.setType(type);
    }

    /** {@inheritDoc} */
    @Override
    public void decompile(IndentPrintStream s) {
        s.print("return ");
        e.decompile(s);
        s.print(";");
    }

    /** {@inheritDoc} */
    @Override
    protected void iterChildren(TreeFunction f) {
        e.iter(f);

    }

    /** {@inheritDoc} */
    @Override
    protected void prettyPrintChildren(PrintStream s, String prefix) {
        e.prettyPrint(s, prefix, true);
    }

    
}
