package fr.ensimag.deca;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.lang.Runtime;

import org.apache.log4j.Logger;

/**
 * Main class for the command-line Deca compiler.
 *
 * @author gl20
 * @date 01/01/2022
 * @version $Id: $Id
 */
public class DecacMain {
    private static Logger LOG = Logger.getLogger(DecacMain.class);
    
    /**
     * <p>main.</p>
     *
     * @param args an array of {@link java.lang.String} objects
     */
    public static void main(String[] args) {
        // example log4j message.
        LOG.info("Decac compiler started");
        boolean error = false;
        final CompilerOptions options = new CompilerOptions();
        try {
            options.parseArgs(args);
        } catch (CLIException e) {
            System.err.println("Error during option parsing:\n"
                    + e.getMessage());
            options.displayUsage();
            System.exit(1);
        }
        if (options.getPrintBanner()) {
            // System.out.println("Groupe 4 Equipe 20");
            // System.out.println("Hagenburg Arthur / Maggiori Oscar / Martineau Thomas / Gariel Arnaud / Kuhnast Antoine");
            URL location = DecacMain.class.getProtectionDomain().getCodeSource().getLocation();
            try (Scanner input = new Scanner(new File(location.getPath()+"../../src/main/java/fr/ensimag/deca/banner.txt"))) {
                while (input.hasNextLine())
                {
                    System.out.println(input.nextLine());
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            System.exit(0);
        }
        if (options.getSourceFiles().isEmpty()) {
            options.displayUsage();
        }
        if (options.getParallel()) {
            ExecutorService exec = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
            List<Future<?>> ens = new LinkedList<Future<?>>(); 
            for (File source : options.getSourceFiles()) {
                ens.add(exec.submit(new Runnable() {
                    @Override
                    public void run() {
                        DecacCompiler compiler = new DecacCompiler(options, source);
                        compiler.compile();
                    }
                }));
            }
            for (Future<?> fut : ens) {
                try {
                    fut.get();
                } catch (Exception e) {
                    error = true;
                }
            }
            
        } else {
            for (File source : options.getSourceFiles()) {
                DecacCompiler compiler = new DecacCompiler(options, source);
                if (compiler.compile()) {
                    error = true;
                }
            }
        }
        System.exit(error ? 1 : 0);
    }
}
