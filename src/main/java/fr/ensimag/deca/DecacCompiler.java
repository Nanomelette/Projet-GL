package fr.ensimag.deca;

import fr.ensimag.deca.codegen.Data;
import fr.ensimag.deca.context.ClassType;
import fr.ensimag.deca.context.EnvironmentExp;
import fr.ensimag.deca.context.EnvironmentType;
import fr.ensimag.deca.context.Type;
import fr.ensimag.deca.syntax.DecaLexer;
import fr.ensimag.deca.syntax.DecaParser;
import fr.ensimag.deca.tools.DecacInternalError;
import fr.ensimag.deca.tools.SymbolTable;
import fr.ensimag.deca.tools.SymbolTable.Symbol;
import fr.ensimag.deca.tree.AbstractProgram;
import fr.ensimag.deca.tree.LocationException;
import fr.ensimag.ima.pseudocode.AbstractLine;
import fr.ensimag.ima.pseudocode.IMAProgram;
import fr.ensimag.ima.pseudocode.Instruction;
import fr.ensimag.ima.pseudocode.Label;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.apache.log4j.Logger;

/**
 * Decac compiler instance.
 *
 * This class is to be instantiated once per source file to be compiled. It
 * contains the meta-data used for compiling (source file name, compilation
 * options) and the necessary utilities for compilation (symbol tables, abstract
 * representation of target file, ...).
 *
 * It contains several objects specialized for different tasks. Delegate methods
 * are used to simplify the code of the caller (e.g. call
 * compiler.addInstruction() instead of compiler.getProgram().addInstruction()).
 *
 * @author gl20
 * @date 01/01/2022
 * @version $Id: $Id
 */
public class DecacCompiler {
    private static final Logger LOG = Logger.getLogger(DecacCompiler.class);

    // private static final SymbolTable symbol_table = new SymbolTable();
    private Data data = new Data();

    private Data dataBloc = new Data();

    /**
     * <p>Getter for the field <code>data</code>.</p>
     *
     * @return a {@link fr.ensimag.deca.codegen.Data} object
     */
    public Data getData() {
        if (writeInBloc) {
            return dataBloc;
        }
        return data;
    }

    private int nLabel = 0;
    
    /**
     * <p>Getter for the field <code>nLabel</code>.</p>
     *
     * @return a int
     */
    public int getNLabel() {
        return nLabel;
    }

    /**
     * <p>incrNLabel.</p>
     */
    public void incrNLabel() {
        nLabel++;
    }


    /**
     * Portable newline character.
     */
    private static final String nl = System.getProperty("line.separator", "\n");

    /**
     * <p>Constructor for DecacCompiler.</p>
     *
     * @param compilerOptions a {@link fr.ensimag.deca.CompilerOptions} object
     * @param source a {@link java.io.File} object
     */
    public DecacCompiler(CompilerOptions compilerOptions, File source) {
        super();
        this.compilerOptions = compilerOptions;
        this.source = source;
        if (compilerOptions != null) {
            this.data.setMaxRegister(compilerOptions.getMaxRegister());
            this.dataBloc.setMaxRegister(compilerOptions.getMaxRegister());
        }
        this.symbolTable = new SymbolTable();
        this.env_Types = new EnvironmentType(this);
        this.Env_exp= new EnvironmentExp(null);
    }

    /**
     * <p>Getter for the field <code>symbolTable</code>.</p>
     *
     * @return a {@link fr.ensimag.deca.tools.SymbolTable} object
     */
    public SymbolTable getSymbolTable(){
        return this.symbolTable;
    }

    /**
     * <p>GetEnvTypes.</p>
     *
     * @return a {@link fr.ensimag.deca.context.EnvironmentType} object
     */
    public EnvironmentType GetEnvTypes(){
        return this.env_Types ;
    }
    
	/**
	 * <p>searchSymbol.</p>
	 *
	 * @param type a {@link fr.ensimag.deca.tools.SymbolTable.Symbol} object
	 * @return a {@link fr.ensimag.deca.context.Type} object
	 */
	public Type searchSymbol(Symbol type) {
		return env_Types.getType(type);
	}

    /**
     * Source file associated with this compiler instance.
     *
     * @return a {@link java.io.File} object
     */
    public File getSource() {
        return source;
    }

    /**
     * Compilation options (e.g. when to stop compilation, number of registers
     * to use, ...).
     *
     * @return a {@link fr.ensimag.deca.CompilerOptions} object
     */
    public CompilerOptions getCompilerOptions() {
        return compilerOptions;
    }

    /**
     * <p>add.</p>
     *
     * @param line a {@link fr.ensimag.ima.pseudocode.AbstractLine} object
     */
    public void add(AbstractLine line) {
        if (writeInBloc) {
            bloc.add(line);
        } else {
            program.add(line);
        }
    }

    /**
     * <p>addComment.</p>
     *
     * @see fr.ensimag.ima.pseudocode.IMAProgram#addComment(java.lang.String)
     * @param comment a {@link java.lang.String} object
     */
    public void addComment(String comment) {
        if (writeInBloc) {
            bloc.addComment(comment);
        } else {
            program.addComment(comment);
        }
    }

