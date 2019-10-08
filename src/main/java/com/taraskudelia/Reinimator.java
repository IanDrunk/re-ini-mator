package com.taraskudelia;

import com.taraskudelia.ini.IniFileParser;
import com.taraskudelia.ini.IniFileWriter;
import com.taraskudelia.ini.IniMerger;
import com.taraskudelia.ini.IniModel;
import org.apache.logging.log4j.message.StringFormatterMessageFactory;

import java.io.File;
import java.io.IOException;
import java.lang.invoke.WrongMethodTypeException;

/**
 * @author Taras Kudelia
 * @since 07 Oct 2019
 */
public class Reinimator {

    /* pattern for the printUsage function */
    private final static String USAGE_PATTERN = "java -jar %s.jar %s.ini %s.ini";
    private final static String SUCCESS_PATTERN = "Done! File %s was created at %s.";

    /**
     * Entry point.
     * @param args - command-line arguments
     *               [0] base ini file
     *               [1] supplementary ini file
     *               [2] name of the result .ini file
     * @implNote recurring or existing file names are forbidden.
     */
    public static void main(String[] args) {
        // If errors found - exit with error + print usage
        final String errorMessage = validateArgs(args);
        if (errorMessage != null) {
            exitWithError(errorMessage);
        }

        // Get input files and create file for the output.
        final File inFile = new File(args[0]);
        final File supFile = new File(args[1]);
        File outFile = new File(args[2]);

        // Parsing IniModels from the files
        IniModel inModel = IniFileParser.parse(inFile);
        IniModel supModel = IniFileParser.parse(supFile);

        // Trying to apply merge
        IniModel outModel = IniMerger.merge(inModel, supModel);
        try {
            IniFileWriter.saveModel(outModel, outFile);
        } catch (IOException e) {
            e.printStackTrace();
            exitWithError(e.getMessage());
        }

        // Done. Print message and exit like a good citizen
        final String outFilePath = outFile.getAbsolutePath();
        final String outFileName = outFile.getName();
        System.out.println(StringFormatterMessageFactory.INSTANCE.newMessage(SUCCESS_PATTERN, outFileName, outFilePath));
        System.exit(0);
    }

    /**
     * Searches for the obvious errors in the command-line arguments.
     * @param args - command-line arguments.
     * @return error message String with the error description if spotted;
     *          null - if all tests are passed.
     */
    private static String validateArgs(String[] args) {
        if (args.length != 3) {
            // arg count
            return "wrong number of arguments";
        }
        // Files check
        try {
            final String inFilePath = args[0];
            final String supFileName = args[1];
            final String outFileName = args[2];

            if (!isValidInputFile(inFilePath)) {
                return "File " + inFilePath + " does not exists.";
            } else if (!isValidInputFile(supFileName)) {
                return "File " + supFileName + " does not exists.";
            } else if (!isValidOutputFile(outFileName)) {
                return "File " + outFileName + " already exists.";
            }

            // File names collide
            if (inFilePath.equals(supFileName) || inFilePath.equals(outFileName) || supFileName.equals(outFileName)) {
                return "All .ini files must be different files.";
            }
        } catch (WrongMethodTypeException e) {
            e.printStackTrace();
            return e.getMessage();
        }
        return null;
    }

    /**
     * Check if given path is valid .ini file.
     * @param pathToFile - absolute path to the .ini file.
     * @return true if all criteria was met; false - otherwise.
     */
    private static boolean isValidInputFile(final String pathToFile) {
        File iniFile = new File(pathToFile);
        final String fileName = iniFile.getName();
        return iniFile.exists() && iniFile.isFile() && iniFile.canRead()
                && fileName.substring(fileName.lastIndexOf(".")).equalsIgnoreCase(".ini");
    }

    /**
     * Check if given path can be used to create an output .ini file.
     * @param pathToFile - absolute path to the .ini file.
     * @return true if all criteria was met; false - otherwise.
     */
    private static boolean isValidOutputFile(final String pathToFile) {
        File iniFile = new File(pathToFile);
        File parentFile = iniFile.getParentFile();
        return !iniFile.exists() && parentFile.isDirectory() && parentFile.canWrite() && parentFile.canRead();
    }

    /**
     * Prints error message with usage info to the console and shuts down the application.
     * @param message - error message to be printed.
     */
    private static void exitWithError(final String message) {
        System.out.println(message);
        printUsage();
        System.exit(1);
    }

    /**
     * Prints usage for this tool in the console.
     */
    private static void printUsage() {
        System.out.println(StringFormatterMessageFactory.INSTANCE.newMessage(USAGE_PATTERN, "path_to_the_base",
                "path_to_the_supplementary", "name_of_the_result"));
    }

}
