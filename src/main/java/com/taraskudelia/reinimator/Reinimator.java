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
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.Window;
import lombok.Getter;
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
    private static final int BASE_PADDING = 20;
    private static final int NODE_PADDING = 5;
    private static final int H_GAP = 10;
    private static final int V_GAP = 8;
    private static final int BTN_WIDTH = 60;

    /* Colors */
    private static final String COLOR_INFO_LABEL_OK = "e1ecf4";
    private static final String COLOR_INFO_LABEL_ERROR = "fbffbb";
    private static final String COLOR_INFO_LABEL_WARNING = "ff9e9e";

    /* String literals for the GUI */
    private static final String MAIN_INI_DESCRIPTION = "Choose path to the MAIN ini file";
    private static final String SUPP_INI_DESCRIPTION = "Choose path to the SECONDARY ini file:";
    private static final String OUT_FILE_NAME_PLACEHOLDER = "Enter resulting ini file name";
    private static final String BTN_TXT_OPEN = "Open";
    private static final String BTN_TXT_MERGE = "Merge";
    private static final String LBL_TXT_EMPTY = " \n ";
    private static final String MSG_SELECT_FILES = "Please, select input ini files fro the merge.";

    /* String format */
    private static final String ERR_RW_PERMISSION = "Read/write rights not permitted for the file %s.\nPlease, change file settings.";
    private static final String ERR_FILE_EXISTS = "File %s is already exists.\nPlease, change output file name.";
    private static final String ERR_FILE_DOESNT_EXISTS = "File %s doesn't exists or read rights not permitted.\nPlease, choose another file or change current file settings.";

    private static final String[] FILE_EXTENSIONS = new String[] {
            ".ini", ".txt"
    };

    private static Label mainFilePathLabel = null;
    private static Label suppFilePathLabel = null;
    private static Label infoLabel = null;
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
        root.setPadding(new Insets(BASE_PADDING));
        root.setHgap(H_GAP);
        root.setVgap(V_GAP);

        createMainFileRow(root, stage);
        createSuppFileRow(root, stage);
        createOutputFileRow(root);
        createFeedbackRow(root);

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
        labelMainIni.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        pane.add(labelMainIni, 0, 0, 2, 1);

        final Button openMainBtn = new Button(BTN_TXT_OPEN);
        openMainBtn.setMinWidth(BTN_WIDTH);
        openMainBtn.setPrefWidth(BTN_WIDTH);
        openMainBtn.setMaxWidth(BTN_WIDTH);
        pane.add(openMainBtn, 2, 0, 1, 1);

        mainFilePathLabel = new Label();
        mainFilePathLabel.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
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
        labelSuppIni.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        pane.add(labelSuppIni, 0, 1, 2, 1);

        final Button openSuppBtn = new Button(BTN_TXT_OPEN);
        openSuppBtn.setMinWidth(BTN_WIDTH);
        openSuppBtn.setPrefWidth(BTN_WIDTH);
        openSuppBtn.setMaxWidth(BTN_WIDTH);
        pane.add(openSuppBtn, 2, 1, 1, 1);

        suppFilePathLabel = new Label();
        suppFilePathLabel.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
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
        labelOutName.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        pane.add(labelOutName, 0, 2,2,1);

        mergeBtn = new Button(BTN_TXT_MERGE);
        mergeBtn.setMinWidth(BTN_WIDTH);
        mergeBtn.setPrefWidth(BTN_WIDTH);
        mergeBtn.setMaxWidth(BTN_WIDTH);
        pane.add(mergeBtn, 2, 2, 1, 1);

        mergeBtn.setOnAction(event ->
                updateInfoLabel("Not implemented yet!\nSORRY! This is ok, tho.", LabelStatus.OK)
        );
        updateMergeButton();
    }

    /**
     * Creates the feedback row and fills it with controls.
     *
     * @param pane  - parent GridPane.
     */
    private static void createFeedbackRow(final GridPane pane) {
        infoLabel = new Label();
        infoLabel.setPadding(new Insets(NODE_PADDING));
        infoLabel.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        updateInfoLabel(LBL_TXT_EMPTY, LabelStatus.OK);
        pane.add(infoLabel, 0, 3, 3, 2);

        // TODO font-size
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
        if (infoLabel != null) {
            boolean isValid = isValidInFile(mainFilePathLabel) && isValidInFile(suppFilePathLabel)
                    && isValidOutFile(labelOutName);
            mergeBtn.setDisable(!isValid);
        }
    }

    /**
     * Updates info label text with the provided message and sets it's background color depending on the status.
     *
     * @param message - Text to display on the info label. May be null.
     * @param status  - Status of the program process.
     */
    private static void updateInfoLabel(String message, LabelStatus status) {
        infoLabel.setText(message == null ? LBL_TXT_EMPTY : message);
        infoLabel.setBackground(new Background(new BackgroundFill(status.getColor(), CornerRadii.EMPTY, Insets.EMPTY)));
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
            updateInfoLabel(MSG_SELECT_FILES, LabelStatus.OK);
            return false;
        }
        File file = (File) userData;

        // Exists & can be read
        if (!file.exists()) {
            updateInfoLabel(String.format(ERR_FILE_DOESNT_EXISTS, file.getName()), LabelStatus.WARNING);
            return false;
        } else if (!file.canRead()) {
            updateInfoLabel(String.format(ERR_RW_PERMISSION, file.getName()), LabelStatus.WARNING);
            return false;
        }
        return true;
    }

    /**
     * Tries to retrieve user data (expected String) from the control
     *  and validate if the file can be created from it.
     *
     * @param labelOut - Label with the filename
     * @return true if userData of the given control is an non-existing file that can be created.
     */
    private static boolean isValidOutFile(@NonNull TextField labelOut) {
        // File location = main file path + label file name
        final File outFile = new File((File) mainFilePathLabel.getUserData(), labelOut.getText());

        if (outFile.exists()) {
            updateInfoLabel(String.format(ERR_FILE_EXISTS, outFile.getName()), LabelStatus.WARNING);
            return false;
        }
        if (!(outFile.canRead() && outFile.canWrite())) {
            updateInfoLabel(String.format(ERR_RW_PERMISSION, outFile.getName()), LabelStatus.ERROR);
            return false;
        }
        return true;
    }

    /**
     * Label statuses with colors.
     */
    private enum LabelStatus {
        OK(Color.web(COLOR_INFO_LABEL_OK)),
        WARNING(Color.web(COLOR_INFO_LABEL_WARNING)),
        ERROR(Color.web(COLOR_INFO_LABEL_ERROR));

        @Getter
        private final Color color;

        LabelStatus(Color color) {
            this.color = color;
        }

    }

}
