package fr.ensimag.deca.tree;

import fr.ensimag.deca.context.Type;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.ClassDefinition;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.context.EnvironmentExp;
import fr.ensimag.deca.tools.IndentPrintStream;

/**
 * List of inst
 *
 * @author gl20
 * @date 01/01/2022
 * @version $Id: $Id
 */
public class ListInst extends TreeList<AbstractInst> {

    // private static final Logger LOG = Logger.getLogger(ListInst.class);

    /**
     * Implements non-terminal "list_inst" of [SyntaxeContextuelle] in pass 3
     *
     * @param compiler contains "env_types" attribute
     * @param localEnv corresponds to "env_exp" attribute
     * @param returnType
     *          corresponds to "return" attribute (void in the main bloc).
     * @throws fr.ensimag.deca.context.ContextualError if any.
     */
    public void verifyListInst(DecacCompiler compiler, EnvironmentExp localEnv,
            ClassDefinition currentClass, Type returnType)
            throws ContextualError {
        for (AbstractInst i : getList()){
    		i.verifyInst(compiler, localEnv, currentClass, returnType);
    	}
    }

    /**
     * <p>codeGenListInst.</p>
     *
     * @param compiler a {@link fr.ensimag.deca.DecacCompiler} object
     */
    public void codeGenListInst(DecacCompiler compiler) {
        for (AbstractInst i : getList()) {
            i.codeGenInst(compiler);
            compiler.getData().restoreData();
        }
    }

    /** {@inheritDoc} */
    @Override
    public void decompile(IndentPrintStream s) {
        for (AbstractInst i : getList()) {
            i.decompileInst(s);
            s.println();
        }
    }
}
