package fr.ensimag.deca.tree;

import java.io.PrintStream;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.ClassDefinition;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.context.EnvironmentExp;
import fr.ensimag.deca.context.Type;
import fr.ensimag.deca.tools.IndentPrintStream;
import fr.ensimag.ima.pseudocode.InlinePortion;
import fr.ensimag.ima.pseudocode.instructions.RTS;


public class MethodBodyAsm extends AbstractMethodBody {
    private StringLiteral code;

    public MethodBodyAsm(StringLiteral string){
        this.code = string;
    }


    @Override
    public void decompile(IndentPrintStream s) {
        s.print("asm(");
        code.decompile(s);
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
    protected void verifyMethodBody(DecacCompiler compiler, EnvironmentExp localEnv, ClassDefinition currentClass, Type returnType) throws ContextualError {
        
        this.code.setType(this.code.verifyExpr(compiler, localEnv, currentClass));
        
    }


    @Override
    protected void codeGenMethodBody(DecacCompiler compiler) {
        compiler.add(new InlinePortion("    "+code.getValue().replace("\\\"", "\"")));
    }


    @Override
    public void verifyClassBody(DecacCompiler compiler, EnvironmentExp members, EnvironmentExp envExpParams,
            AbstractIdentifier class1, Type return1) throws ContextualError {
        // Nothing to do
        
    }


    @Override
    protected void codeGenSaveRestore(DecacCompiler compiler) {
        compiler.addInstruction(new RTS());
    }

    public int getNbrVarMethodBody() {
        return 0;
    }

    
}
