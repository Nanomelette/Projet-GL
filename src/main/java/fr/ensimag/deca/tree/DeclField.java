package fr.ensimag.deca.tree;

import org.apache.commons.lang.Validate;

public class DeclField {

    private Identifier field;
    private Initialization init;

    public DeclField(Identifier field, Initialization init){
        Validate.notNull(field);
        Validate.notNull(init);
        this.field = field;
        this.init = init;
    }
    
}
