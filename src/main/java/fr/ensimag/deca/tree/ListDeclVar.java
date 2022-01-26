package fr.ensimag.deca.tree;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.ClassDefinition;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.context.EnvironmentExp;
import fr.ensimag.deca.tools.IndentPrintStream;
// import org.apache.log4j.Logger;

/**
 * List of declarations (e.g. int x; float y,z).
 *
 * @author gl20
 * @date 01/01/2022
 * @version $Id: $Id
 */
public class ListDeclVar extends TreeList<AbstractDeclVar> {

    /** {@inheritDoc} */
    @Override
    public void decompile(IndentPrintStream s) {
        for (AbstractDeclVar declVar : getList()){
            declVar.decompile(s);
        }
    }

    /**
     * Implements non-terminal "list_decl_var" of [SyntaxeContextuelle] in pass 3
     *
     * @param compiler contains the "env_types" attribute
     * @return a {@link fr.ensimag.deca.context.EnvironmentExp} object
     * @throws fr.ensimag.deca.context.ContextualError if any.
     */
    public EnvironmentExp verifyListDeclVariable(DecacCompiler compiler, EnvironmentExp localEnv,
            ClassDefinition currentClass) throws ContextualError {
                for (AbstractDeclVar v : getList()) {
                    v.verifyDeclVar(compiler,localEnv,currentClass);
                }
                return localEnv;
    }

    /**
     * <p>codeGenListDeclVarGlob.</p>
     *
     * @param compiler a {@link fr.ensimag.deca.DecacCompiler} object
     */
    public void codeGenListDeclVarGlob(DecacCompiler compiler) {
        for (AbstractDeclVar declVar : getList()) {
            declVar.codeGenDeclVarGlob(compiler);
        }
    }
    /**
     * <p>codeGenListDeclVarLoc.</p>
     *
     * @param compiler a {@link fr.ensimag.deca.DecacCompiler} object
     */
    public void codeGenListDeclVarLoc(DecacCompiler compiler) {
        compiler.getData().restorelBOffset();
        for (AbstractDeclVar declVar : getList()) {
            declVar.codeGenDeclVarLoc(compiler);
            compiler.getData().restoreData();
        }
    }


}
