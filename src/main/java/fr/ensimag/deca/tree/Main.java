package fr.ensimag.deca.tree;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.codegen.Data;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.context.EnvironmentExp;
import fr.ensimag.deca.tools.IndentPrintStream;
import fr.ensimag.deca.tools.SymbolTable.Symbol;
import java.io.PrintStream;
import org.apache.commons.lang.Validate;


/**
 * Main class
 *
 * @author gl20
 * @date 01/01/2022
 * @version $Id: $Id
 */
public class Main extends AbstractMain {
    
    private ListDeclVar declVariables;
    private ListInst insts;
    /**
     * <p>Constructor for Main.</p>
     *
     * @param declVariables a {@link fr.ensimag.deca.tree.ListDeclVar} object
     * @param insts a {@link fr.ensimag.deca.tree.ListInst} object
     */
    public Main(ListDeclVar declVariables,
            ListInst insts) {
        Validate.notNull(declVariables);
        Validate.notNull(insts);
        this.declVariables = declVariables;
        this.insts = insts;
    }

    /**
     * <p>Getter for the field <code>declVariables</code>.</p>
     *
     * @return a {@link fr.ensimag.deca.tree.ListDeclVar} object
     */
    public ListDeclVar getDeclVariables() {
        return declVariables;
    }

    /** {@inheritDoc} */
    @Override
    protected void verifyMain(DecacCompiler compiler) throws ContextualError {
        EnvironmentExp localEnv = new EnvironmentExp(null);
        localEnv = declVariables.verifyListDeclVariable(compiler, localEnv, null);
        Symbol symbVoid = compiler.getSymbolTable().create("void");
        insts.verifyListInst(compiler,localEnv,null,compiler.searchSymbol(symbVoid));
    }

    /** {@inheritDoc} */
    @Override
    protected void codeGenMain(DecacCompiler compiler) {

        compiler.addComment("------------------------------------------");
        compiler.addComment("    Déclaration des variables globales    ");
        compiler.addComment("------------------------------------------");
        declVariables.codeGenListDeclVarGlob(compiler);
        compiler.addComment("------------------------------------------");
        compiler.addComment("      Début des instructions du main      ");
        compiler.addComment("------------------------------------------");
        insts.codeGenListInst(compiler);
    }
    
    /** {@inheritDoc} */
    @Override
    public void decompile(IndentPrintStream s) {
        s.println("{");
        s.indent();
        declVariables.decompile(s);
        insts.decompile(s);
        s.unindent();
        s.println("}");
    }

    /** {@inheritDoc} */
    @Override
    protected void iterChildren(TreeFunction f) {
        for(AbstractDeclVar var : declVariables.getList()){
            var.iter(f);
        }
        for(AbstractInst inst : insts.getList()){
            inst.iter(f);
        }
    }
 
    /** {@inheritDoc} */
    @Override
    protected void prettyPrintChildren(PrintStream s, String prefix) {
        declVariables.prettyPrint(s, prefix, false);
        insts.prettyPrint(s, prefix, true);
    }

    /** {@inheritDoc} */
    public void vTableInitialization(DecacCompiler compiler, ListDeclClass classes) {
        Data data = compiler.getData();
        if (!classes.getList().isEmpty()) {
            data.newVTable(compiler);
        }
        classes.addToVTable(compiler);
    } 
}
