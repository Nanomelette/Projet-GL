package fr.ensimag.deca.tree;

import java.io.PrintStream;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.ClassDefinition;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.context.EnvironmentExp;
import fr.ensimag.deca.context.Type;
import fr.ensimag.deca.tools.IndentPrintStream;

public class Cast extends AbstractExpr {
    private AbstractIdentifier type;
    private AbstractExpr e;

    public Cast (AbstractIdentifier type, AbstractExpr e){
        this.type = type;
        this.e = e;
    }

    @Override
    public Type verifyExpr(DecacCompiler compiler, EnvironmentExp localEnv, ClassDefinition currentClass)
            throws ContextualError {

        if(type.getName().getName().equals("Null")){
            throw new ContextualError("cannot cast null type", getLocation());
        }
        if(type.getName().getName().equals("void")){
            throw new ContextualError("cannot cast void type", getLocation());
        }
        Type type = this.type.verifyType(compiler);
        Type expressionType = this.e.verifyExpr(compiler, localEnv, currentClass);
        if(type.isVoid()){
            throw new ContextualError("cannot cast void type", getLocation());
        }
        if(type.isNull()){
            throw new ContextualError("cannot cast null type", getLocation());
        }
        if(type == expressionType){
            return type;
        }
        if(compiler.assignCompatible(compiler, type, expressionType)!= null){
            return compiler.assignCompatible(compiler, type, expressionType);
        }
        if(compiler.assignCompatible(compiler, expressionType, type)!= null){
            return compiler.assignCompatible(compiler, expressionType, type);
        }
        throw new ContextualError("impossible cast", getLocation());
    }

    @Override
    public void decompile(IndentPrintStream s) {
        s.print("(");
        type.decompile(s);
        s.print(") (");
        e.decompile(s);
        s.print(")");        
    }

    @Override
    protected void prettyPrintChildren(PrintStream s, String prefix) {
        type.prettyPrint(s, prefix, false);
        e.prettyPrint(s, prefix, false);
        
    }

    @Override
    protected void iterChildren(TreeFunction f) {
        type.iter(f);
        e.iter(f);
    }
    
}
