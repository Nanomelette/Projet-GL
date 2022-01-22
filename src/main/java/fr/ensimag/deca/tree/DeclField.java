package fr.ensimag.deca.tree;

import fr.ensimag.deca.context.Type;
import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.ClassDefinition;
import fr.ensimag.deca.context.ClassType;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.context.EnvironmentExp;
import fr.ensimag.deca.context.EnvironmentType;
import fr.ensimag.deca.context.ExpDefinition;
import fr.ensimag.deca.context.FieldDefinition;
import fr.ensimag.deca.tools.IndentPrintStream;
import fr.ensimag.ima.pseudocode.Register;
import fr.ensimag.ima.pseudocode.RegisterOffset;
import fr.ensimag.ima.pseudocode.instructions.LOAD;
import fr.ensimag.ima.pseudocode.instructions.STORE;
import fr.ensimag.deca.tools.SymbolTable.Symbol;
import fr.ensimag.deca.tree.Visibility;

import java.io.PrintStream;
import java.io.ObjectInputStream.GetField;

import org.apache.commons.lang.Validate;

public class DeclField extends AbstractDeclField {

    private Visibility v;
    private AbstractIdentifier type;
    private AbstractIdentifier field;
    private AbstractInitialization init;
    

    public DeclField(Visibility v, AbstractIdentifier type, AbstractIdentifier field, AbstractInitialization init){
        Validate.notNull(v);
        Validate.notNull(type);
        Validate.notNull(field);
        Validate.notNull(init);
        this.v = v;
        this.type = type;
        this.field = field;
        this.init = init;
    }

    public DeclField(AbstractIdentifier tree, AbstractExpr tree2, AbstractInitialization init2) {
    }

    public AbstractIdentifier getField(){
        return this.field;
    }

    @Override
    public void verifyField(DecacCompiler compiler, AbstractIdentifier classeSup, AbstractIdentifier classe)
    throws ContextualError {
        Symbol fieldName = this.field.getName();
        EnvironmentType env_Types = compiler.GetEnvTypes();
        Symbol symbSup = classeSup.getName();
        ClassType classTypeSup = (ClassType) env_Types.getType(symbSup);
        Symbol symb = classe.getName();
        ClassType defType = (ClassType) env_Types.getType(symb);
        if (classTypeSup != null && defType != null) {
            ClassDefinition classDefSup = classTypeSup.getDefinition();
            EnvironmentExp envExpSup = classDefSup.getMembers();
            ClassDefinition def = defType.getDefinition();
            EnvironmentExp envExp = def.getMembers();
            if (envExpSup.equals(envExp.getParent())) {
                ExpDefinition ExpDef = envExpSup.get(fieldName);
                if (ExpDef != null) {
                    Validate.isTrue(ExpDef.isField(), fieldName.getName() + " isn't a field");
                }
            }
            Type fType = type.verifyType(compiler);
            if (fType.isVoid()){
                throw new ContextualError("type void", this.type.getLocation());
            }
            try {
                int index = classDefSup.getNumberOfFields() + def.getNumberOfFields() + 1;
                FieldDefinition newDef= new FieldDefinition(fType, field.getLocation(), v, null, index);
                field.setDefinition(newDef);
                field.setType(fType);
                envExp.declare(fieldName, newDef);
                def.incNumberOfFields();
                init.verifyInitialization(compiler, fType, envExp , def);
            } catch (EnvironmentExp.DoubleDefException e) {
                String message = "can't defined field identifier several times in a class";
                throw new ContextualError(message, field.getLocation());
            }
        }
    }

    @Override
    public void verifyFieldBody(DecacCompiler compiler, AbstractIdentifier classeSup, AbstractIdentifier classe)
    throws ContextualError {
        Symbol fieldName = this.field.getName();
        EnvironmentType env_Types = compiler.GetEnvTypes();
        Symbol symbSup = classeSup.getName();
        ClassType classTypeSup = (ClassType) env_Types.getType(symbSup);
        Symbol symb = classe.getName();
        ClassType defType = (ClassType) env_Types.getType(symb);
        if (classTypeSup != null && defType != null) {
            ClassDefinition classDefSup = classTypeSup.getDefinition();
            EnvironmentExp envExpSup = classDefSup.getMembers();
            ClassDefinition def = defType.getDefinition();
            EnvironmentExp envExp = def.getMembers();
            if (envExpSup.equals(envExp.getParent())) {
                ExpDefinition ExpDef = envExpSup.get(fieldName);
                if (ExpDef != null) {
                    Validate.isTrue(compiler.subType(compiler, this.type.getType(), ExpDef.getType()), "Incompatible extension of field "+this.field.getName());
                }
            }
        }
    }

    @Override
    public void decompile(IndentPrintStream s) {
        if (v.toString().equals("PROTECTED")) {
            s.print("protected");
        }
        s.print(" ");
        type.decompile(s);
        s.print(" ");
    	this.field.decompile(s);
    	s.print(" ");
    	this.init.decompile(s);
    	s.println(";");	
    }

    @Override
    protected void prettyPrintChildren(PrintStream s, String prefix) {
        this.type.prettyPrint(s, prefix, false);
        this.field.prettyPrint(s, prefix, false);
        this.init.prettyPrint(s, prefix, true);
        
    }

    @Override
    protected void iterChildren(TreeFunction f) {
        type.iter(f);
        field.iter(f);
        init.iter(f);
    }

    @Override
    protected void codeGenDeclField(DecacCompiler compiler) {
        compiler.addComment("Initialisation du champ " + field.getName().getName());
        init.codeGenInitField(compiler, field.getType());
        compiler.addInstruction(new LOAD(new RegisterOffset(-2, Register.LB), Register.R1));
        compiler.addInstruction(new STORE(Register.R0, new RegisterOffset(field.getFieldDefinition().getIndex(), Register.R1)));
    }

    @Override
    protected void codeGenDeclFieldZero(DecacCompiler compiler) {
        compiler.addComment("Initialisation du champ " + field.getName().getName() + " à zero");
        compiler.addInstruction(new LOAD(0, Register.R0));
        compiler.addInstruction(new LOAD(new RegisterOffset(-2, Register.LB), Register.R1));
        compiler.addInstruction(new STORE(Register.R0, new RegisterOffset(field.getFieldDefinition().getIndex(), Register.R1)));
    }
    
}
