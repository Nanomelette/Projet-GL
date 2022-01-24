package fr.ensimag.deca.tree;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.context.EnvironmentExp;

public abstract class AbstractDeclFieldSet extends Tree {
	
	protected abstract void verifyDeclFieldSet(DecacCompiler compiler,
            AbstractIdentifier superClass, AbstractIdentifier name)
            throws ContextualError;

	public abstract void verifyClassBody(DecacCompiler compiler, EnvironmentExp members, AbstractIdentifier name) throws ContextualError ;

    public abstract void codeGenDeclFieldSet(DecacCompiler compiler);
    
    public abstract Visibility getVisibility() ;

	public abstract void codeGenSetOperandField(DecacCompiler compiler); 
    
    public abstract void codeGenDeclFieldSetZero(DecacCompiler compiler);
}
