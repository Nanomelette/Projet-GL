package fr.ensimag.deca;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

/**
 * User-specified options influencing the compilation.
 *
 * @author gl20
 * @date 01/01/2022
 */
public class CompilerOptions {
    public static final int QUIET = 0;
    public static final int INFO  = 1;
    public static final int DEBUG = 2;
    public static final int TRACE = 3;
    public int getDebug() {
        return debug;
    }

    public boolean getParallel() {
        return parallel;
    }

    public boolean getPrintBanner() {
        return printBanner;
    }
    
    public List<File> getSourceFiles() {
        return Collections.unmodifiableList(sourceFiles);
    }

    public boolean getParse() {
        return parse;
    }

    public boolean getVerification() {
        return verification;
    }

    public int getMaxRegister() {
        return maxRegister;
    }

    public boolean getNoCheck() {
        return noCheck;
    }

    public boolean getTree() {
        return tree;
    }

    private int debug = 0;
    private boolean parallel = false;
    private boolean printBanner = false;
    private List<File> sourceFiles = new ArrayList<File>();
    private boolean parse = false;
    private boolean verification = false;
    private boolean noCheck = false;
    private int maxRegister = 16;
    private boolean tree = false;
    
    public void parseArgs(String[] args) throws CLIException {
        if (args.length != 0) {
            if ((args.length == 1)) {
                if (args[0].equals("-b")) {
                    printBanner = true;
                } else {
                    // Only one filepath is given
                    File sourceFile = new File(args[0]);
                    sourceFiles.add(sourceFile);
                }
            } else {
                // More than one argument
                boolean pOrVUsed = false;
                int firstSourceIndex = 0;
                for (int i = 0; i<args.length; i++) {
                    if (args[i].charAt(0) == '-') {
                        if (args[i].length() == 2) {
                            if ((args[i].charAt(1) == 'p') && !pOrVUsed) {
                                // -p option : parse
                                pOrVUsed = true;
                                parse = true;
                            } else if ((args[i].charAt(1) == 'v') && !pOrVUsed) {
                                // -v option : verification
                                pOrVUsed = true;
                                verification = true;
                            } else if (args[i].charAt(1) == 'n') {
                                // -n option : no check
                                noCheck = true;
                            } else if (args[i].charAt(1) == 'r') {
                                // - r X option (must parse the X) : registers 
                                if (args[i+1] != null) {
                                    try {
                                        int xOption = Integer.parseInt(args[i+1]);
                                        if ((xOption >= 4) && (xOption <= 16)) {
                                            i += 1;
                                            maxRegister = xOption;
                                        } else {
                                            throw new CLIException("Incorrect number of registers");
                                        }
                                    } catch (NumberFormatException e) {
                                        throw new CLIException("Incorrect number of registers");
                                    }
                                    
                                } else {
                                    throw new CLIException("Incorrect arguments");
                                }
                            } else if (args[i].charAt(1) == 'd') {
                                // d option : debug
                                if (debug < 3) {
                                    debug += 1;
                                }
                            } else if (args[i].charAt(1) == 'P') {
                                parallel = true;
                            } else if (args[i].charAt(1) == 't') {
                                tree = true;
                            } else {
                                throw new CLIException("Incorrect arguments");
                            }
                        } else {
                            throw new CLIException("Incorrect arguments");
                        }
                    } else {
                        // It's a filepath, we stop the option managment
                        firstSourceIndex = i;
                        break;
                    }
                }
                // filepath management
                for (int i = firstSourceIndex; i<args.length; i++) {
                    File sourceFile = new File(args[i]);
                    if (sourceFile.getName().length() > 5) {
                        if (sourceFile.getName().substring(sourceFile.getName().length()-5, sourceFile.getName().length()).equals(".deca")) {
                            if (!sourceFiles.contains(sourceFile)) {
                                sourceFiles.add(sourceFile);
                            }
                        } else {
                            throw new CLIException("Incorrect extension : .deca expected");
                        }
                    } else {
                        throw new CLIException("Incorrect extension : .deca expected");
                    }
                }
            }
        }
        Logger logger = Logger.getRootLogger();
        // map command-line debug option to log4j's level.
        switch (getDebug()) {
        case QUIET: break; // keep default
        case INFO:
            logger.setLevel(Level.INFO); break;
        case DEBUG:
            logger.setLevel(Level.DEBUG); break;
        case TRACE:
            logger.setLevel(Level.TRACE); break;
        default:
            logger.setLevel(Level.ALL); break;
        }
        logger.info("Application-wide trace level set to " + logger.getLevel());

        boolean assertsEnabled = false;
        assert assertsEnabled = true; // Intentional side effect!!!
        if (assertsEnabled) {
            logger.info("Java assertions enabled");
        } else {
            logger.info("Java assertions disabled");
        }
    }

    protected void displayUsage() {
        URL location = DecacMain.class.getProtectionDomain().getCodeSource().getLocation();
        try (Scanner input = new Scanner(new File(location.getPath()+"../../src/main/java/fr/ensimag/deca/docDecac.txt"))) {
            while (input.hasNextLine())
            {
                System.out.println(input.nextLine());
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
