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
        int index = 1;
        if (classTypeSup != null && defType != null) {
            ClassDefinition classDefSup = classTypeSup.getDefinition();
            EnvironmentExp envExpSup = classDefSup.getMembers();
            ClassDefinition def = defType.getDefinition();
            EnvironmentExp envExp = def.getMembers();
            if (envExpSup.equals(envExp.getParent())) {
                ExpDefinition ExpDef = envExpSup.get(fieldName);
                if (ExpDef != null) {
                    Validate.isTrue(ExpDef.isField(), "field must be a field in classeSup");
                    FieldDefinition fieldDef = (FieldDefinition) ExpDef;
                    index = fieldDef.getIndex() + 1;
                }
            }
            Type fType = type.verifyType(compiler);
            if (fType.isVoid()){
                throw new ContextualError("type void", getLocation());
            }
            try {
                FieldDefinition newDef= new FieldDefinition(fType, field.getLocation(), v, null, index);
                field.setDefinition(newDef);
                field.setType(fType);
                envExp.declare(fieldName, newDef);
                init.verifyInitialization(compiler, fType, envExp , def);
            } catch (EnvironmentExp.DoubleDefException e) {
                String message = "can't defined field identifier several times in a class";
                throw new ContextualError(message, getLocation());
            }
        }
    }

    @Override
    public void decompile(IndentPrintStream s) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    protected void prettyPrintChildren(PrintStream s, String prefix) {
        this.field.prettyPrint(s, prefix, false);
        this.init.prettyPrint(s, prefix, true);
        
    }

    @Override
    protected void iterChildren(TreeFunction f) {
        // TODO Auto-generated method stub
        
    }
    
}
