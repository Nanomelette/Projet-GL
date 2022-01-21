package fr.ensimag.deca.tree;

import java.io.PrintStream;

import org.apache.commons.lang.Validate;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.context.EnvironmentExp;
import fr.ensimag.deca.context.Type;
import fr.ensimag.deca.tools.IndentPrintStream;

public class DeclFieldSet extends AbstractDeclFieldSet{

    private ListDeclField declField;
    private AbstractIdentifier type;
    private Visibility visibility;

    public DeclFieldSet(AbstractIdentifier type, ListDeclField declField, Visibility visibility) {
        super();
        Validate.notNull(type);
        Validate.notNull(declField);
        Validate.notNull(visibility);
        this.type = type;
        this.declField = declField;
        this.visibility = visibility;
    }

    public AbstractIdentifier getType() {
        return type;
    }

    public ListDeclField getDeclField() {
        return declField;
    }
    
    @Override
    public void decompile(IndentPrintStream s) {
    	if ( !this.getVisibility().name().equals("PUBLIC")) {
    	    s.print(this.getVisibility().name().toLowerCase());
        }
    	s.print(" ");
    	this.getType().decompile();
    	s.print(" ");
    	this.getDeclField().decompile();
    	s.println(";");	
    }

    @Override
    protected
    void iterChildren(TreeFunction f) {
        type.iter(f);
        declField.iter(f);
    }

    @Override
    protected void prettyPrintChildren(PrintStream s, String prefix) {
        type.prettyPrint(s, prefix, false);
        declField.prettyPrint(s, prefix, true);
    }

    @Override
    protected void verifyDeclFieldSet(DecacCompiler compiler, AbstractIdentifier superClass,
            AbstractIdentifier name) throws ContextualError {
                Type varType = this.type.verifyType(compiler);
                if (varType.isVoid()) {
                    throw new ContextualError("type void", getLocation());
                }
                
    }

    @Override
    public void verifyClassBody(DecacCompiler compiler, EnvironmentExp members, AbstractIdentifier name)
            throws ContextualError {
        // TODO Auto-generated method stub
        
    }

    @Override
    public Visibility getVisibility() {
        return this.visibility;
    }

    @Override
    public void codeGenSetOperandField(DecacCompiler compiler) {
        // TODO Auto-generated method stub
        
    }

}
