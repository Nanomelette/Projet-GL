package fr.ensimag.deca.tree;

import java.io.PrintStream;

import org.apache.commons.lang.Validate;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.context.EnvironmentExp;
import fr.ensimag.deca.context.Type;
import fr.ensimag.deca.tools.IndentPrintStream;

/**
 * Declaration of a set of field.
 *
 * @author oscarmaggiori
 * @version $Id: $Id
 */
public class DeclFieldSet extends AbstractDeclFieldSet{

    private ListDeclField declField;
    private AbstractIdentifier type;
    private Visibility visibility;

    /**
     * <p>Constructor for DeclFieldSet.</p>
     *
     * @param type a {@link fr.ensimag.deca.tree.AbstractIdentifier} object
     * @param declField a {@link fr.ensimag.deca.tree.ListDeclField} object
     * @param visibility a {@link fr.ensimag.deca.tree.Visibility} object
     */
    public DeclFieldSet(AbstractIdentifier type, ListDeclField declField, Visibility visibility) {
        super();
        Validate.notNull(type);
        Validate.notNull(declField);
        Validate.notNull(visibility);
        this.type = type;
        this.declField = declField;
        this.visibility = visibility;
    }

    /**
     * <p>Getter for the field <code>type</code>.</p>
     *
     * @return a {@link fr.ensimag.deca.tree.AbstractIdentifier} object
     */
    public AbstractIdentifier getType() {
        return type;
    }

    /**
     * <p>Getter for the field <code>declField</code>.</p>
     *
     * @return a {@link fr.ensimag.deca.tree.ListDeclField} object
     */
    public ListDeclField getDeclField() {
        return declField;
    }
    
    /** {@inheritDoc} */
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

    /** {@inheritDoc} */
    @Override
    protected
    void iterChildren(TreeFunction f) {
        type.iter(f);
        declField.iter(f);
    }

    /** {@inheritDoc} */
    @Override
    protected void prettyPrintChildren(PrintStream s, String prefix) {
        type.prettyPrint(s, prefix, false);
        declField.prettyPrint(s, prefix, true);
    }

    /** {@inheritDoc} */
    @Override
    protected void verifyDeclFieldSet(DecacCompiler compiler, AbstractIdentifier superClass,
            AbstractIdentifier name) throws ContextualError {
                Type varType = this.type.verifyType(compiler);
                if (varType.isVoid()) {
                    throw new ContextualError("type void", this.type.getLocation());
                }
                
    }

    /** {@inheritDoc} */
    @Override
    public void verifyClassBody(DecacCompiler compiler, EnvironmentExp members, AbstractIdentifier name)
            throws ContextualError {
        // TODO Auto-generated method stub
        
    }

    /** {@inheritDoc} */
    @Override
    public Visibility getVisibility() {
        return this.visibility;
    }

    /** {@inheritDoc} */
    @Override
    public void codeGenSetOperandField(DecacCompiler compiler) {
        // TODO Auto-generated method stub
        
    }

    /** {@inheritDoc} */
    @Override
    public void codeGenDeclFieldSet(DecacCompiler compiler) {
        for (AbstractDeclField field : declField.getList()) {
            field.codeGenDeclField(compiler);
        }
    }

    /** {@inheritDoc} */
    @Override
    public void codeGenDeclFieldSetZero(DecacCompiler compiler) {
        for (AbstractDeclField field : declField.getList()) {
            field.codeGenDeclFieldZero(compiler);
        }
    }
}
