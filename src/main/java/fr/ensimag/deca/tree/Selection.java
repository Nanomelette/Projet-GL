package fr.ensimag.deca.tree;

import java.io.PrintStream;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.codegen.Data;
import fr.ensimag.deca.context.ClassDefinition;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.context.EnvironmentExp;
import fr.ensimag.deca.context.Type;
import fr.ensimag.deca.tools.IndentPrintStream;
import fr.ensimag.ima.pseudocode.GPRegister;
import fr.ensimag.ima.pseudocode.Label;
import fr.ensimag.ima.pseudocode.NullOperand;
import fr.ensimag.ima.pseudocode.instructions.BEQ;
import fr.ensimag.ima.pseudocode.instructions.CMP;

public class Selection extends AbstractLValue{

    private AbstractExpr obj;
    private AbstractIdentifier field;

    public Selection(AbstractExpr obj, AbstractIdentifier field){
        this.obj = obj;
        this.field = field;
    }

    @Override
    public Type verifyExpr(DecacCompiler compiler, EnvironmentExp localEnv, ClassDefinition currentClass)
            throws ContextualError {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void decompile(IndentPrintStream s) {
        s.print(obj.decompile());
        s.print(".");
        s.print(field.decompile());        
    }

    @Override
    protected void prettyPrintChildren(PrintStream s, String prefix) {
        obj.prettyPrint(s, prefix, false);
        field.prettyPrint(s, prefix, false);
        
    }

    @Override
    protected void iterChildren(TreeFunction f) {
        obj.iter(f);
        field.iter(f);
        
    }

    @Override
    protected void codeGenInst(DecacCompiler compiler) {
        Data data = compiler.getData();
        obj.codeGenSelect(compiler);
        // On met dans objRegister l'adresse de l'obj en partie gauche
        GPRegister objRegister = data.getLastUsedRegister();
        compiler.addInstruction(new CMP(new NullOperand(), objRegister));
        compiler.addInstruction(new BEQ(new Label("null_dereference")));
        // On récupère l'offset du champ concerné
        
    }

    @Override
    protected void codeGenSelect(DecacCompiler compiler) {
        Data data = compiler.getData();
        obj.codeGenSelect(compiler);
        // On met dans objRegister l'adresse de l'obj en partie gauche
        GPRegister objRegister = data.getLastUsedRegister();
        compiler.addInstruction(new CMP(new NullOperand(), objRegister));
        compiler.addInstruction(new BEQ(new Label("null_dereference")));
    }

    @Override
    protected void codeGenAssign(DecacCompiler compiler) {
        codeGenInst(compiler);

    }
    
}
