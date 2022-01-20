package fr.ensimag.deca.tree;

import java.io.PrintStream;
import java.util.Iterator;

import org.apache.log4j.DailyRollingFileAppender;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.codegen.Data;
import fr.ensimag.deca.context.ClassDefinition;
import fr.ensimag.deca.context.ClassType;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.context.EnvironmentExp;
import fr.ensimag.deca.context.MethodDefinition;
import fr.ensimag.deca.context.Signature;
import fr.ensimag.deca.context.Type;
import fr.ensimag.deca.tools.IndentPrintStream;
import fr.ensimag.deca.tools.SymbolTable.Symbol;
import fr.ensimag.ima.pseudocode.DAddr;
import fr.ensimag.ima.pseudocode.GPRegister;
import fr.ensimag.ima.pseudocode.Instruction;
import fr.ensimag.ima.pseudocode.Label;
import fr.ensimag.ima.pseudocode.NullOperand;
import fr.ensimag.ima.pseudocode.Register;
import fr.ensimag.ima.pseudocode.RegisterOffset;
import fr.ensimag.ima.pseudocode.instructions.ADDSP;
import fr.ensimag.ima.pseudocode.instructions.BEQ;
import fr.ensimag.ima.pseudocode.instructions.BSR;
import fr.ensimag.ima.pseudocode.instructions.CMP;
import fr.ensimag.ima.pseudocode.instructions.LOAD;
import fr.ensimag.ima.pseudocode.instructions.STORE;
import fr.ensimag.ima.pseudocode.instructions.SUBSP;

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
        System.out.println(this.obj);
        Identifier a = (Identifier) this.obj;
        System.out.println(a.getName());
        System.out.println(compiler.GetEnvExp().getDictionnary().keySet().toString());
        int i=0;
        while(i < compiler.GetEnvExp().getDictionnary().keySet().size()){
            if(a.getName().equals(((Symbol)compiler.GetEnvExp().getDictionnary().keySet().toArray()[i]).getName())){
                break;
            }
            i++;
        }
        if ((i == compiler.GetEnvExp().getDictionnary().keySet().size())) {
            throw new ContextualError("identifier not defined", getLocation());
        }
        Symbol symb = (Symbol)compiler.GetEnvExp().getDictionnary().keySet().toArray()[i];
        if (compiler.GetEnvExp().get(symb) != null) {
            setType(compiler.GetEnvExp().get(symb).getType());
            return compiler.GetEnvExp().get(symb).getType();
        }
        throw new UnsupportedOperationException("Invalid expression");
    }

    @Override
    public void decompile(IndentPrintStream s) {
        if( this.obj != null){
            s.print(this.obj.decompile());
            s.print(".");
        }
        s.print(this.meth.decompile());
        s.print("(");
        s.print(this.param.decompile());
        s.print(")");
    }

    @Override
    protected void prettyPrintChildren(PrintStream s, String prefix) {
        // TODO Auto-generated method stub
    }

    @Override
    protected void iterChildren(TreeFunction f) {
        // TODO Auto-generated method stub
        
    }

    @Override
    protected void codeGenInst(DecacCompiler compiler) {
        Data data = compiler.getData();
        obj.codeGenInst(compiler);
        
        // On calcul l'emplacement de la méthode
        Register addresseClassRegister = data.getLastUsedRegister();
        DAddr addr = new RegisterOffset(0, addresseClassRegister);

        // On reserve la place pour les parametres
        compiler.addInstruction(new ADDSP(param.size() + 1));
        GPRegister register = data.getFreeRegister(compiler);
        data.decrementFreeStoragePointer();

        // On empile le parametre implicite
        compiler.addInstruction(new LOAD(addr, register));
        compiler.addInstruction(new STORE(register, new RegisterOffset(0, Register.SP)));

        // On empile les autres parametres
        param.codeGenListExpr(compiler);
        
        register = data.getFreeRegister(compiler);
        compiler.addInstruction(new LOAD(new RegisterOffset(0, Register.SP), register));
        compiler.addInstruction(new CMP(new NullOperand(), register));
        compiler.addInstruction(new BEQ(new Label("null_dereference")));
        // On recupere l'adresse de la table des méthodes
        compiler.addInstruction(new LOAD(new RegisterOffset(0, register), register));
        // On saute à la méthode
        compiler.addInstruction(new BSR(new RegisterOffset(meth.getMethodDefinition().getIndex(), register)));
        // On dépile nos parametres
        compiler.addInstruction(new SUBSP(param.size() + 1));
        // Le resultat de la méthode est stocké dans R0
        data.setLastUsedRegister(Register.R0);
    }
}
