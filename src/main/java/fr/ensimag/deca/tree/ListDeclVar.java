package fr.ensimag.deca.tree;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.ClassDefinition;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.context.EnvironmentExp;
import fr.ensimag.deca.tools.IndentPrintStream;

import org.apache.commons.lang.Validate;
import org.apache.log4j.Logger;

/**
 * List of declarations (e.g. int x; float y,z).
 * 
 * @author gl20
 * @date 01/01/2022
 */
public class ListDeclVar extends TreeList<AbstractDeclVar> {
    private static final Logger LOG = Logger.getLogger(ListDeclVar.class);

    @Override
    public void decompile(IndentPrintStream s) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    /**
     * Implements non-terminal "list_decl_var" of [SyntaxeContextuelle] in pass 3
     * @param compiler contains the "env_types" attribute
     * @param localEnv 
     *   its "parentEnvironment" corresponds to "env_exp_sup" attribute
     *   in precondition, its "current" dictionary corresponds to 
     *      the "env_exp" attribute
     *   in postcondition, its "current" dictionary corresponds to 
     *      the "env_exp_r" attribute
     * @param currentClass 
     *          corresponds to "class" attribute (null in the main bloc).
     */    
    public EnvironmentExp verifyListDeclVariable(DecacCompiler compiler, EnvironmentExp localEnv,
            ClassDefinition currentClass) throws ContextualError {
                for (AbstractDeclVar v : getList()) {
                    //LOG.debug(localEnv.getClass().getName().toString());
                    v.verifyDeclVar(compiler,localEnv,currentClass);
                }
                return localEnv;
    }


}
