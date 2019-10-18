package com.taraskudelia.ini;

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
            // Write to the file
            outModel.setFile(outFile);
            outModel.store();
        } catch (InvalidFileFormatException e) {
            System.out.println("Invalid file format.");
        } catch (IOException e) {
            System.out.println("Problem reading file.");
        }
    }

}
