package fr.ensimag.deca.tree;

import fr.ensimag.deca.context.Type;
import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.ClassDefinition;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.context.Definition;
import fr.ensimag.deca.context.FieldDefinition;
import fr.ensimag.deca.context.MethodDefinition;
import fr.ensimag.deca.context.ExpDefinition;
import fr.ensimag.deca.context.VariableDefinition;
import fr.ensimag.deca.tools.SymbolTable;

/**
 * <p>Abstract AbstractIdentifier class.</p>
 *
 * @author gl20
 * @date 01/01/2022
 * @version $Id: $Id
 */
public abstract class AbstractIdentifier extends AbstractLValue {

    /**
     * Like {@link #getDefinition()}, but works only if the definition is a
     * ClassDefinition.
     *
     * This method essentially performs a cast, but throws an explicit exception
     * when the cast fails.
     *
     * @return a {@link fr.ensimag.deca.context.ClassDefinition} object
     */
    public abstract ClassDefinition getClassDefinition();

    /**
     * <p>getDefinition.</p>
     *
     * @return a {@link fr.ensimag.deca.context.Definition} object
     */
    public abstract Definition getDefinition();

    /**
     * Like {@link #getDefinition()}, but works only if the definition is a
     * FieldDefinition.
     *
     * This method essentially performs a cast, but throws an explicit exception
     * when the cast fails.
     *
     * @return a {@link fr.ensimag.deca.context.FieldDefinition} object
     */
    public abstract FieldDefinition getFieldDefinition();

    /**
     * Like {@link #getDefinition()}, but works only if the definition is a
     * MethodDefinition.
     *
     * This method essentially performs a cast, but throws an explicit exception
     * when the cast fails.
     *
     * @return a {@link fr.ensimag.deca.context.MethodDefinition} object
     */
    public abstract MethodDefinition getMethodDefinition();

    /**
     * <p>getName.</p>
     *
     * @return a {@link fr.ensimag.deca.tools.SymbolTable.Symbol} object
     */
    public abstract SymbolTable.Symbol getName();

    /**
     * Like {@link #getDefinition()}, but works only if the definition is a ExpDefinition.
     *
     * This method essentially performs a cast, but throws an explicit exception
     * when the cast fails.
     *
     * @return a {@link fr.ensimag.deca.context.ExpDefinition} object
     */
    public abstract ExpDefinition getExpDefinition();

    /**
     * Like {@link #getDefinition()}, but works only if the definition is a
     * VariableDefinition.
     *
     * This method essentially performs a cast, but throws an explicit exception
     * when the cast fails.
     *
     * @return a {@link fr.ensimag.deca.context.VariableDefinition} object
     */
    public abstract VariableDefinition getVariableDefinition();

    /**
     * <p>setDefinition.</p>
     *
     * @param definition a {@link fr.ensimag.deca.context.Definition} object
     */
    public abstract void setDefinition(Definition definition);



    /**
     * Implements non-terminal "type" of [SyntaxeContextuelle] in the 3 passes
     *
     * @param compiler contains "env_types" attribute
     * @return the type corresponding to this identifier
     *         (corresponds to the "type" attribute)
     * @throws fr.ensimag.deca.context.ContextualError if any.
     */
    public abstract Type verifyType(DecacCompiler compiler) throws ContextualError;
}