    /**
     * <p>addLabel.</p>
     *
     * @param label a {@link fr.ensimag.ima.pseudocode.Label} object
     */
    public void addLabel(Label label) {
        if (writeInBloc) {
            bloc.addLabel(label);
        } else {
            program.addLabel(label);
        }
    }

    /**
     * <p>addLabelAtFirst.</p>
     *
     * @param label a {@link fr.ensimag.ima.pseudocode.Label} object
     */
    public void addLabelAtFirst(Label label) {
        if (writeInBloc) {
            bloc.addLabelAtFirst(label);
        } else {
            program.addLabelAtFirst(label);
        }
    }

    /**
     * <p>addInstruction.</p>
     *
     * @param instruction a {@link fr.ensimag.ima.pseudocode.Instruction} object
     */
    public void addInstruction(Instruction instruction) {
        if (writeInBloc) {
            bloc.addInstruction(instruction);
        } else {
            program.addInstruction(instruction);
        }
    }

    /**
     * <p>addInstruction.</p>
     *
     * @param instruction a {@link fr.ensimag.ima.pseudocode.Instruction} object
     * @param comment a {@link java.lang.String} object
     */
    public void addInstruction(Instruction instruction, String comment) {
        if (writeInBloc) {
            bloc.addInstruction(instruction, comment);
        } else {
            program.addInstruction(instruction, comment);
        }
    }

    /**
     * <p>addInstructionAtFirst.</p>
     *
     * @param instruction a {@link fr.ensimag.ima.pseudocode.Instruction} object
     */
    public void addInstructionAtFirst(Instruction instruction) {
        if (writeInBloc) {
            bloc.addFirst(instruction);
        } else {
            program.addFirst(instruction);
        }
    }

    /**
     * <p>addInstructionAtFirst.</p>
     *
     * @param instruction a {@link fr.ensimag.ima.pseudocode.Instruction} object
     * @param comment a {@link java.lang.String} object
     */
    public void addInstructionAtFirst(Instruction instruction, String comment) {
        if (writeInBloc) {
            bloc.addFirst(instruction, comment);
        } else {
            program.addFirst(instruction, comment);
        }
    }

    /**
     * <p>appendBlocInstructions.</p>
     */
    public void appendBlocInstructions() {
        program.append(bloc);
    }

    /**
     * <p>addCommentAtFirst.</p>
     *
     * @param comment a {@link java.lang.String} object
     */
    public void addCommentAtFirst(String comment) {
        if (writeInBloc) {
            bloc.addFirst(null, comment);
        } else {
            program.addFirst(null, comment);
        }
    }

    
    /**
     * <p>displayIMAProgram.</p>
     *
     * @return a {@link java.lang.String} object
     */
    public String displayIMAProgram() {
        return program.display();
    }
    
    private final CompilerOptions compilerOptions;
    private final File source;
    private SymbolTable symbolTable;
    private EnvironmentType env_Types;
    private EnvironmentExp Env_exp;
    /**
     * The main program. Every instruction generated will eventually end up here.
     */
    private final IMAProgram program = new IMAProgram();

    /**
     * The substiture program. Instructions generated in a bloc will be written
     * in the bloc then appened to the main program.
     */
    private IMAProgram bloc = new IMAProgram();

    /**
     * A boolean to know where to write
     */
    private Boolean writeInBloc = false;

    /**
     * <p>Setter for the field <code>writeInBloc</code>.</p>
     *
     * @param writeInBloc a {@link java.lang.Boolean} object
     */
    public void setWriteInBloc(Boolean writeInBloc) {
        this.writeInBloc = writeInBloc;
    }

    /**
     * <p>setToMainProgram.</p>
     */
    public void setToMainProgram() {
        this.writeInBloc = false;
    }

    /**
     * <p>setToBlocProgram.</p>
     */
    public void setToBlocProgram() {
        this.writeInBloc = true;
    }

    /**
     * <p>newBloc.</p>
     */
    public void newBloc() {
        dataBloc = new Data();
        dataBloc.setMaxRegister(compilerOptions.getMaxRegister());
        bloc = new IMAProgram();
    }
 

    /**
     * Run the compiler (parse source file, generate code)
     *
     * @return true on error
     */
    public boolean compile() {
        String sourceFile = source.getAbsolutePath();
        // calculee le nom du fichier .ass à partir du nom du fichier .deca.
        String destFile = sourceFile.substring(0, sourceFile.length()-4);
        destFile += "ass";
        PrintStream err = System.err;
        PrintStream out = System.out;
        LOG.debug("Compiling file " + sourceFile + " to assembly file " + destFile);
        try {
            return doCompile(sourceFile, destFile, out, err);
        } catch (LocationException e) {
            e.display(err);
            return true;
        } catch (DecacFatalError e) {
            err.println(e.getMessage());
            return true;
        } catch (StackOverflowError e) {
            LOG.debug("stack overflow", e);
            err.println("Stack overflow while compiling file " + sourceFile + ".");
            return true;
        } catch (Exception e) {
            LOG.fatal("Exception raised while compiling file " + sourceFile
                    + ":", e);
            err.println("Internal compiler error while compiling file " + sourceFile + ", sorry.");
            return true;
        } catch (AssertionError e) {
            LOG.fatal("Assertion failed while compiling file " + sourceFile
                    + ":", e);
            err.println("Internal compiler error while compiling file " + sourceFile + ", sorry.");
            return true;
        }
    }

