package fr.ensimag.deca.tree;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.ContextualError;

/**
 * Class declaration.
 *
 * @author gl20
 * @date 01/01/2022
 * @version $Id: $Id
 */
public abstract class AbstractDeclClass extends Tree {

    /**
     * Pass 1 of [SyntaxeContextuelle]. Verify that the class declaration is OK
     * without looking at its content.
     *
     * @param compiler a {@link fr.ensimag.deca.DecacCompiler} object
     * @throws fr.ensimag.deca.context.ContextualError if any.
     */
    protected abstract void verifyClass(DecacCompiler compiler)
            throws ContextualError;

    /**
     * Pass 2 of [SyntaxeContextuelle]. Verify that the class members (fields and
     * methods) are OK, without looking at method body and field initialization.
     *
     * @param compiler a {@link fr.ensimag.deca.DecacCompiler} object
     * @throws fr.ensimag.deca.context.ContextualError if any.
     */
    protected abstract void verifyClassMembers(DecacCompiler compiler)
            throws ContextualError;

    /**
     * Pass 3 of [SyntaxeContextuelle]. Verify that instructions and expressions
     * contained in the class are OK.
     *
     * @param compiler a {@link fr.ensimag.deca.DecacCompiler} object
     * @throws fr.ensimag.deca.context.ContextualError if any.
     */
    protected abstract void verifyClassBody(DecacCompiler compiler)
            throws ContextualError;


    /**
     * Pass 1 of [GenCode]. Add each method of each class to the method table.
     *
     * @param compiler a {@link fr.ensimag.deca.DecacCompiler} object
     */
    protected abstract void addToVTable(DecacCompiler compiler);

    /**
     * Pass 2 of [GenCode] for class declarations
     *
     * @param compiler a {@link fr.ensimag.deca.DecacCompiler} object
     */
    protected abstract void codeGenDeclClass(DecacCompiler compiler);
}
