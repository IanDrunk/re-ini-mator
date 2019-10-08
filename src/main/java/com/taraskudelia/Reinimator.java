package com.taraskudelia;

import com.taraskudelia.ini.IniFileWriter;
import com.taraskudelia.ini.IniMerger;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.message.StringFormatterMessageFactory;
import org.ini4j.Ini;

import java.io.File;
import java.io.IOException;

/**
 * @author Taras Kudelia
 * @since 07 Oct 2019
 */
@Slf4j
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

        // Define output file
        File outFile = new File(args[2]);

        // Parsing IniModels from the files
        try {
            Ini inModel = new Ini(new File(args[0]));
            Ini supModel = new Ini(new File(args[1]));

            // TODO ====================================================================================================
            // Trying to apply merge
            Ini outModel = IniMerger.merge(inModel, supModel);
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
        // arg count
        if (args.length != 3) {
            return "Wrong number of arguments";
        }

        // Files check
        final String inFilePath = args[0];
        final String supFileName = args[1];
        final String outFileName = args[2];

        if (!isValidInputFile(inFilePath)) {
            return "File " + inFilePath + " does not exists.";
        }
        if (!isValidInputFile(supFileName)) {
            return "File " + supFileName + " does not exists.";
        }
        if (!isValidOutputFile(outFileName)) {
            return "File " + outFileName + " already exists.";
        }

        // File names collide
        if (inFilePath.equals(supFileName) || inFilePath.equals(outFileName) || supFileName.equals(outFileName)) {
            return "All .ini files must be different files.";
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
