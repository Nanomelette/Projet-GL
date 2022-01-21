package fr.ensimag.deca.tree;

import java.io.PrintStream;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.ClassDefinition;
import fr.ensimag.deca.context.ClassType;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.context.EnvironmentExp;
import fr.ensimag.deca.context.MethodDefinition;
import fr.ensimag.deca.context.Signature;
import fr.ensimag.deca.context.StringType;
import fr.ensimag.deca.context.Type;
import fr.ensimag.deca.tools.IndentPrintStream;


public class MethodBodyAsm extends AbstractMethodBody {
    private StringLiteral code;

    public MethodBodyAsm(StringLiteral string){
        this.code = string;
    }


    @Override
    public void decompile(IndentPrintStream s) {
        s.print("asm(");
        code.decompile();
        s.print(");");
    }

    @Override
    protected void prettyPrintChildren(PrintStream s, String prefix) {
        code.prettyPrint(s, prefix, false);
    }


    @Override
    protected void iterChildren(TreeFunction f) {
        code.iter(f);
    }


    @Override
    protected void verifyMethodBody(DecacCompiler compiler) throws ContextualError {
        
        Type type = new StringType(compiler.getSymbolTable().create("String"));
        this.code.setType(type);
        
    }


    @Override
    protected void codeGenMethodBody(DecacCompiler compiler) {
        // TODO : split
        
    }


    @Override
    public void verifyClassBody(DecacCompiler compiler, EnvironmentExp members, EnvironmentExp envExpParams,
            AbstractIdentifier class1, Type return1) throws ContextualError {
        // TODO Auto-generated method stub
        
    }

    
}
