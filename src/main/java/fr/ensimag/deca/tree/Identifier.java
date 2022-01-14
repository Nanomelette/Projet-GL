package fr.ensimag.deca.tree;

import fr.ensimag.deca.context.Type;
import fr.ensimag.deca.context.ClassType;
import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.ClassDefinition;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.context.Definition;
import fr.ensimag.deca.context.EnvironmentExp;
import fr.ensimag.deca.context.FieldDefinition;
import fr.ensimag.deca.context.MethodDefinition;
import fr.ensimag.deca.context.ExpDefinition;
import fr.ensimag.deca.context.VariableDefinition;
import fr.ensimag.deca.tools.DecacInternalError;
import fr.ensimag.deca.tools.IndentPrintStream;
import fr.ensimag.deca.tools.SymbolTable.Symbol;
import java.io.PrintStream;
import org.apache.commons.lang.Validate;
import org.apache.log4j.Logger;

/**
 * Deca Identifier
 *
 * @author gl20
 * @date 01/01/2022
 */
public class Identifier extends AbstractIdentifier {

    private static final Logger LOG = Logger.getLogger(Identifier.class);
    
    @Override
    protected void checkDecoration() {
        if (getDefinition() == null) {
            throw new DecacInternalError("Identifier " + this.getName() + " has no attached Definition");
        }
    }

    @Override
    public Definition getDefinition() {
        return definition;
    }

    /**
     * Like {@link #getDefinition()}, but works only if the definition is a
     * ClassDefinition.
     * 
     * This method essentially performs a cast, but throws an explicit exception
     * when the cast fails.
     * 
     * @throws DecacInternalError
     *             if the definition is not a class definition.
     */
    @Override
    public ClassDefinition getClassDefinition() {
        try {
            return (ClassDefinition) definition;
        } catch (ClassCastException e) {
            throw new DecacInternalError(
                    "Identifier "
                            + getName()
                            + " is not a class identifier, you can't call getClassDefinition on it");
        }
    }

    /**
     * Like {@link #getDefinition()}, but works only if the definition is a
     * MethodDefinition.
     * 
     * This method essentially performs a cast, but throws an explicit exception
     * when the cast fails.
     * 
     * @throws DecacInternalError
     *             if the definition is not a method definition.
     */
    @Override
    public MethodDefinition getMethodDefinition() {
        try {
            return (MethodDefinition) definition;
        } catch (ClassCastException e) {
            throw new DecacInternalError(
                    "Identifier "
                            + getName()
                            + " is not a method identifier, you can't call getMethodDefinition on it");
        }
    }

    /**
     * Like {@link #getDefinition()}, but works only if the definition is a
     * FieldDefinition.
     * 
     * This method essentially performs a cast, but throws an explicit exception
     * when the cast fails.
     * 
     * @throws DecacInternalError
     *             if the definition is not a field definition.
     */
    @Override
    public FieldDefinition getFieldDefinition() {
        try {
            return (FieldDefinition) definition;
        } catch (ClassCastException e) {
            throw new DecacInternalError(
                    "Identifier "
                            + getName()
                            + " is not a field identifier, you can't call getFieldDefinition on it");
        }
    }

    /**
     * Like {@link #getDefinition()}, but works only if the definition is a
     * VariableDefinition.
     * 
     * This method essentially performs a cast, but throws an explicit exception
     * when the cast fails.
     * 
     * @throws DecacInternalError
     *             if the definition is not a field definition.
     */
    @Override
    public VariableDefinition getVariableDefinition() {
        try {
            return (VariableDefinition) definition;
        } catch (ClassCastException e) {
            throw new DecacInternalError(
                    "Identifier "
                            + getName()
                            + " is not a variable identifier, you can't call getVariableDefinition on it");
        }
    }

    /**
     * Like {@link #getDefinition()}, but works only if the definition is a ExpDefinition.
     * 
     * This method essentially performs a cast, but throws an explicit exception
     * when the cast fails.
     * 
     * @throws DecacInternalError
     *             if the definition is not a field definition.
     */
    @Override
    public ExpDefinition getExpDefinition() {
        try {
            return (ExpDefinition) definition;
        } catch (ClassCastException e) {
            throw new DecacInternalError(
                    "Identifier "
                            + getName()
                            + " is not a Exp identifier, you can't call getExpDefinition on it");
        }
    }

    @Override
    public void setDefinition(Definition definition) {
        this.definition = definition;
    }

    @Override
    public Symbol getName() {
        return name;
    }

    private Symbol name;

    public Identifier(Symbol name) {
        Validate.notNull(name);
        this.name = name;
    }

