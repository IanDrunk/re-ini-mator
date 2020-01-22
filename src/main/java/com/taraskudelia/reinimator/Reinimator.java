package com.taraskudelia.reinimator;

import com.taraskudelia.reinimator.view.dialog.FileChooserHelper;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.io.File;

/**
 * @author Taras Kudelia
 * @since 07 Oct 2019
 */
public class Reinimator extends Application {

    private static final int PADDING = 20;
    private static final int H_GAP = 25;
    private static final int V_GAP = 15;

    private static final String MAIN_INI_DESCRIPTION = "Choose path to the MAIN ini file";
    private static final String SUPP_INI_DESCRIPTION = "Choose path to the SECONDARY ini file:";
    private static final String NO_FILE_SELECTED = "NO FILE SELECTED";
    private static final String OPEN = "Open";
    private static final String MERGE = "Merge";

    private static final String[] FILE_EXTENSIONS = new String[] {
            ".ini", ".txt"
    };

    /* target files */
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
     * TODO
     * @param stage
     * @return
     */
    private static Scene getScene(Stage stage) {
        // Set up base GridPane
        GridPane root = new GridPane();
        root.setPadding(new Insets(PADDING));
        root.setHgap(H_GAP);
        root.setVgap(V_GAP);

        createMainFileRow(root, stage);
        createSuppFileRow(root, stage);
        createOutputFileRow(root, stage);

        return new Scene(root, 640, 480);
    }

    /**
     * TODO
     * @param pane
     * @param stage
     */
    private static void createMainFileRow(final GridPane pane, final Stage stage) {
        final int row = 0;

        final Label labelMainIni = new Label(MAIN_INI_DESCRIPTION);
        pane.add(labelMainIni, 0, row, 2, 1);
        final Button openMainBtn = new Button(OPEN);
        pane.add(openMainBtn, 2, row, 1, 1);
        mainFilePathLabel = new Label(NO_FILE_SELECTED);
        pane.add(mainFilePathLabel, 3, row, 2, 1);
        openMainBtn.setOnAction(getOpenIniListener(stage, MAIN_INI_DESCRIPTION, mainFilePathLabel));
    }

    /**
     * TODO
     * @param pane
     * @param stage
     */
    private static void createSuppFileRow(final GridPane pane, final Stage stage) {
        final int row = 1;

        final Label labelSuppIni = new Label(SUPP_INI_DESCRIPTION);
        pane.add(labelSuppIni, 0, row, 2, 1);
        final Button openSuppBtn = new Button(OPEN);
        pane.add(openSuppBtn, 2, row, 1, 1);
        suppFilePathLabel = new Label(NO_FILE_SELECTED);
        pane.add(suppFilePathLabel, 3, row, 2, 1);
        openSuppBtn.setOnAction(getOpenIniListener(stage, SUPP_INI_DESCRIPTION, suppFilePathLabel));
    }

    /**
     * TODO
     * @param pane
     * @param stage
     */
    private static void createOutputFileRow(final GridPane pane, final Stage stage) {
        final int row = 2;

        labelOutName = new TextField();
        labelOutName.setPromptText("Enter resulting ini file name");
        pane.add(labelOutName, 0, row,2,1);
        mergeBtn = new Button(MERGE);
        pane.add(mergeBtn, 2, row, 1, 1);
        mergeBtn.setOnAction(event ->
                System.out.println("TODO")// TODO: hook up merge
        );
    }

    /**
     * TODO
     * @param parentScene
     * @param description
     * @param textIndicator
     * @return
     */
    private static EventHandler<ActionEvent> getOpenIniListener(Window parentScene, final String description, Label textIndicator) {
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
        boolean isValid = isValidFile(mainFilePathLabel) && isValidFile(suppFilePathLabel) && isValidFile(labelOutName);
        mergeBtn.setDisable(!isValid);
    }

    private static boolean isValidFile(Control control) {
        //TODO
        return false;
    }

}
