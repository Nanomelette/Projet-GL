package fr.ensimag.deca.tree;

import java.io.PrintStream;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.ClassDefinition;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.context.EnvironmentExp;
import fr.ensimag.deca.context.Type;
import fr.ensimag.deca.tools.IndentPrintStream;
import fr.ensimag.ima.pseudocode.Register;
import fr.ensimag.ima.pseudocode.instructions.RTS;

/**
 * Implementation of a method body
 *
 * @author oscarmaggiori
 * @version $Id: $Id
 */
public class MethodBody extends AbstractMethodBody{
    private ListDeclVar var;
    private ListInst inst;

    /**
     * <p>Constructor for MethodBody.</p>
     *
     * @param listDeclVar a {@link fr.ensimag.deca.tree.ListDeclVar} object
     * @param listInst a {@link fr.ensimag.deca.tree.ListInst} object
     */
    public MethodBody(ListDeclVar listDeclVar, ListInst listInst){
        this.var = listDeclVar;
        this.inst = listInst;
    }

    /** {@inheritDoc} */
    @Override
    public int getNbrVarMethodBody() {
        return var.size();
    }

    /**
     * <p>verifyExpr.</p>
     *
     * @param compiler a {@link fr.ensimag.deca.DecacCompiler} object
     * @param localEnv a {@link fr.ensimag.deca.context.EnvironmentExp} object
     * @param currentClass a {@link fr.ensimag.deca.context.ClassDefinition} object
     * @return a {@link fr.ensimag.deca.context.Type} object
     * @throws fr.ensimag.deca.context.ContextualError if any.
     */
    public Type verifyExpr(DecacCompiler compiler, EnvironmentExp localEnv, ClassDefinition currentClass)
            throws ContextualError {
        return null;
    }

    /** {@inheritDoc} */
    @Override
    public void decompile(IndentPrintStream s) {
        s.println("{");
        s.indent(); 
        var.decompile(s);
        inst.decompile(s);
        s.unindent();
        s.println("}");
    }

    /** {@inheritDoc} */
    @Override
    protected void prettyPrintChildren(PrintStream s, String prefix) {
        var.prettyPrint(s,prefix,false);
        inst.prettyPrint(s,prefix,true);
    }

    /** {@inheritDoc} */
    @Override
    protected void iterChildren(TreeFunction f) {
        var.iter(f);
        inst.iter(f);    
    }

    /** {@inheritDoc} */
    @Override
    protected void verifyMethodBody(DecacCompiler compiler, EnvironmentExp localEnv, ClassDefinition currentClass, Type returnType) throws ContextualError {
        this.var.verifyListDeclVariable(compiler, localEnv, currentClass);
        this.inst.verifyListInst(compiler, localEnv, currentClass, returnType);
    }

    /** {@inheritDoc} */
    @Override
    protected void codeGenMethodBody(DecacCompiler compiler) {
        var.codeGenListDeclVarLoc(compiler);
        compiler.addComment("Debut des instruction de la methode");
        inst.codeGenListInst(compiler);
    }

    /** {@inheritDoc} */
    @Override
    public void verifyClassBody(DecacCompiler compiler, EnvironmentExp members, EnvironmentExp envExpParams,
            AbstractIdentifier class1, Type return1) throws ContextualError {
        
    }

    /** {@inheritDoc} */
    @Override
    protected void codeGenSaveRestore(DecacCompiler compiler) {
        // Restauration des registres
        if (compiler.getData().getNumberOfUsedRegister() > 0) {
            compiler.addComment("Restauration des registres");
        }
        compiler.getData().popUsedRegisters(compiler);
        compiler.getData().setLastUsedRegister(Register.R0);
        compiler.addInstruction(new RTS());

        // Sauvegarde des registres
        compiler.getData().pushUsedRegisters(compiler);
        if (compiler.getData().getNumberOfUsedRegister() > 0) {
            compiler.addCommentAtFirst("Sauvegarde des registres");
        }
    }
 
}
