package com.taraskudelia;

import com.taraskudelia.ini.IniFileParser;
import com.taraskudelia.ini.IniFileWriter;
import com.taraskudelia.ini.IniMerger;
import com.taraskudelia.ini.IniModel;
import org.apache.logging.log4j.message.StringFormatterMessageFactory;

import java.io.File;
import java.io.IOException;

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
        validateArgs(args);

        // Get input files and create file for the output.
        final File inFile = new File("");
        final File modFile = new File("");
        File outFile = new File("");

        // Parsing IniModels from the files
        IniModel inModel = IniFileParser.parse(inFile);
        IniModel modModel = IniFileParser.parse(modFile);

        // Trying to apply merge
        IniModel outModel = IniMerger.merge(inModel, modModel);
        try {
            IniFileWriter.saveModel(outModel, outFile);
        } catch (IOException e) {
            e.printStackTrace();
            printUsage();
            System.exit(1);
        }

        // Done. Print message and exit like a good citizen
        System.out.println(StringFormatterMessageFactory.INSTANCE.newMessage(SUCCESS_PATTERN, outFile.getName(),
                outFile.getAbsolutePath()));
        System.exit(0);
    }

    private static void validateArgs(String[] args) {
        // TODO
    }

    /**
     * Prints usage for this tool in the console.
     */
    private static void printUsage() {
        System.out.println(StringFormatterMessageFactory.INSTANCE.newMessage(USAGE_PATTERN, "path_to_the_base",
                "path_to_the_supplementary", "name_of_the_result"));
    }

}
