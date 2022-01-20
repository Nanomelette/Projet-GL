package fr.ensimag.deca.codegen;

import java.util.Iterator;
import java.util.Optional;

import org.antlr.v4.runtime.misc.ObjectEqualityComparator;
import org.apache.log4j.Logger;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.ima.pseudocode.GPRegister;
import fr.ensimag.ima.pseudocode.Register;
import fr.ensimag.ima.pseudocode.RegisterOffset;
import fr.ensimag.ima.pseudocode.Label;
import fr.ensimag.ima.pseudocode.LabelOperand;
import fr.ensimag.ima.pseudocode.NullOperand;
import fr.ensimag.ima.pseudocode.instructions.ADDSP;
import fr.ensimag.ima.pseudocode.instructions.BOV;
import fr.ensimag.ima.pseudocode.instructions.ERROR;
import fr.ensimag.ima.pseudocode.instructions.LOAD;
import fr.ensimag.ima.pseudocode.instructions.STORE;
import fr.ensimag.ima.pseudocode.instructions.TSTO;
import fr.ensimag.ima.pseudocode.instructions.WNL;
import fr.ensimag.ima.pseudocode.instructions.WSTR;
import fr.ensimag.ima.pseudocode.DAddr;
import fr.ensimag.deca.tree.AbstractDeclClass;
import fr.ensimag.deca.tree.AbstractDeclVar;
import fr.ensimag.deca.tree.DeclVar;
import fr.ensimag.deca.tree.Identifier;
import fr.ensimag.deca.tree.ListDeclVar;


public class Data {
    private static final Logger LOG = Logger.getLogger(Data.class);

    private int maxRegister = 15;
    // When storagePointer < maxRegister, the storage is in a register, 
    // otherwise it is in the stack.
    private int freeStoragePointer = 2;
    private int gBOffset = 1;
    private int lB = 1;
    private int maxStackLength = 0;
    private GPRegister lastUsedRegister = GPRegister.getR(2);

    // Labels :
    // private Label stack_overflow_error  = new Label("stack_overflow_error");
    // private Label io_error = new Label("io_error");
    // private Label overflow_error = new Label("overflow_error");
    // private Label zero_division = new Label("zero_division");
    // private Label equals = new Label("code.Object.equals");
    private Labels labels = new Labels();


    public Data() {};

   public Labels getLabels() {
       return labels;
   }

    public int getlB() {
        return lB;
    }

    public void incrementLb() {
        lB++;
    }

    public void setMaxRegister(int maxRegister) {
        this.maxRegister = maxRegister;
    }
    
    public boolean hasFreeRegister() {
        return (freeStoragePointer < maxRegister);
    }

    public GPRegister getMaxRegister() {
        return GPRegister.getR(maxRegister - 1);
    }

    public GPRegister getFreeRegister(DecacCompiler compiler) {
        if (hasFreeRegister()) {
            return GPRegister.getR(freeStoragePointer++);
        } else {
            GPRegister lastRegister = GPRegister.getR(maxRegister - 1);
            return lastRegister;
        }
    }

    public int nbFreeRegsiter() {
        if (hasFreeRegister()) {
            return maxRegister - freeStoragePointer - 1;
        } else {
            return 0;
        }
    }

    public void decrementFreeStoragePointer() {
        freeStoragePointer--;
    }

    public void incrementFreeStoragePointer(int... incrementList) {
        freeStoragePointer++;
        for (int i : incrementList) {
            freeStoragePointer += i;
        }
        if (freeStoragePointer > maxStackLength) {
            maxStackLength = freeStoragePointer;
        }

    }

    public void restoreData() {
        freeStoragePointer = 2;
    }

    public void restoreDataTo(int i) {
        freeStoragePointer = i;
    } 

    public void setLastUsedRegister(GPRegister lastUsedRegister) {
        this.lastUsedRegister = lastUsedRegister;
    }

    public GPRegister getLastUsedRegister() {
        return lastUsedRegister;
    }

    public void variableInit(ListDeclVar declVariables) {
        for (AbstractDeclVar absDeclVar : declVariables.getList()) {
            DAddr address = new RegisterOffset(gBOffset, Register.GB);
            Identifier var = (Identifier)((DeclVar)absDeclVar).getVarName();
            var.getExpDefinition().setOperand(address);
            gBOffset++;
        }
    }

    public void incrementGbOffset(int... incrementList) {
        gBOffset++;
        for (int i : incrementList) {
            gBOffset += i;
        }
    }

    public int getGbOffset() {
        return gBOffset;
    }

    public void addHeader(DecacCompiler compiler) {
        compiler.addInstructionAtFirst(new ADDSP(gBOffset-1));
        compiler.addInstructionAtFirst(new BOV(labels.stack_overflow_error));
        compiler.addInstructionAtFirst(new TSTO(gBOffset+maxStackLength-1));
        compiler.addInstructionAtFirst(null, "start main program");
    }

    public void addBottom(DecacCompiler compiler) {
        // compiler.addLabel(overflow_error);
        // compiler.addInstruction(new WSTR("Error: overflow_error."));
        // compiler.addInstruction(new WNL());
        // compiler.addInstruction(new ERROR());
        // compiler.addLabel(stack_overflow_error);
        // compiler.addInstruction(new WSTR("Error: full stack."));
        // compiler.addInstruction(new WNL());
        // compiler.addInstruction(new ERROR());
        // compiler.addLabel(io_error);
        // compiler.addInstruction(new WSTR("Error: io_error."));
        // compiler.addInstruction(new WNL());
        // compiler.addInstruction(new ERROR());
        // compiler.addLabel(zero_division);
        // compiler.addInstruction(new WSTR("Error: zero_division."));
        // compiler.addInstruction(new WNL());
        // compiler.addInstruction(new ERROR());
        Iterator<Label> it = labels.getUsedLabels();
        while (it.hasNext()) {
            Label label = it.next();
            compiler.addLabel(label);
            compiler.addInstruction(new WSTR("Error: " + label.toString()));
            compiler.addInstruction(new WNL());
            compiler.addInstruction(new ERROR());
        }
        
    }

    public int getFreeStoragePointer() {
        return freeStoragePointer;
    }

    public void newVTable(DecacCompiler compiler) {
        compiler.addComment("------------------------------------------");
        compiler.addComment("   Construction des tables des méthodes   ");
        compiler.addComment("------------------------------------------");
        compiler.addComment("Code de la table des méthodes de Object");
        compiler.addInstruction(new LOAD(new NullOperand(), Register.R0));
        compiler.addInstruction(new STORE(Register.R0, new RegisterOffset(1, Register.GB)));
        compiler.addInstruction(new LOAD(new LabelOperand(labels.equals), Register.R0));
        labels.addLabel(labels.equals);
        compiler.addInstruction(new STORE(Register.R0, new RegisterOffset(2, Register.GB)));
        // Faire la table des étiquettes de Object.
        incrementGbOffset(1);
        incrementFreeStoragePointer(1);
    }

}
