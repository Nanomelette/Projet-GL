package fr.ensimag.deca.context;

import java.util.HashMap;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.tools.SymbolTable.Symbol;
import fr.ensimag.deca.tree.Location;
import fr.ensimag.ima.pseudocode.Label;


/**
 * <p>EnvironmentType class.</p>
 *
 * @author oscarmaggiori
 * @version $Id: $Id
 */
public class EnvironmentType {
    
    HashMap<Symbol, TypeDefinition> env_Type = new HashMap<Symbol, TypeDefinition>() ;
    
    /**
     * <p>addEnvironmentType.</p>
     *
     * @param symbol a {@link fr.ensimag.deca.tools.SymbolTable.Symbol} object
     * @param t a {@link fr.ensimag.deca.context.TypeDefinition} object
     */
    public void addEnvironmentType ( Symbol symbol , TypeDefinition t)
    {
    	this.env_Type.put(symbol, t);
    	
    }
    
    /**
     * <p>Constructor for EnvironmentType.</p>
     *
     * @param compiler a {@link fr.ensimag.deca.DecacCompiler} object
     */
    public EnvironmentType(DecacCompiler compiler){
        
    	Symbol symbInt = compiler.getSymbolTable().create("int");
    	Symbol symbFloat = compiler.getSymbolTable().create("float");
    	Symbol symbBoolean = compiler.getSymbolTable().create("boolean");
        Symbol symbString = compiler.getSymbolTable().create("String");
    	Symbol symbVoid = compiler.getSymbolTable().create("void");
        Symbol symbObject = compiler.getSymbolTable().create("Object");

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
        EnvironmentExp envExp = defObject.getMembers();
        // Ajout de la méthode equals
        Symbol equals = compiler.getSymbolTable().create("equals");
        Signature sig = new Signature();
        sig.add(classTypeObject);
        Type boolType = new BooleanType(compiler.getSymbolTable().create("boolean"));
        MethodDefinition newDef = new MethodDefinition(boolType, Location.BUILTIN, sig, 1);
        Label label = new Label("Object.equals");
        newDef.setLabel(label);
        try {
            envExp.declare(equals, newDef);
            defObject.incNumberOfMethods();
        } catch (EnvironmentExp.DoubleDefException e) {}
		
		
        classTypeObject.getDefinition().setIndexMethods(2);
    }
    
    /**
     * <p>getDefinition.</p>
     *
     * @param s a {@link fr.ensimag.deca.tools.SymbolTable.Symbol} object
     * @return a {@link fr.ensimag.deca.context.TypeDefinition} object
     */
    public TypeDefinition getDefinition( Symbol s ){
		return this.getEnvironmentType().get(s);
    }
    
    /**
     * <p>setEnvironmentType.</p>
     *
     * @param s a {@link fr.ensimag.deca.tools.SymbolTable.Symbol} object
     * @param type a {@link fr.ensimag.deca.context.Type} object
     * @param location a {@link fr.ensimag.deca.tree.Location} object
     */
    public void setEnvironmentType(Symbol s , Type type , Location location){
    	TypeDefinition typeDef = new TypeDefinition(type,location);
    	this.env_Type.put(s, typeDef);
    }
    
    /**
     * <p>getEnvironmentType.</p>
     *
     * @return a {@link java.util.HashMap} object
     */
    public HashMap<Symbol, TypeDefinition> getEnvironmentType(){
    	return this.env_Type;
    }
    
    /**
     * <p>getType.</p>
     *
     * @param s a {@link fr.ensimag.deca.tools.SymbolTable.Symbol} object
     * @return a {@link fr.ensimag.deca.context.Type} object
     */
    public Type getType(Symbol s){
    	TypeDefinition typeDef = this.env_Type.get(s);
        if (typeDef == null) {
            return null;
        }
        return typeDef.getType();
    }

    /**
     * <p>get.</p>
     *
     * @param create a {@link fr.ensimag.deca.tools.SymbolTable.Symbol} object
     * @return a {@link fr.ensimag.deca.context.TypeDefinition} object
     */
    public TypeDefinition get(Symbol create) {
        return env_Type.get(create);
        
    }
}
