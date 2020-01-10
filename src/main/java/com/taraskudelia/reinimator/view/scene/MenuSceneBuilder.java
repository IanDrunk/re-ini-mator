package com.taraskudelia.reinimator.view.scene;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

/**
 * Represents Main Menu GUI scene
 */
public class MenuSceneBuilder {

    private static final int PADDING = 20;
    private static final int H_GAP = 25;
    private static final int V_GAP = 15;

    public static Scene getScene() {
        // Set up base GridPane
        GridPane root = new GridPane();
        root.setPadding(new Insets(PADDING));
        root.setHgap(H_GAP);
        root.setVgap(V_GAP);

        // Main INI file
        int row = 0;
        Label labelMainIni = new Label("Choose path to the MAIN ini file:");
        root.add(labelMainIni, 0, row, 2, 1);
        Button openMainBtn = new Button("Open");
        root.add(openMainBtn, 2, row, 1, 1);

        // Supplementary INI file
        row++;
        Label labelSuppIni = new Label("Choose path to the SECONDARY ini file:");
        root.add(labelSuppIni, 0, row, 2, 1);
        Button openSuppBtn = new Button("Open");
        root.add(openSuppBtn, 2, row, 1, 1);

        // Resulting INI file
        row++;
        // TODO: sample text
        TextField labelOutName = new TextField("Enter resulting ini file name");
        root.add(labelOutName, 0, row,2,1);

        // TODO: input validation (images/icons?)
        // TODO: "generate" button
        // TODO: "open file location" button

        return new Scene(root, 640, 480);
    }

}
