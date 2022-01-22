package fr.ensimag.deca.tree;

import java.io.PrintStream;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.codegen.Data;
import fr.ensimag.deca.context.ClassDefinition;
import fr.ensimag.deca.context.ClassType;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.context.EnvironmentExp;
import fr.ensimag.deca.context.FieldDefinition;
import fr.ensimag.deca.context.Type;
import fr.ensimag.deca.tools.IndentPrintStream;
import fr.ensimag.ima.pseudocode.GPRegister;
import fr.ensimag.ima.pseudocode.Label;
import fr.ensimag.ima.pseudocode.NullOperand;
import fr.ensimag.ima.pseudocode.Register;
import fr.ensimag.ima.pseudocode.RegisterOffset;
import fr.ensimag.ima.pseudocode.instructions.BEQ;
import fr.ensimag.ima.pseudocode.instructions.CMP;
import fr.ensimag.ima.pseudocode.instructions.LOAD;
import fr.ensimag.ima.pseudocode.instructions.STORE;
import net.bytebuddy.asm.Advice.Thrown;

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

        Type type = this.obj.verifyExpr(compiler, localEnv, currentClass);
        ClassType currentType = (ClassType) type;
        ClassType classType;
        if(type.isClass()){
            classType = (ClassType) type;
        }
        else{
            throw new ContextualError("Must be a class", getLocation());
        }
        type = compiler.searchSymbol(type.getName());
        classType = (ClassType) type;
        EnvironmentExp env_exp2 = classType.getDefinition().getMembers();
        if(env_exp2.get(this.field.getName())==null){
            throw new ContextualError("env_exp2 is null", getLocation());
        }
        FieldDefinition fieldDef ;
        if(env_exp2.get(this.field.getName()).isField()){
            fieldDef = (FieldDefinition) env_exp2.get(this.field.getName());
        }
        else{
            throw new ContextualError("Not a field", getLocation());
        }

        // condition : field_ident = { visibility : PROTECTED }

        if(fieldDef.getVisibility().name().equals("PROTECTED")){
            //on verifie les subTypes
            Type protectedType = this.obj.getType();
            ClassType pclassType;
            if(protectedType.isClass()){
                pclassType = (ClassType) protectedType;
            }
            else{
                throw new ContextualError("Type is not a class", getLocation());
            }
            if (classType.isSubClassOf(currentType)){
                if(currentType.isSubClassOf(pclassType)){
                    setType(fieldDef.getType());
                    this.field.setDefinition(fieldDef);
                    this.obj.setType(fieldDef.getType());
                    return fieldDef.getType();
                }
            }
            else {
                throw new ContextualError("current class not of the same class", getLocation());
            }
        }
        
        setType(fieldDef.getType());
        this.field.setDefinition(fieldDef);
        this.obj.setType(fieldDef.getType());
        return fieldDef.getType();


    }

    @Override
    public void decompile(IndentPrintStream s) {
        obj.decompile(s);
        s.print(".");
        field.decompile(s);        
    }

    @Override
    protected void prettyPrintChildren(PrintStream s, String prefix) {
        this.obj.prettyPrint(s, prefix, false);
        this.field.prettyPrint(s, prefix, false);
        
    }

    @Override
    protected void iterChildren(TreeFunction f) {
        this.obj.iter(f);
        this.field.iter(f);
        
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
        compiler.addInstruction(new LOAD(new RegisterOffset(field.getFieldDefinition().getIndex(), objRegister), objRegister));
        
    }

    @Override
    protected void codeGenSelect(DecacCompiler compiler) {
        codeGenInst(compiler);
    }

    @Override
    protected void codeGenAssign(DecacCompiler compiler, Register register) {
        Data data = compiler.getData();
        obj.codeGenSelect(compiler);
        // On met dans objRegister l'adresse de l'obj en partie gauche
        GPRegister objRegister = data.getLastUsedRegister();
        if (!(compiler.getCompilerOptions().getNoCheck())) {
            compiler.addInstruction(new CMP(new NullOperand(), objRegister));
            compiler.addInstruction(new BEQ(new Label("null_dereference")));
        }
        compiler.addInstruction(new STORE(register, new RegisterOffset(field.getFieldDefinition().getIndex(), compiler.getData().getLastUsedRegister())));
    }
    
}
