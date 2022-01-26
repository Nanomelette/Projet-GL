package fr.ensimag.deca.tree;

import java.io.PrintStream;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.ClassDefinition;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.context.EnvironmentExp;
import fr.ensimag.deca.context.Type;
import fr.ensimag.deca.tools.IndentPrintStream;
import fr.ensimag.ima.pseudocode.InlinePortion;
import fr.ensimag.ima.pseudocode.instructions.RTS;


/**
 * Method body using "asm(...)"
 *
 * @author oscarmaggiori
 * @version $Id: $Id
 */
public class MethodBodyAsm extends AbstractMethodBody {
    private StringLiteral code;

    /**
     * <p>Constructor for MethodBodyAsm.</p>
     *
     * @param string a {@link fr.ensimag.deca.tree.StringLiteral} object
     */
    public MethodBodyAsm(StringLiteral string){
        this.code = string;
    }


    /** {@inheritDoc} */
    @Override
    public void decompile(IndentPrintStream s) {
        s.print("asm(");
        code.decompile(s);
        s.print(");");
    }

    /** {@inheritDoc} */
    @Override
    protected void prettyPrintChildren(PrintStream s, String prefix) {
        code.prettyPrint(s, prefix, false);
    }


    /** {@inheritDoc} */
    @Override
    protected void iterChildren(TreeFunction f) {
        code.iter(f);
    }


    /** {@inheritDoc} */
    @Override
    protected void verifyMethodBody(DecacCompiler compiler, EnvironmentExp localEnv, ClassDefinition currentClass, Type returnType) throws ContextualError {
        
        this.code.setType(this.code.verifyExpr(compiler, localEnv, currentClass));
        
    }


    /** {@inheritDoc} */
    @Override
    protected void codeGenMethodBody(DecacCompiler compiler) {
        compiler.add(new InlinePortion("    "+code.getValue().replace("\\\"", "\"")));
    }


    /** {@inheritDoc} */
    @Override
    public void verifyClassBody(DecacCompiler compiler, EnvironmentExp members, EnvironmentExp envExpParams,
            AbstractIdentifier class1, Type return1) throws ContextualError {
        // Nothing to do
        
    }


    /** {@inheritDoc} */
    @Override
    protected void codeGenSaveRestore(DecacCompiler compiler) {
        compiler.addInstruction(new RTS());
    }

    /**
     * <p>getNbrVarMethodBody.</p>
     *
     * @return a int
     */
    public int getNbrVarMethodBody() {
        return 0;
    }

    
}
