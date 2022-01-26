package fr.ensimag.deca.context;

import fr.ensimag.deca.tree.Location;
import fr.ensimag.ima.pseudocode.Label;
import org.apache.commons.lang.Validate;

/**
 * Definition of a method
 *
 * @author gl20
 * @date 01/01/2022
 * @version $Id: $Id
 */
public class MethodDefinition extends ExpDefinition {

    /** {@inheritDoc} */
    @Override
    public boolean isMethod() {
        return true;
    }

    /**
     * <p>Getter for the field <code>label</code>.</p>
     *
     * @return a {@link fr.ensimag.ima.pseudocode.Label} object
     */
    public Label getLabel() {
        Validate.isTrue(label != null,
                "setLabel() should have been called before");
        return label;
    }

    /**
     * <p>Setter for the field <code>label</code>.</p>
     *
     * @param label a {@link fr.ensimag.ima.pseudocode.Label} object
     */
    public void setLabel(Label label) {
        this.label = label;
    }

    /**
     * <p>Getter for the field <code>index</code>.</p>
     *
     * @return a int
     */
    public int getIndex() {
        return index;
    }

    private int index;

    /** {@inheritDoc} */
    @Override
    public MethodDefinition asMethodDefinition(String errorMessage, Location l)
            throws ContextualError {
        return this;
    }

    private final Signature signature;
    private final EnvironmentExp members;
    private Label label;
    
    /**
     * <p>Constructor for MethodDefinition.</p>
     *
     * @param type Return type of the method
     * @param location Location of the declaration of the method
     * @param signature List of arguments of the method
     * @param index Index of the method in the class. Starts from 0.
     */
    public MethodDefinition(Type type, Location location, Signature signature, int index) {
        super(type, location);
        this.signature = signature;
        this.index = index;
        this.members = new EnvironmentExp(null);
    }

    /**
     * <p>Getter for the field <code>signature</code>.</p>
     *
     * @return a {@link fr.ensimag.deca.context.Signature} object
     */
    public Signature getSignature() {
        return signature;
    }

    /**
     * <p>Getter for the field <code>members</code>.</p>
     *
     * @return a {@link fr.ensimag.deca.context.EnvironmentExp} object
     */
    public EnvironmentExp getMembers() {
        return members;
    }

    /** {@inheritDoc} */
    @Override
    public String getNature() {
        return "method";
    }

    /** {@inheritDoc} */
    @Override
    public boolean isExpression() {
        return false;
    }

}
