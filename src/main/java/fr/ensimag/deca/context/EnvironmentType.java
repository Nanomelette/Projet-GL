package fr.ensimag.deca.context;

import java.util.HashMap;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.tools.SymbolTable.Symbol;
import fr.ensimag.deca.tree.AbstractIdentifier;
import fr.ensimag.deca.tree.DeclParam;
import fr.ensimag.deca.tree.Identifier;
import fr.ensimag.deca.tree.ListDeclParam;
import fr.ensimag.deca.tree.Location;
import fr.ensimag.ima.pseudocode.Label;


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
        Symbol symbString = compiler.getSymbolTable().create("String");
    	Symbol symbVoid = compiler.getSymbolTable().create("void");
        Symbol symbObject = compiler.getSymbolTable().create("Object");
        Symbol symbOther = compiler.getSymbolTable().create("Other");

    	Type typeInt = new IntType(symbInt);
    	Type typeFloat = new FloatType(symbFloat) ;
    	Type typeBoolean = new BooleanType(symbBoolean) ;
        Type typeString = new StringType(symbString) ;
    	Type typeVoid = new VoidType(symbVoid) ;
        ClassType classTypeObject = new ClassType(symbObject,  Location.BUILTIN, null);
    	
    	setEnvironmentType(symbInt,typeInt,Location.BUILTIN);
    	setEnvironmentType(symbFloat,typeFloat,Location.BUILTIN);
		setEnvironmentType(symbBoolean,typeBoolean,Location.BUILTIN);
        setEnvironmentType(symbString,typeString,Location.BUILTIN);
    	setEnvironmentType(symbVoid,typeVoid,Location.BUILTIN);
        setEnvironmentType(symbObject, classTypeObject, Location.BUILTIN);

    	ClassDefinition defObject = classTypeObject.getDefinition();

		AbstractIdentifier identObject = new Identifier (symbObject) ;
		AbstractIdentifier identOther  = new Identifier(symbOther);
		
		ParamDefinition defOther = new ParamDefinition(classTypeObject, Location.BUILTIN);
		
        classTypeObject.getDefinition().setIndexMethods(2);
        identOther.setDefinition(defOther);
		
		ListDeclParam listParamEquals  = new ListDeclParam() ;
    }
    
    public TypeDefinition getDefinition( Symbol s ){
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
    	TypeDefinition typeDef = this.env_Type.get(s);
        if (typeDef == null) {
            return null;
        }
        return typeDef.getType();
    }

    public TypeDefinition get(Symbol create) {
        return env_Type.get(create);
        
    }
}
