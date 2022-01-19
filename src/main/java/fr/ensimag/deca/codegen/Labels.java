package fr.ensimag.deca.codegen;

import java.util.HashSet;
import java.util.Iterator;

import fr.ensimag.ima.pseudocode.Label;

public class Labels {
    public final Label stack_overflow_error  = new Label("stack_overflow_error");
    public final Label io_error = new Label("io_error");
    public final Label overflow_error = new Label("overflow_error");
    public final Label zero_division = new Label("zero_division");
    public final Label equals = new Label("code.Object.equals");
    public final Label null_dereference = new Label("null_dereference");

    private HashSet<Label> usedLabels = new HashSet<Label>();

    public Labels() {
        addLabel(stack_overflow_error);
        addLabel(io_error);
        addLabel(overflow_error);
        addLabel(zero_division);
        addLabel(equals);
        addLabel(null_dereference);
    }

    public void addLabel(Label label) {
        usedLabels.add(label);
    }

    public Iterator<Label> getUsedLabels() {
        return usedLabels.iterator();
    }

}
