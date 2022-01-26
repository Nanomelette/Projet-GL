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
/**
 * Representation of the data of an IMAProgram.
 *
 * @author oscarmaggiori
 * @version $Id: $Id
 */
public class Data {

    /**
     * Represents the number of register we are allowed to use, defined by the 
     * compilerOptions (-r maxRegister+1)
     */
    private int maxRegister = 16;
    // When freeStoragePointer < maxRegister, the storage is in a register,
    // otherwise it is in the stack.
    /**
     * Points out the next free register
     */
    private int freeStoragePointer = 2;
    /**
     * Counts the register used, so the maximum index
     */
    private int numberOfUsedRegister = 0;
    /**
     * Keeps count of the offset from GB
     */
    private int gBOffset = 1;
    /**
     * Keeps count of the offset from LB
     */
    private int lBOffset = 1;
    /**
     * Keeps count of the maximum length of the stack
     */
    private int maxStackLength = 0;
    /**
     * Points out to the last used Register, usefull in order
     * to optimize the code
     */
    private GPRegister lastUsedRegister = GPRegister.getR(2);

    private Label labelReturn;
    /**
     * Keeps track of the used labels
     */
    private Labels labels = new Labels();

    /**
     * <p>Constructor for Data.</p>
     */
    public Data() {
    };

    /**
     * Setter of the numberOfUsedRegister
     *
     * @param numberOfUsedRegister a int
     */
    public void setNumberOfUsedRegister(int numberOfUsedRegister) {
        this.numberOfUsedRegister = numberOfUsedRegister;
    }

    /**
     * increment the lBOffset
     */
    public void incrementLb() {
        lBOffset++;
    }

    /**
     * Getter of the maxStackLength
     *
     * @return a int
     */
    public int getMaxStackLength() {
        return maxStackLength;
    }

    /**
     * Getter of the numberOfUsedRegister
     *
     * @return a int
     */
    public int getNumberOfUsedRegister() {
        return numberOfUsedRegister;
    }

    /**
     * Getter of the labels
     *
     * @return a {@link fr.ensimag.deca.codegen.Labels} object
     */
    public Labels getLabels() {
        return labels;
    }

    /**
     * Getter of the lBOffset
     *
     * @return a int
     */
    public int getlBOffset() {
        return lBOffset;
    }

    /**
     * Increments the lBOffset
     */
    public void incrementlBOffset() {
        lBOffset++;
    }

    /**
     * Setter of the maxRegister
     *
     * @param maxRegister a int
     */
    public void setMaxRegister(int maxRegister) {
        this.maxRegister = maxRegister;
    }

    /**
     * <p>hasFreeRegister.</p>
     *
     * @return whether the data has still a free register
     */
    public boolean hasFreeRegister() {
        return (freeStoragePointer < maxRegister);
    }

    /**
     * Getter of the max register
     *
     * @return a {@link fr.ensimag.ima.pseudocode.GPRegister} object
     */
    public GPRegister getMaxRegister() {
        return GPRegister.getR(maxRegister - 1);
    }

    /**
     * If there isn't any free register, return the last one, a PUSH/POP instructions
     * will be needed.
     *
     * @param compiler a {@link fr.ensimag.deca.DecacCompiler} object
     * @return a free register
     */
    public GPRegister getFreeRegister(DecacCompiler compiler) {
        if (hasFreeRegister()) {
            numberOfUsedRegister = Math.max(numberOfUsedRegister, freeStoragePointer - 1);
            return GPRegister.getR(freeStoragePointer++);
        } else {
            GPRegister lastRegister = GPRegister.getR(maxRegister - 1);
            return lastRegister;
        }
    }

    /**
     * <p>nbFreeRegsiter.</p>
     *
     * @return the number of free register
     */
    public int nbFreeRegsiter() {
        if (hasFreeRegister()) {
            return maxRegister - freeStoragePointer - 1;
        } else {
            return 0;
        }
    }

    /**
     * Decrements the freeStoragePointer
     */
    public void decrementFreeStoragePointer() {
        freeStoragePointer--;
    }

