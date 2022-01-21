package fr.ensimag.deca.tree;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.codegen.Data;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.context.EnvironmentExp;
import fr.ensimag.deca.tools.IndentPrintStream;
import fr.ensimag.deca.tools.SymbolTable.Symbol;
import fr.ensimag.ima.pseudocode.DAddr;
import fr.ensimag.ima.pseudocode.Register;
import fr.ensimag.ima.pseudocode.RegisterOffset;

import java.io.PrintStream;


import org.apache.commons.lang.Validate;
import org.apache.log4j.Logger;

/**
 * @author gl20
 * @date 01/01/2022
 */
public class Main extends AbstractMain {
    private static final Logger LOG = Logger.getLogger(Main.class);
    
    private ListDeclVar declVariables;
    private ListInst insts;
    public Main(ListDeclVar declVariables,
            ListInst insts) {
        Validate.notNull(declVariables);
        Validate.notNull(insts);
        this.declVariables = declVariables;
        this.insts = insts;
    }

    public ListDeclVar getDeclVariables() {
        return declVariables;
    }

    @Override
    protected void verifyMain(DecacCompiler compiler) throws ContextualError {
        LOG.debug("verify Main: start");
        // A FAIRE: Appeler méthodes "verify*" de ListDeclVar et ListInst.
        // Vous avez le droit de changer le profil fourni pour ces méthodes
        // (mais ce n'est à priori pas nécessaire).
        EnvironmentExp localEnv = new EnvironmentExp(null);
        localEnv = declVariables.verifyListDeclVariable(compiler, localEnv, null);
        Symbol symbVoid = compiler.getSymbolTable().create("void");
        insts.verifyListInst(compiler,localEnv,null,compiler.searchSymbol(symbVoid));

        LOG.debug("verify Main: end");
    }

    @Override
    protected void codeGenMain(DecacCompiler compiler) {
        // for (AbstractDeclVar absDeclVar : declVariables.getList()) {
        //     DeclVar declVar = (DeclVar) absDeclVar;
        //     declVar.codeGenDeclVarGlob(compiler);
        // }

        compiler.addComment("------------------------------------------");
        compiler.addComment("    Déclaration des variables globales    ");
        compiler.addComment("------------------------------------------");
        declVariables.codeGenListDeclVarGlob(compiler);
        compiler.addComment("------------------------------------------");
        compiler.addComment("      Début des instructions du main      ");
        compiler.addComment("------------------------------------------");
        insts.codeGenListInst(compiler);
    }
    
    @Override
    public void decompile(IndentPrintStream s) {
        s.println("{");
        s.indent();
        declVariables.decompile(s);
        insts.decompile(s);
        s.unindent();
        s.println("}");
    }

    @Override
    protected void iterChildren(TreeFunction f) {
        declVariables.iter(f);
        insts.iter(f);
    }
 
    @Override
    protected void prettyPrintChildren(PrintStream s, String prefix) {
        declVariables.prettyPrint(s, prefix, false);
        insts.prettyPrint(s, prefix, true);
    }

    public void vTableInitialization(DecacCompiler compiler, ListDeclClass classes) {
        Data data = compiler.getData();
        if (!classes.getList().isEmpty()) {
            data.newVTable(compiler);
        }
        classes.addToVTable(compiler);
    } 
}
