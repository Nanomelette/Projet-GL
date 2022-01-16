package fr.ensimag.deca.context;

import java.util.HashMap;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.tools.SymbolTable.Symbol;
import fr.ensimag.deca.tree.Location;


public class EnvironmentType {
    
    HashMap<Symbol, TypeDefinition> env_Type = new HashMap<Symbol, TypeDefinition>() ;
    
    public void addEnvironmentType ( Symbol symbol , TypeDefinition t)
    {
    	this.env_Type.put(symbol, t);
    	
    }
    
    public EnvironmentType(DecacCompiler compiler){
        
    	Symbol symbInt = compiler.getSymbolTable().create("int");
    	Symbol symbFloat = compiler.getSymbolTable().create("float");
    	Symbol symbBoolean = compiler.getSymbolTable().create("boolean");
    	Symbol symbVoid = compiler.getSymbolTable().create("void");

    	Type typeInt = new IntType(symbInt);
    	Type typeFloat = new FloatType(symbFloat) ;
    	Type typeBoolean = new BooleanType(symbBoolean) ;
    	Type typeVoid = new VoidType(symbVoid) ;
    	
    	setEnvironmentType(symbInt,typeInt,Location.BUILTIN);
    	setEnvironmentType(symbFloat,typeFloat,Location.BUILTIN);
		setEnvironmentType(symbBoolean,typeBoolean ,Location.BUILTIN);
    	setEnvironmentType(symbVoid,typeVoid,Location.BUILTIN);
		
    }
    
    public TypeDefinition getDefinition( Symbol s ){
    	System.out.println("GetDef");
		return this.getEnvironmentType().get(s);
    }
    
    public void setEnvironmentType(Symbol s , Type type , Location location){
    	TypeDefinition typeDef = new TypeDefinition(type,location);
    	this.env_Type.put(s, typeDef);
    }
    
    public HashMap<Symbol, TypeDefinition> getEnvironmentType(){
    	return this.env_Type;
    }
    
    public Type getType(Symbol s){
    	return this.env_Type.get(s).getType();
    }

    public TypeDefinition get(Symbol create) {
        return env_Type.get(create);
        
    }

}
