package fr.ensimag.deca.tree;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.tools.IndentPrintStream;
import fr.ensimag.ima.pseudocode.Label;
import fr.ensimag.ima.pseudocode.Register;
import fr.ensimag.ima.pseudocode.RegisterOffset;
import fr.ensimag.ima.pseudocode.instructions.CMP;
import fr.ensimag.ima.pseudocode.instructions.LOAD;
import fr.ensimag.ima.pseudocode.instructions.RTS;
import fr.ensimag.ima.pseudocode.instructions.SEQ;

import org.apache.log4j.Logger;

/**
 *
 * @author gl20
 * @date 01/01/2022
 */
public class ListDeclClass extends TreeList<AbstractDeclClass> {
    private static final Logger LOG = Logger.getLogger(ListDeclClass.class);
    
    @Override
    public void decompile(IndentPrintStream s) {
        for (AbstractDeclClass c : getList()) {
            c.decompile(s);
            s.println();
        }
    }

    /**
     * Pass 1 of [SyntaxeContextuelle]
     */
    void verifyListClass(DecacCompiler compiler) throws ContextualError {
        LOG.debug("verify listClass: start");

        for(AbstractDeclClass c : this.getList()){
            c.verifyClass(compiler);
        }
        LOG.debug("verify listClass: end");
    }

    /**
     * Pass 2 of [SyntaxeContextuelle]
     */
    public void verifyListClassMembers(DecacCompiler compiler) throws ContextualError {

        for(AbstractDeclClass c : this.getList()){
            c.verifyClassMembers(compiler);
        }
    }
    
    /**
     * Pass 3 of [SyntaxeContextuelle]
     */
    public void verifyListClassBody(DecacCompiler compiler) throws ContextualError {
        
        for(AbstractDeclClass c : this.getList()){
            c.verifyClassBody(compiler);
        }
    }

    /**
     * Pass 1 of [GenCode]
     */
    public void addToVTable(DecacCompiler compiler) {
        for (AbstractDeclClass absDeclClass : this.getList()) {
            absDeclClass.addToVTable(compiler);
        }
    }

    private void codeGenInitClass(DecacCompiler compiler) {
        compiler.addLabel(
                new Label("code.Object.equals"));
        compiler.addInstruction(
                new LOAD(
                        new RegisterOffset(-2, Register.LB),
                        Register.R0));
        compiler.addInstruction(
                new LOAD(
                        new RegisterOffset(-3, Register.LB),
                        Register.R1));
        compiler.addInstruction(
                new CMP(Register.R0, Register.R1));

        compiler.addInstruction(
                new SEQ(Register.R0));
        compiler.getData().setLastUsedRegister(Register.R0);
        compiler.addInstruction(
                new RTS());

    }

    /**
     * Pass 2 of [genCode]
     */
    public void codeGenListDeclClass(DecacCompiler compiler) {
        codeGenInitClass(compiler);
        for (AbstractDeclClass declClass : getList()) {
            declClass.codeGenDeclClass(compiler);
        }
    }

}
