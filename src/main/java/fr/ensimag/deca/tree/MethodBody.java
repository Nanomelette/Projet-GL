package fr.ensimag.deca.tree;

import java.io.PrintStream;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.ClassDefinition;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.context.EnvironmentExp;
import fr.ensimag.deca.context.Type;
import fr.ensimag.deca.tools.IndentPrintStream;

public class MethodBody extends AbstractMethodBody{
    private ListDeclVar var;
    private ListInst inst;

    public MethodBody(ListDeclVar listDeclVar, ListInst listInst){
        this.var = listDeclVar;
        this.inst = listInst;
    }

    public int getNbrVarMethodBody() {
        return var.size();
    }

    public Type verifyExpr(DecacCompiler compiler, EnvironmentExp localEnv, ClassDefinition currentClass)
            throws ContextualError {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void decompile(IndentPrintStream s) {
        s.print("{"); 
        s.print(var.decompile());
        s.print(inst.decompile());
        s.print("}");
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
    protected void verifyMethodBody(DecacCompiler compiler) throws ContextualError {
    }

    @Override
    protected void codeGenMethodBody(DecacCompiler compiler) {
        var.codeGenListDeclVarLoc(compiler);
        inst.codeGenListInst(compiler);
    }
 
}
