package com.taraskudelia.reinimator.ini;

import org.ini4j.InvalidFileFormatException;
import org.ini4j.Wini;

import java.io.File;
import java.io.IOException;

/**
 * @author Taras Kudelia
 * @since 08 Oct 2019
 */
public class IniFileWriter {

    public static void saveModel(Wini outModel, File outFile) {
        try {
            // Create file for writing
            if (!outFile.exists() && !outFile.createNewFile()) {
                return;
            }
            // Here we need to adapt
            Wini ini = new Wini(outFile);
            ini.getConfig().setStrictOperator(true);
            outModel.values().forEach(section -> section.forEach((key, value) -> ini.put(section.getName(), key, value)));
            // Write to the file
            ini.store();
        } catch (InvalidFileFormatException e) {
            System.out.println("Invalid file format.");
        } catch (IOException e) {
            System.out.println("Problem reading file.");
        }
    }

}
