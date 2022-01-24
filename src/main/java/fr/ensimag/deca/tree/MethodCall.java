package fr.ensimag.deca.tree;

import java.io.PrintStream;
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
import fr.ensimag.ima.pseudocode.DAddr;
import fr.ensimag.ima.pseudocode.GPRegister;
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
        Type type = this.obj.verifyExpr(compiler, localEnv, currentClass);
        if (!type.isClass()) {
            throw new ContextualError("object isn't a class", this.getLocation());
        }
        ClassType classType = (ClassType) type ;
        ClassDefinition classDef = classType.getDefinition();
        EnvironmentExp env = classDef.getMembers();
        MethodDefinition methodDef = (MethodDefinition) env.getDictionnary().get(this.meth.getName());
        if (methodDef == null) {
            throw new ContextualError(this.meth.getName()+" not defined", this.getLocation());
        }
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
        // if( this.obj != null){
        //     this.obj.decompile(s);
        //     s.print(".");
        // } else {
        //     s.print("this.");
        // }
        obj.decompile(s);
        s.print(".");
        meth.decompile(s);
        s.print("(");
        param.decompile(s);
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

    @Override
    protected void codeGenInst(DecacCompiler compiler) {
        Data data = compiler.getData();
        obj.codeGenInst(compiler);
        
        // On calcul l'emplacement de la méthode
        Register addresseClassRegister = data.getLastUsedRegister();
        // DAddr addr = new RegisterOffset(0, addresseClassRegister);

        // On reserve la place pour les parametres
        compiler.addInstruction(new ADDSP(param.size() + 1));
        GPRegister register = data.getFreeRegister(compiler);
        // data.decrementFreeStoragePointer();

        // On empile le parametre implicite
        compiler.addInstruction(new LOAD(addresseClassRegister, register));
        compiler.addInstruction(new STORE(register, new RegisterOffset(0, Register.SP)));

        // On empile les autres parametres
        param.codeGenListExpr(compiler);
        
        // register = data.getFreeRegister(compiler);
        compiler.addInstruction(new LOAD(new RegisterOffset(0, Register.SP), register));
        if (!(compiler.getCompilerOptions().getNoCheck())) {
            compiler.addInstruction(new CMP(new NullOperand(), register));
            compiler.addInstruction(new BEQ(new Label("null_dereference")));
        }
        // On recupere l'adresse de la table des méthodes
        compiler.addInstruction(new LOAD(new RegisterOffset(0, register), register));
        // On saute à la méthode
        compiler.addInstruction(new BSR(new RegisterOffset(meth.getMethodDefinition().getIndex(), register)));
        // On dépile nos parametres
        compiler.addInstruction(new SUBSP(param.size() + 1));
        // Le resultat de la méthode est stocké dans R0
        data.setLastUsedRegister(Register.R0);
    }

    @Override
    protected void codeGenSelect(DecacCompiler compiler) {
        codeGenInst(compiler);
    }
}