    /**
     * Increments the freeStoragePointer by all elements from incrementList + 1
     *
     * @param incrementList a int
     */
    public void incrementFreeStoragePointer(int... incrementList) {
        freeStoragePointer++;
        for (int i : incrementList) {
            freeStoragePointer += i;
        }
        if (freeStoragePointer > maxStackLength) {
            maxStackLength = freeStoragePointer;
        }
        numberOfUsedRegister = Math.max(numberOfUsedRegister, freeStoragePointer - 2);
    }

    /**
     * Restore the element
     */
    public void restoreData() {
        freeStoragePointer = 2;
    }

    /**
     * Restore the element to the int i
     *
     * @param i a int
     */
    public void restoreDataTo(int i) {
        freeStoragePointer = i;
    }

    /**
     * Setter of the lastUsedRegister
     *
     * @param lastUsedRegister a {@link fr.ensimag.ima.pseudocode.GPRegister} object
     */
    public void setLastUsedRegister(GPRegister lastUsedRegister) {
        this.lastUsedRegister = lastUsedRegister;
    }

    /**
     * Getter of the lastUsedRegister
     *
     * @return a {@link fr.ensimag.ima.pseudocode.GPRegister} object
     */
    public GPRegister getLastUsedRegister() {
        return lastUsedRegister;
    }

    /**
     * Increments the gBOffset by all the element from incrementList +1
     *
     * @param incrementList a int
     */
    public void incrementGbOffset(int... incrementList) {
        gBOffset++;
        for (int i : incrementList) {
            gBOffset += i;
        }
    }

    /**
     * Getter of the GBOffset
     *
     * @return a int
     */
    public int getGbOffset() {
        return gBOffset;
    }

    /**
     * Add the header of a program
     *
     * @param compiler a {@link fr.ensimag.deca.DecacCompiler} object
     */
    public void addHeader(DecacCompiler compiler) {
        compiler.addInstructionAtFirst(new ADDSP(gBOffset - 1));
        if (!(compiler.getCompilerOptions().getNoCheck())) {
            compiler.addInstructionAtFirst(new BOV(labels.stack_overflow_error));
            compiler.addInstructionAtFirst(new TSTO(gBOffset + maxStackLength - 1));
        }
        compiler.addInstructionAtFirst(null, "Début du programme");
    }

    /**
     * <p>addBottom.</p>
     *
     * @param compiler a {@link fr.ensimag.deca.DecacCompiler} object
     */
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

    /**
     * Getter of the freeStoragePointer
     *
     * @return a int
     */
    public int getFreeStoragePointer() {
        return freeStoragePointer;
    }

    /**
     * Add the header of a VTable
     *
     * @param compiler a {@link fr.ensimag.deca.DecacCompiler} object
     */
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

    /**
     * Used to POP all used register in a methodbody
     *
     * @param compiler a {@link fr.ensimag.deca.DecacCompiler} object
     */
    public void popUsedRegisters(DecacCompiler compiler) {
        int tmp = getNumberOfUsedRegister();
        while (tmp > 0) {
            compiler.addInstruction(new POP(Register.getR(tmp+1)));
            tmp--;
        }
    }

    /**
     * Used to PUSH all used register in a methodbody
     *
     * @param compiler a {@link fr.ensimag.deca.DecacCompiler} object
     */
    public void pushUsedRegisters(DecacCompiler compiler) {
        int tmp = getNumberOfUsedRegister();
        while (tmp > 0) {
            compiler.addInstructionAtFirst(new PUSH(Register.getR(tmp+1)));
            tmp--;
        }
    }

    /**
     * Restore the LBOffset
     */
    public void restorelBOffset() {
        lBOffset = 1;
    }

    /**
     * Setter of the return Label
     *
     * @param labelReturn a {@link fr.ensimag.ima.pseudocode.Label} object
     */
    public void setLabelReturn(Label labelReturn) {
        this.labelReturn = labelReturn;
    }
    /**
     * Getter of the return Label
     *
     * @return a {@link fr.ensimag.ima.pseudocode.Label} object
     */
    public Label getLabelReturn() {
        return labelReturn;
    }
}
