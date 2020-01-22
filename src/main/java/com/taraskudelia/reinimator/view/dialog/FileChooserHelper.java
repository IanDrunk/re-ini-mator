package com.taraskudelia.reinimator.view.dialog;

import javafx.stage.FileChooser;
import javafx.stage.Window;
import org.springframework.lang.Nullable;

import java.io.File;

/**
 * Holds utils for the file choosing.
 *
 * @author Taras Kudelia
 * @since 11.01.2020
 */
public class FileChooserHelper {

    public static File getFile(@Nullable Window window, final String description, final String... extensions) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setSelectedExtensionFilter(new FileChooser.ExtensionFilter(description, extensions));
        return fileChooser.showOpenDialog(window);
    }

}