    @Override
    public Type verifyExpr(DecacCompiler compiler, EnvironmentExp localEnv,
            ClassDefinition currentClass) throws ContextualError {

        //System.out.println(compiler.getSymbolTable().getMap().toString());
        System.out.println("1 - Key           := " + compiler.getSymbolTable().create(this.name.getName()));
        System.out.println("2 - EnvExp        := " + compiler.GetEnvExp().getDictionnary().toString());
        System.out.println("3 - EnvExp keys   := " + compiler.GetEnvExp().getDictionnary().keySet().toString());
        System.out.println("4 - EnvExp values := " + compiler.GetEnvExp().getDictionnary().values().toString());
        System.out.println("5 - Symbol Table  := " + compiler.getSymbolTable().getMap().toString());
        //System.out.println("4 - TEST := " + compiler.GetEnvExp().get(compiler.getSymbolTable().create(this.name.getName())));

        //System.out.println("-"+this.name.toString()+"-");
        //System.out.println("-"+compiler.GetEnvExp().getDictionnary().keySet().toArray()[0]+"-");
        // if(this.name.getName().equals(((Symbol)compiler.GetEnvExp().getDictionnary().keySet().toArray()[0]).getName())){
        //     System.out.println("enfin !");
        // }
        int i=0;
        while(i < compiler.GetEnvExp().getDictionnary().keySet().size()-1){
            i++;
            if(this.name.getName().equals(((Symbol)compiler.GetEnvExp().getDictionnary().keySet().toArray()[i]).getName())){
                break;
            }
        }
        //System.out.println((Symbol)compiler.getSymbolTable().create((((Symbol)compiler.GetEnvExp().getDictionnary().keySet().toArray()[i]).getName())));

        //System.out.println("type : " +compiler.GetEnvExp().getExpDefinition(((Symbol)compiler.GetEnvExp().getDictionnary().keySet().toArray()[i])).getType());

        if (this.name.getName().equals(((Symbol)compiler.GetEnvExp().getDictionnary().keySet().toArray()[i]).getName())) {
            System.out.println("Entering if");;
            setDefinition(compiler.GetEnvExp().getExpDefinition((Symbol)compiler.getSymbolTable().create((((Symbol)compiler.GetEnvExp().getDictionnary().keySet().toArray()[i]).getName()))));
            setType(compiler.GetEnvExp().getExpDefinition((((Symbol)compiler.GetEnvExp().getDictionnary().keySet().toArray()[i]))).getType());
            return compiler.GetEnvExp().getExpDefinition((((Symbol)compiler.GetEnvExp().getDictionnary().keySet().toArray()[i]))).getType();
        }
        else if(compiler.GetEnvExp().getExpDefinition(((Symbol)compiler.GetEnvExp().getDictionnary().keySet().toArray()[i]))!=null){
    		setDefinition(compiler.GetEnvExp().getExpDefinition((((Symbol)compiler.GetEnvExp().getDictionnary().keySet().toArray()[i]))));
    		setType(compiler.GetEnvExp().getExpDefinition(((Symbol)compiler.GetEnvExp().getDictionnary().keySet().toArray()[i])).getType());        	
    		return compiler.GetEnvExp().getExpDefinition(((Symbol)compiler.GetEnvExp().getDictionnary().keySet().toArray()[i])).getType();
        }   
        else{
            throw new ContextualError("identifier not defined", getLocation());
        }
        //throw new UnsupportedOperationException("not yet implemented");
    }

    /**
     * Implements non-terminal "type" of [SyntaxeContextuelle] in the 3 passes
     * @param compiler contains "env_types" attribute
     */
    @Override
    public Type verifyType(DecacCompiler compiler) throws ContextualError {
            Type type = compiler.searchSymbol(this.getName());
            if ( type == null )
                throw new ContextualError("Identifier-type error", this.getLocation());
            else 
                setType(type);
                return type ;
        //throw new UnsupportedOperationException("not yet implemented");
    }
    
    
    private Definition definition;


    @Override
    protected void iterChildren(TreeFunction f) {
        // leaf node => nothing to do
    }

    @Override
    protected void prettyPrintChildren(PrintStream s, String prefix) {
        // leaf node => nothing to do
    }

    @Override
    public void decompile(IndentPrintStream s) {
        s.print(name.toString());
    }

    @Override
    String prettyPrintNode() {
        return "Identifier (" + getName() + ")";
    }

    @Override
    protected void prettyPrintType(PrintStream s, String prefix) {
        Definition d = getDefinition();
        if (d != null) {
            s.print(prefix);
            s.print("definition: ");
            s.print(d);
            s.println();
        }
    }

}
