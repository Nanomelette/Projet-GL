package fr.ensimag.deca.context;

import fr.ensimag.deca.tools.SymbolTable;

public class ObjectType extends Type {

    public ObjectType(SymbolTable.Symbol name) {
        super(name);
    }

    @Override
    public boolean isObject() {
        return true;
    }

    @Override
    public boolean sameType(Type otherType) {
        return otherType.isInt();
    }



}
