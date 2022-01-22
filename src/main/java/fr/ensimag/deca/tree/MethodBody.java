package fr.ensimag.deca.tree;

import java.io.PrintStream;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.ClassDefinition;
import fr.ensimag.deca.context.ClassType;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.context.EnvironmentExp;
import fr.ensimag.deca.context.Type;
import fr.ensimag.deca.tools.IndentPrintStream;
import fr.ensimag.deca.tools.SymbolTable.Symbol;

public class MethodBody extends AbstractMethodBody{
    private ListDeclVar var;
    private ListInst inst;

    public MethodBody(ListDeclVar listDeclVar, ListInst listInst){
        this.var = listDeclVar;
        this.inst = listInst;
    }
    @Override
    public void decompile(IndentPrintStream s) {
        s.print("{"); 
        var.decompile(s);
        inst.decompile(s);
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
    protected void verifyMethodBody(DecacCompiler compiler, EnvironmentExp localEnv, ClassDefinition currentClass, Type returnType) throws ContextualError {
        this.var.verifyListDeclVariable(compiler, localEnv, currentClass);
        this.inst.verifyListInst(compiler, localEnv, currentClass, returnType);
    }
 
}
