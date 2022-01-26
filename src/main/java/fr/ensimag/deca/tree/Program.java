package fr.ensimag.deca.tree;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.tools.IndentPrintStream;
import fr.ensimag.ima.pseudocode.instructions.*;
import java.io.PrintStream;
import org.apache.commons.lang.Validate;
import org.apache.log4j.Logger;

/**
 * Deca complete program (class definition plus main block)
 *
 * @author gl20
 * @date 01/01/2022
 * @version $Id: $Id
 */
public class Program extends AbstractProgram {
    private static final Logger LOG = Logger.getLogger(Program.class);
    
    /**
     * <p>Constructor for Program.</p>
     *
     * @param classes a {@link fr.ensimag.deca.tree.ListDeclClass} object
     * @param main a {@link fr.ensimag.deca.tree.AbstractMain} object
     */
    public Program(ListDeclClass classes, AbstractMain main) {
        Validate.notNull(classes);
        Validate.notNull(main);
        this.classes = classes;
        this.main = main;
    }
    /**
     * <p>Getter for the field <code>classes</code>.</p>
     *
     * @return a {@link fr.ensimag.deca.tree.ListDeclClass} object
     */
    public ListDeclClass getClasses() {
        return classes;
    }
    /**
     * <p>Getter for the field <code>main</code>.</p>
     *
     * @return a {@link fr.ensimag.deca.tree.AbstractMain} object
     */
    public AbstractMain getMain() {
        return main;
    }
    private ListDeclClass classes;
    private AbstractMain main;

    /** {@inheritDoc} */
    @Override
    public void verifyProgram(DecacCompiler compiler) throws ContextualError {
        LOG.debug("verify program: start");
        // Pass 1
        classes.verifyListClass(compiler);
        // Pass 2
        classes.verifyListClassMembers(compiler);
        // Pass 3
        classes.verifyListClassBody(compiler);
        main.verifyMain(compiler);
        LOG.debug("verify program: end");
    }

    /** {@inheritDoc} */
    @Override
    public void codeGenProgram(DecacCompiler compiler) {
        
        // Table des méthodes
        main.vTableInitialization(compiler, getClasses());
        // Génération du code
        main.codeGenMain(compiler);
        compiler.addInstruction(new HALT());
        // Ecriture des messages d'erreur
        compiler.getData().addBottom(compiler);
        
        compiler.addComment("------------------------------------------");
        compiler.addComment("            Codage des méthodes           ");
        compiler.addComment("------------------------------------------");
        classes.codeGenListDeclClass(compiler);
        
        // Ecriture de l'header
        compiler.getData().addHeader(compiler);
    }

    /** {@inheritDoc} */
    @Override
    public void decompile(IndentPrintStream s) {
        getClasses().decompile(s);
        getMain().decompile(s);
    }
    
    /** {@inheritDoc} */
    @Override
    protected void iterChildren(TreeFunction f) {
        classes.iter(f);
        main.iter(f);
    }
    /** {@inheritDoc} */
    @Override
    protected void prettyPrintChildren(PrintStream s, String prefix) {
        classes.prettyPrint(s, prefix, false);
        main.prettyPrint(s, prefix, true);
    }
}
