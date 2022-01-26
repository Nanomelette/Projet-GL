package fr.ensimag.deca.tree;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.context.EnvironmentExp;

/**
 * <p>Abstract AbstractDeclFieldSet class.</p>
 *
 * @author oscarmaggiori
 * @version $Id: $Id
 */
public abstract class AbstractDeclFieldSet extends Tree {
	
	/**
	 * <p>verifyDeclFieldSet.</p>
	 *
	 * @param compiler a {@link fr.ensimag.deca.DecacCompiler} object
	 * @param superClass a {@link fr.ensimag.deca.tree.AbstractIdentifier} object
	 * @param name a {@link fr.ensimag.deca.tree.AbstractIdentifier} object
	 * @throws fr.ensimag.deca.context.ContextualError if any.
	 */
	protected abstract void verifyDeclFieldSet(DecacCompiler compiler,
            AbstractIdentifier superClass, AbstractIdentifier name)
            throws ContextualError;

	/**
	 * <p>verifyClassBody.</p>
	 *
	 * @param compiler a {@link fr.ensimag.deca.DecacCompiler} object
	 * @param members a {@link fr.ensimag.deca.context.EnvironmentExp} object
	 * @param name a {@link fr.ensimag.deca.tree.AbstractIdentifier} object
	 * @throws fr.ensimag.deca.context.ContextualError if any.
	 */
	public abstract void verifyClassBody(DecacCompiler compiler, EnvironmentExp members, AbstractIdentifier name) throws ContextualError ;

    /**
     * <p>codeGenDeclFieldSet.</p>
     *
     * @param compiler a {@link fr.ensimag.deca.DecacCompiler} object
     */
    public abstract void codeGenDeclFieldSet(DecacCompiler compiler);
    
    /**
     * <p>getVisibility.</p>
     *
     * @return a {@link fr.ensimag.deca.tree.Visibility} object
     */
    public abstract Visibility getVisibility() ;

	/**
	 * <p>codeGenSetOperandField.</p>
	 *
	 * @param compiler a {@link fr.ensimag.deca.DecacCompiler} object
	 */
	public abstract void codeGenSetOperandField(DecacCompiler compiler); 
    
    /**
     * <p>codeGenDeclFieldSetZero.</p>
     *
     * @param compiler a {@link fr.ensimag.deca.DecacCompiler} object
     */
    public abstract void codeGenDeclFieldSetZero(DecacCompiler compiler);
}
