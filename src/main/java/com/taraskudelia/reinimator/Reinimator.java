package com.taraskudelia.reinimator;

import com.taraskudelia.reinimator.view.dialog.FileChooserHelper;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.stage.Window;
import lombok.NonNull;

import java.io.File;

/**
 * @author Taras Kudelia
 * @since 07 Oct 2019
 */
public class Reinimator extends Application {

    /* Scene dimensions and fitting */
    private static final int SCENE_WIDTH = 640;
    private static final int SCENE_HEIGHT = 400;
    private static final int PADDING = 20;
    private static final int H_GAP = 25;
    private static final int V_GAP = 15;

    /* String literals for the GUI */
    private static final String MAIN_INI_DESCRIPTION = "Choose path to the MAIN ini file";
    private static final String SUPP_INI_DESCRIPTION = "Choose path to the SECONDARY ini file:";
    private static final String NO_FILE_SELECTED = "NO FILE SELECTED";
    private static final String OUT_FILE_NAME_PLACEHOLDER = "Enter resulting ini file name";
    private static final String OPEN = "Open";
    private static final String MERGE = "Merge";

    private static final String[] FILE_EXTENSIONS = new String[] {
            ".ini", ".txt"
    };

    private static Label mainFilePathLabel = null;
    private static Label suppFilePathLabel = null;
    private static Button mergeBtn = null;
    private static TextField labelOutName = null;

    /**
     * Entry point.
     * @param args - command-line arguments
     */
    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage stage) {
        stage.setScene(getScene(stage));
        stage.show();
    }

    /**
     * Creates the Menu scene and fills it with controls.
     *
     * @param stage - root stage.
     * @return created scene.
     */
    private static Scene getScene(Stage stage) {
        GridPane root = new GridPane();
        root.setPadding(new Insets(PADDING));
        root.setHgap(H_GAP);
        root.setVgap(V_GAP);

        createMainFileRow(root, stage);
        createSuppFileRow(root, stage);
        createOutputFileRow(root);

        // TODO: add label with the brief usage text for the user.

        return new Scene(root, SCENE_WIDTH, SCENE_HEIGHT);
    }

    /**
     * Creates the main ini file row and fills it with controls.
     *
     * @param pane  - parent GridPane.
     * @param stage - root stage.
     */
    private static void createMainFileRow(final GridPane pane, final Stage stage) {
        final Label labelMainIni = new Label(MAIN_INI_DESCRIPTION);
        pane.add(labelMainIni, 0, 0, 2, 1);
        final Button openMainBtn = new Button(OPEN);
        pane.add(openMainBtn, 2, 0, 1, 1);
        mainFilePathLabel = new Label(NO_FILE_SELECTED);
        pane.add(mainFilePathLabel, 3, 0, 2, 1);
        openMainBtn.setOnAction(getOpenIniHandler(stage, MAIN_INI_DESCRIPTION, mainFilePathLabel));
    }

    /**
     * Creates the supplementary ini file row and fills it with controls.
     *
     * @param pane  - parent GridPane.
     * @param stage - root stage.
     */
    private static void createSuppFileRow(final GridPane pane, final Stage stage) {
        final Label labelSuppIni = new Label(SUPP_INI_DESCRIPTION);
        pane.add(labelSuppIni, 0, 1, 2, 1);
        final Button openSuppBtn = new Button(OPEN);
        pane.add(openSuppBtn, 2, 1, 1, 1);
        suppFilePathLabel = new Label(NO_FILE_SELECTED);
        pane.add(suppFilePathLabel, 3, 1, 2, 1);
        openSuppBtn.setOnAction(getOpenIniHandler(stage, SUPP_INI_DESCRIPTION, suppFilePathLabel));
    }

    /**
     * Creates the resulting ini file row and fills it with controls.
     *
     * @param pane  - parent GridPane.
     */
    private static void createOutputFileRow(final GridPane pane) {
        labelOutName = new TextField();
        labelOutName.setPromptText(OUT_FILE_NAME_PLACEHOLDER);
        pane.add(labelOutName, 0, 2,2,1);
        mergeBtn = new Button(MERGE);
        pane.add(mergeBtn, 2, 2, 1, 1);
        mergeBtn.setOnAction(event ->
                System.out.println("TODO")// TODO: hook up merge
        );
        updateMergeButton();
    }

    /**
     * Creates the EventHandler for opening the ini file.
     *
     * @param parentScene   - root Scene.
     * @param description   - file chooser dialog description.
     * @param textIndicator - Label that will indicate to the user which file is loaded.
     * @return created EventHandler with established event.
     */
    private static EventHandler<ActionEvent> getOpenIniHandler(Window parentScene, final String description, Label textIndicator) {
        return event -> {
            File choice = FileChooserHelper.getFile(parentScene, description, FILE_EXTENSIONS);
            if (choice != null && textIndicator != null) {
                textIndicator.setUserData(choice);
                textIndicator.setText(choice.getName());
            }
            updateMergeButton();
        };
    }


    /**
     * Updates 'Merge' button availability status based on the validity of the input files
     */
    private static void updateMergeButton() {
        boolean isValid = isValidInFile(mainFilePathLabel) && isValidInFile(suppFilePathLabel)
                && isValidOutFile(labelOutName);
        mergeBtn.setDisable(!isValid);
    }

    /**
     * Tries to retrieve user data (expected File) from the control and validate it.
     *
     * @param control - holder control.
     * @return true if userData of the given control is a existing file that can be read.
     */
    private static boolean isValidInFile(@NonNull Label control) {
        // User data holds File
        Object userData = control.getUserData();
        if (!(userData instanceof File)) {
            return false;
        }
        File file = (File) userData;

        // Exists & can be read
        return file.exists() && file.canRead();
    }

    /**
     * Tries to retrieve user data (expected String) from the control
     *  and validate if the file can be created from it.
     *
     * @param labelOut - Label with the filename
     * @return true if userData of the given control is an non-existing file that can be created.
     */
    private static boolean isValidOutFile(TextField labelOut) {
        // TODO
        return false;
    }

}
