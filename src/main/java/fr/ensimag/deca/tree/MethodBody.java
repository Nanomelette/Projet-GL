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

public class MethodBody extends AbstractMethodBody{
    private ListDeclVar var;
    private ListInst inst;

    public MethodBody(ListDeclVar listDeclVar, ListInst listInst){
        this.var = listDeclVar;
        this.inst = listInst;
    }

    @Override
    public int getNbrVarMethodBody() {
        return var.size();
    }

    public Type verifyExpr(DecacCompiler compiler, EnvironmentExp localEnv, ClassDefinition currentClass)
            throws ContextualError {
        return null;
    }

    @Override
    public void decompile(IndentPrintStream s) {
        s.println("{");
        s.indent(); 
        var.decompile(s);
        inst.decompile(s);
        s.unindent();
        s.println("}");
    }

    @Override
    protected void prettyPrintChildren(PrintStream s, String prefix) {
        var.prettyPrint(s,prefix,false);
        inst.prettyPrint(s,prefix,true);
    }

    @Override
    protected void iterChildren(TreeFunction f) {
        var.iter(f);
        inst.iter(f);    
    }

    @Override
    protected void verifyMethodBody(DecacCompiler compiler, EnvironmentExp localEnv, ClassDefinition currentClass, Type returnType) throws ContextualError {
        this.var.verifyListDeclVariable(compiler, localEnv, currentClass);
        this.inst.verifyListInst(compiler, localEnv, currentClass, returnType);
    }

    @Override
    protected void codeGenMethodBody(DecacCompiler compiler) {
        var.codeGenListDeclVarLoc(compiler);
        inst.codeGenListInst(compiler);
    }

    @Override
    public void verifyClassBody(DecacCompiler compiler, EnvironmentExp members, EnvironmentExp envExpParams,
            AbstractIdentifier class1, Type return1) throws ContextualError {
        // TODO Auto-generated method stub
        
    }

    @Override
    protected void codeGenSaveRestore(DecacCompiler compiler) {
        // Restauration des registres
        if (compiler.getData().getNumberOfUsedRegister() > 1) {
            compiler.addComment("Restauration des registres");
        }
        compiler.getData().popUsedRegisters(compiler);
        compiler.getData().setLastUsedRegister(Register.R0);
        compiler.addInstruction(new RTS());

        // Sauvegarde des registres
        compiler.getData().pushUsedRegisters(compiler);
        if (compiler.getData().getNumberOfUsedRegister() > 1) {
            compiler.addCommentAtFirst("Sauvegarde des registres");
        }
    }
 
}
