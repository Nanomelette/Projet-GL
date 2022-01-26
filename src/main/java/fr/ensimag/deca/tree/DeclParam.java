package fr.ensimag.deca.tree;

import org.apache.commons.lang.Validate;
import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.context.EnvironmentExp;
import fr.ensimag.deca.context.ParamDefinition;
import fr.ensimag.deca.context.Type;
import fr.ensimag.deca.tools.IndentPrintStream;
import java.io.PrintStream;

/**
 * Declaration of a parameter.
 *
 * @author oscarmaggiori
 * @version $Id: $Id
 */
public class DeclParam extends AbstractDeclParam{

    private AbstractIdentifier type;
    private AbstractIdentifier name;

    /**
     * <p>Constructor for DeclParam.</p>
     *
     * @param type a {@link fr.ensimag.deca.tree.AbstractIdentifier} object
     * @param name a {@link fr.ensimag.deca.tree.AbstractIdentifier} object
     */
    public DeclParam(AbstractIdentifier type, AbstractIdentifier name){
        Validate.notNull(type);
        Validate.notNull(name);
        this.type = type;
        this.name = name;
    }

    /** {@inheritDoc} */
    protected Type verifyParam(DecacCompiler compiler)
            throws ContextualError{
            Type paramType = type.verifyType(compiler);
            if (paramType.isVoid()){
                throw new ContextualError("parameter void", type.getLocation());
            }
            return paramType;
        }
    
    /** {@inheritDoc} */
    protected void verifyDeclParam(DecacCompiler compiler, EnvironmentExp localEnv, int index)
            throws ContextualError{
            try {
                ParamDefinition newDef = new ParamDefinition(type.getType(), name.getLocation());
                newDef.setIndex(index);
                name.setDefinition(newDef);
                name.setType(type.getType());
                localEnv.declare(name.getName(), newDef);
            } catch (EnvironmentExp.DoubleDefException e) {
                String message = "can't defined parameter identifier several times in a class";
                throw new ContextualError(message, name.getLocation());
            }
        }

    /** {@inheritDoc} */
    @Override
    public void decompile(IndentPrintStream s) {
        type.decompile(s);
        s.print(" ");
        name.decompile(s);
        
    }

    /** {@inheritDoc} */
    @Override
    protected void prettyPrintChildren(PrintStream s, String prefix) {
        type.prettyPrint(s, prefix, false);
        name.prettyPrint(s, prefix, false); 
    }

    /** {@inheritDoc} */
    @Override
    protected void iterChildren(TreeFunction f) {
        type.iter(f);
        name.iter(f);
    }
    
}
