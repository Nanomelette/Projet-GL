package fr.ensimag.deca.tree;


import java.io.PrintStream;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.codegen.Data;
import fr.ensimag.deca.context.ClassDefinition;
import fr.ensimag.deca.context.ClassType;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.context.EnvironmentExp;
import fr.ensimag.deca.context.Type;
import fr.ensimag.deca.tools.IndentPrintStream;
import fr.ensimag.ima.pseudocode.DAddr;
import fr.ensimag.ima.pseudocode.GPRegister;
import fr.ensimag.ima.pseudocode.Label;
import fr.ensimag.ima.pseudocode.Register;
import fr.ensimag.ima.pseudocode.RegisterOffset;
import fr.ensimag.ima.pseudocode.instructions.BOV;
import fr.ensimag.ima.pseudocode.instructions.BSR;
import fr.ensimag.ima.pseudocode.instructions.LEA;
import fr.ensimag.ima.pseudocode.instructions.NEW;
import fr.ensimag.ima.pseudocode.instructions.POP;
import fr.ensimag.ima.pseudocode.instructions.PUSH;
import fr.ensimag.ima.pseudocode.instructions.STORE;
public class New extends AbstractExpr {

    private AbstractIdentifier class_;

    public New(AbstractIdentifier class_){
        this.class_ = class_;
    }

    @Override
    public Type verifyExpr(DecacCompiler compiler, EnvironmentExp localEnv, ClassDefinition currentClass)
            throws ContextualError {
        Type type = compiler.searchSymbol(this.class_.getName());
        if(type == null){
            throw new ContextualError("class "+this.class_.getName().getName()+" not defined", getLocation());
        }
        if(!type.isClass()){
            throw new ContextualError("the type of the class must be a class", getLocation());
        }
        ClassType classType = (ClassType) type;
        class_.setType(classType);
        class_.setDefinition(classType.getDefinition());
        setType(classType);
        return classType;
    }

    @Override
    public void decompile(IndentPrintStream s) {     
        s.print("new ");
        class_.decompile(s);
        s.print("()");
    }

    @Override
    protected void prettyPrintChildren(PrintStream s, String prefix) {
        class_.prettyPrint(s, prefix, false);        
    }

    @Override
    protected void iterChildren(TreeFunction f) {
        class_.iter(f);
        
    }
    
    @Override
    protected void codeGenInst(DecacCompiler compiler) {
        Label heap_overflow = new Label("heap_overflow");
        Data data = compiler.getData();
        GPRegister register = data.getFreeRegister(compiler);
        ClassDefinition classDef = class_.getClassDefinition();
        int nbOfField = classDef.getNumberOfFields();
        while (classDef.getSuperClass() != null) {
            classDef = classDef.getSuperClass();
            nbOfField += classDef.getNumberOfFields();
        }
        compiler.addInstruction(new NEW(nbOfField+1, register));
        if (!(compiler.getCompilerOptions().getNoCheck())) {
            compiler.addInstruction(new BOV(heap_overflow));
        }
        DAddr addr = class_.getClassDefinition().getAddressVTable(); 
        compiler.addInstruction(new LEA(addr, Register.R0));
        compiler.addInstruction(new STORE(Register.R0, new RegisterOffset(0, register)));
        compiler.addInstruction(new PUSH(register));
        compiler.addInstruction(new BSR(new Label("init."+class_.getName().getName())));
        compiler.addInstruction(new POP(register));
        data.setLastUsedRegister(register);
    }
}
