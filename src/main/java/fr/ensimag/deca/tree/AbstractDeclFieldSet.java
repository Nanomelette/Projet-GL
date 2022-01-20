package fr.ensimag.deca.tree;

import java.io.PrintStream;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.context.EnvironmentExp;
import fr.ensimag.deca.tools.IndentPrintStream;

public abstract class AbstractDeclFieldSet extends Tree {
	
	protected abstract EnvironmentExp verifyDeclFieldSet(DecacCompiler compiler,
            AbstractIdentifier superClass, AbstractIdentifier name)
            throws ContextualError;

	public abstract void verifyClassBody(DecacCompiler compiler, EnvironmentExp members, AbstractIdentifier name) throws ContextualError ;

    public abstract void codeGenDeclFieldSet(DecacCompiler compiler);
    
    public abstract Visibility getVisibility() ;

	public abstract void codeGenSetOperandField(DecacCompiler compiler); 
    
    public abstract void codeGenDeclFieldSetZero(DecacCompiler compiler);
}