    /**
     * Internal function that does the job of compiling (i.e. calling lexer,
     * verification and code generation).
     *
     * @param sourceName name of the source (deca) file
     * @param destName name of the destination (assembly) file
     * @param out stream to use for standard output (output of decac -p)
     * @param err stream to use to display compilation errors
     *
     * @return true on error
     */
    private boolean doCompile(String sourceName, String destName,
            PrintStream out, PrintStream err)
            throws DecacFatalError, LocationException {
        // A
        AbstractProgram prog = doLexingAndParsing(sourceName, err);

        if (prog == null) {
            LOG.info("Parsing failed");
            return true;
        }
        // assert(prog.checkAllLocations());

        if (compilerOptions.getTree()) {
            prog.prettyPrint(System.out);
            System.exit(0);
        }

        if (compilerOptions.getParse()) {
            prog.decompile(System.out);
            System.exit(0);
        }

        // B
        prog.verifyProgram(this);
        assert(prog.checkAllDecorations());
        if (compilerOptions.getVerification()) {
            System.exit(0);
        }

        if (compilerOptions.getDecoTree()) {
            prog.prettyPrint(System.out);
            System.exit(0);
        }

        // C
        prog.codeGenProgram(this);
        addComment("end main program");
        LOG.debug("Generated assembly code:" + nl + program.display());
        LOG.info("Output file assembly file is: " + destName);

        FileOutputStream fstream = null;
        try {
            fstream = new FileOutputStream(destName);
        } catch (FileNotFoundException e) {
            throw new DecacFatalError("Failed to open output file: " + e.getLocalizedMessage());
        }

        LOG.info("Writing assembler file ...");

        program.display(new PrintStream(fstream));
        LOG.info("Compilation of " + sourceName + " successful.");
        return false;
    }

    /**
     * Build and call the lexer and parser to build the primitive abstract
     * syntax tree.
     *
     * @param sourceName Name of the file to parse
     * @param err Stream to send error messages to
     * @return the abstract syntax tree
     * @throws fr.ensimag.deca.DecacFatalError When an error prevented opening the source file
     * @throws fr.ensimag.deca.tools.DecacInternalError When an inconsistency was detected in the
     * compiler.
     */
    protected AbstractProgram doLexingAndParsing(String sourceName, PrintStream err)
            throws DecacFatalError, DecacInternalError {
        DecaLexer lex;
        try {
            lex = new DecaLexer(CharStreams.fromFileName(sourceName));
        } catch (IOException ex) {
            throw new DecacFatalError("Failed to open input file: " + ex.getLocalizedMessage());
        }
        lex.setDecacCompiler(this);
        CommonTokenStream tokens = new CommonTokenStream(lex);
        DecaParser parser = new DecaParser(tokens);
        parser.setDecacCompiler(this);
        return parser.parseProgramAndManageErrors(err);
    }

    /**
     * <p>GetEnvExp.</p>
     *
     * @return a {@link fr.ensimag.deca.context.EnvironmentExp} object
     */
    public EnvironmentExp GetEnvExp() {
        return Env_exp;
    }

    /**
     * <p>assignCompatible.</p>
     *
     * @param compiler a {@link fr.ensimag.deca.DecacCompiler} object
     * @param left a {@link fr.ensimag.deca.context.Type} object
     * @param right a {@link fr.ensimag.deca.context.Type} object
     * @return a {@link fr.ensimag.deca.context.Type} object
     */
    public Type assignCompatible(DecacCompiler compiler, Type left, Type right){
        if(left == null || right == null){
            return null;
        }
        if(right.isInt() && left.isFloat()){
            return right;
        }
        if (!left.isClass()) {
            if (right.sameType(left)) {
                return right;
            }
        }
        if (right.isClass() && left.isClass()) {
            ClassType rightClassType = (ClassType) right;
            if(rightClassType.isSubClassOf((ClassType) left)){
                return right;
            }
        }
        return null;
    }

    /**
     * <p>subType.</p>
     *
     * @param compiler a {@link fr.ensimag.deca.DecacCompiler} object
     * @param type a {@link fr.ensimag.deca.context.Type} object
     * @param typeSup a {@link fr.ensimag.deca.context.Type} object
     * @return a boolean
     */
    public boolean subType(DecacCompiler compiler, Type type, Type typeSup){
        if (type == null) {
            return false;
        }
        if (type.sameType(typeSup)) {
            return true;
        }
        if (type.isClass() && typeSup.isClass()) {
            ClassType subClassType = (ClassType) type;
            return subClassType.isSubClassOf((ClassType) typeSup);
        }
        return false;
    }

}
