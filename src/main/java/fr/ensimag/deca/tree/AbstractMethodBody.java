package fr.ensimag.deca.tree;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.ClassDefinition;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.context.EnvironmentExp;
import fr.ensimag.deca.context.Type;
import fr.ensimag.deca.tools.IndentPrintStream;

public abstract class AbstractMethodBody extends Tree {

	protected abstract void verifyMethodBody(DecacCompiler compiler, EnvironmentExp localEnv, ClassDefinition currentClass, Type returnType)
            throws ContextualError;

	public abstract void verifyClassBody(DecacCompiler compiler, EnvironmentExp members, EnvironmentExp envExpParams, 
										 AbstractIdentifier class1, Type return1) throws ContextualError ;

    protected abstract void codeGenMethodBody(DecacCompiler compiler);

	protected abstract void codeGenSaveRestore(DecacCompiler compiler);
	
	public abstract void decompile(IndentPrintStream s);

    public abstract int getNbrVarMethodBody();
}