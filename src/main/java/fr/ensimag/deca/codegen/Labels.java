package fr.ensimag.deca.codegen;

import java.util.HashSet;
import java.util.Iterator;
import fr.ensimag.ima.pseudocode.Label;

/**
 * Class used in order to keep track of the used labels
 *
 * @author oscarmaggiori
 * @version $Id: $Id
 */
public class Labels {
    public final Label stack_overflow_error  = new Label("stack_overflow_error");
    public final Label io_error = new Label("io_error");
    public final Label overflow_error = new Label("overflow_error");
    public final Label zero_division = new Label("zero_division");
    public final Label null_dereference = new Label("null_dereference");
    public final Label heap_overflow = new Label("heap_overflow");
    public final Label cast_error = new Label("cast_error");
    

    private HashSet<Label> usedLabels = new HashSet<Label>();

    /**
     * <p>Constructor for Labels.</p>
     */
    public Labels() {
        addLabel(stack_overflow_error);
        addLabel(overflow_error);
        addLabel(heap_overflow);
        addLabel(zero_division);
        addLabel(null_dereference);
        addLabel(cast_error);
        addLabel(io_error);
    }

    /**
     * Add a label
     *
     * @param label a {@link fr.ensimag.ima.pseudocode.Label} object
     */
    public void addLabel(Label label) {
        usedLabels.add(label);
    }

    /**
     * Getter of the used labels
     *
     * @return a {@link java.util.Iterator} object
     */
    public Iterator<Label> getUsedLabels() {
        return usedLabels.iterator();
    }

}
