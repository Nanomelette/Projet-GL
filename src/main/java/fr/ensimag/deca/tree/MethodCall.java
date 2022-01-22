package fr.ensimag.deca.tree;

import java.io.PrintStream;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.ClassDefinition;
import fr.ensimag.deca.context.ClassType;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.context.EnvironmentExp;
import fr.ensimag.deca.context.ExpDefinition;
import fr.ensimag.deca.context.MethodDefinition;
import fr.ensimag.deca.context.Signature;
import fr.ensimag.deca.context.Type;
import fr.ensimag.deca.context.ClassType;
import fr.ensimag.deca.tools.IndentPrintStream;
import fr.ensimag.deca.tools.SymbolTable.Symbol;

public class MethodCall extends AbstractExpr {

    private AbstractExpr obj;
    private AbstractIdentifier meth;
    private ListExpr param;

    public MethodCall(AbstractExpr expr, AbstractIdentifier method, ListExpr listExpr){
        this.obj = expr;
        this.meth = method;
        this.param = listExpr;
    }

    @Override
    public Type verifyExpr(DecacCompiler compiler, EnvironmentExp localEnv, ClassDefinition currentClass)
            throws ContextualError {
        Type type = this.obj.verifyExpr(compiler, localEnv, currentClass);
        ClassType classType = (ClassType) type ;
        ClassDefinition classDef = classType.getDefinition();
        EnvironmentExp env = classDef.getMembers();
        MethodDefinition methodDef = (MethodDefinition) env.getDictionnary().get(this.meth.getName());

        this.meth.setType(methodDef.getType());
        this.meth.setDefinition(methodDef);
        this.setType(methodDef.getType());
        Signature sig = this.param.verifyListExp(compiler, localEnv, currentClass);
        if (!methodDef.getSignature().sameSignature(sig)) {
            throw new ContextualError(this.meth.getName()+": unmatched signature", this.getLocation());
        }
        return methodDef.getType();
    }

    @Override
    public void decompile(IndentPrintStream s) {
        if( this.obj != null){
            this.obj.decompile(s);
            s.print(".");
        }
        this.meth.decompile(s);
        s.print("(");
        this.param.decompile(s);
        s.print(")");
    }

    @Override
    protected void prettyPrintChildren(PrintStream s, String prefix) {
        obj.prettyPrint(s, prefix,false);
        meth.prettyPrint(s, prefix, false);
        param.prettyPrint(s, prefix, true);
    }

    @Override
    protected void iterChildren(TreeFunction f) {
        obj.iter(f);
        meth.iter(f);
        for (AbstractExpr i : param.getList()) {
            i.iter(f);
        }
        
    }
    
}
