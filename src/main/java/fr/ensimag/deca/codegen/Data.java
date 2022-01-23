package fr.ensimag.deca.codegen;

import java.util.Iterator;
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
import fr.ensimag.ima.pseudocode.instructions.POP;
import fr.ensimag.ima.pseudocode.instructions.PUSH;
import fr.ensimag.ima.pseudocode.instructions.STORE;
import fr.ensimag.ima.pseudocode.instructions.TSTO;
import fr.ensimag.ima.pseudocode.instructions.WNL;
import fr.ensimag.ima.pseudocode.instructions.WSTR;

public class Data {

    private int maxRegister = 15;
    // When storagePointer < maxRegister, the storage is in a register,
    // otherwise it is in the stack.
    private int freeStoragePointer = 2;
    private int numberOfUsedRegister = 0;
    private int gBOffset = 1;
    private int lBOffset = 1;
    private int maxStackLength = 0;
    private GPRegister lastUsedRegister = GPRegister.getR(2);

    private Label labelReturn;

    private Labels labels = new Labels();

    public Data() {
    };

    public int getMaxStackLength() {
        return maxStackLength;
    }

    public int getNumberOfUsedRegister() {
        return Math.min(maxRegister - 1, numberOfUsedRegister + 1);
    }

    public Labels getLabels() {
        return labels;
    }

    public int getlBOffset() {
        return lBOffset;
    }

    public void incrementlBOffset() {
        lBOffset++;
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
            numberOfUsedRegister++;
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
        numberOfUsedRegister++;
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
        if (!(compiler.getCompilerOptions().getNoCheck())) {
            compiler.addInstructionAtFirst(new ADDSP(gBOffset - 1));
            compiler.addInstructionAtFirst(new BOV(labels.stack_overflow_error));
            compiler.addInstructionAtFirst(new TSTO(gBOffset + maxStackLength - 1));
        }
        compiler.addInstructionAtFirst(null, "Début du programme");
    }

    public void addBottom(DecacCompiler compiler) {
        compiler.addComment("------------------------------------------");
        compiler.addComment("            Messages d'erreurs            ");
        compiler.addComment("------------------------------------------");
        if (!(compiler.getCompilerOptions().getNoCheck())) {
            Iterator<Label> it = labels.getUsedLabels();
            while (it.hasNext()) {
                Label label = it.next();
                compiler.addLabel(label);
                compiler.addInstruction(new WSTR("Error: " + label.toString()));
                compiler.addInstruction(new WNL());
                compiler.addInstruction(new ERROR());
            }
        } else {
            Label label = labels.io_error;
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
        Label equals = new Label("code.Object.equals");
        compiler.addInstruction(new LOAD(new NullOperand(), Register.R0));
        compiler.addInstruction(new STORE(Register.R0, new RegisterOffset(1, Register.GB)));
        compiler.addInstruction(new LOAD(new LabelOperand(equals), Register.R0));
        compiler.addInstruction(new STORE(Register.R0, new RegisterOffset(2, Register.GB)));
        // Faire la table des étiquettes de Object.
        incrementGbOffset(1);
        incrementFreeStoragePointer(1);
    }

    public void popUsedRegisters(DecacCompiler compiler) {
        int tmp = getNumberOfUsedRegister();
        while (tmp > 1) {
            compiler.addInstruction(new POP(Register.getR(tmp)));
            tmp--;
        }
    }

    public void pushUsedRegisters(DecacCompiler compiler) {
        int tmp = getNumberOfUsedRegister();
        while (tmp > 1) {
            compiler.addInstructionAtFirst(new PUSH(Register.getR(tmp)));
            tmp--;
        }
    }

    public void restorelBOffset() {
        lBOffset = 1;
    }

    public void setLabelReturn(Label labelReturn) {
        this.labelReturn = labelReturn;
    }

    public Label getLabelReturn() {
        return labelReturn;
    }
}
