package fr.ensimag.deca.tree;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.ClassDefinition;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.context.EnvironmentExp;
import fr.ensimag.deca.context.Type;
import fr.ensimag.deca.tools.IndentPrintStream;

/**
 * <p>Abstract AbstractMethodBody class.</p>
 *
 * @author oscarmaggiori
 * @version $Id: $Id
 */
public abstract class AbstractMethodBody extends Tree {

	/**
	 * <p>verifyMethodBody.</p>
	 *
	 * @param compiler a {@link fr.ensimag.deca.DecacCompiler} object
	 * @param localEnv a {@link fr.ensimag.deca.context.EnvironmentExp} object
	 * @param currentClass a {@link fr.ensimag.deca.context.ClassDefinition} object
	 * @param returnType a {@link fr.ensimag.deca.context.Type} object
	 * @throws fr.ensimag.deca.context.ContextualError if any.
	 */
	protected abstract void verifyMethodBody(DecacCompiler compiler, EnvironmentExp localEnv, ClassDefinition currentClass, Type returnType)
            throws ContextualError;

	/**
	 * <p>verifyClassBody.</p>
	 *
	 * @param compiler a {@link fr.ensimag.deca.DecacCompiler} object
	 * @param members a {@link fr.ensimag.deca.context.EnvironmentExp} object
	 * @param envExpParams a {@link fr.ensimag.deca.context.EnvironmentExp} object
	 * @param class1 a {@link fr.ensimag.deca.tree.AbstractIdentifier} object
	 * @param return1 a {@link fr.ensimag.deca.context.Type} object
	 * @throws fr.ensimag.deca.context.ContextualError if any.
	 */
	public abstract void verifyClassBody(DecacCompiler compiler, EnvironmentExp members, EnvironmentExp envExpParams, 
										 AbstractIdentifier class1, Type return1) throws ContextualError ;

    /**
     * <p>codeGenMethodBody.</p>
     *
     * @param compiler a {@link fr.ensimag.deca.DecacCompiler} object
     */
    protected abstract void codeGenMethodBody(DecacCompiler compiler);

	/**
	 * <p>codeGenSaveRestore.</p>
	 *
	 * @param compiler a {@link fr.ensimag.deca.DecacCompiler} object
	 */
	protected abstract void codeGenSaveRestore(DecacCompiler compiler);
	
	/** {@inheritDoc} */
	public abstract void decompile(IndentPrintStream s);

    /**
     * <p>getNbrVarMethodBody.</p>
     *
     * @return a int
     */
    public abstract int getNbrVarMethodBody();
}
