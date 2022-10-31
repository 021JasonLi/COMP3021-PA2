package hk.ust.comp3021.gui.scene.start;

import hk.ust.comp3021.gui.component.maplist.*;
import hk.ust.comp3021.gui.utils.Message;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.SelectionMode;
import javafx.scene.input.DragEvent;
import javafx.stage.FileChooser;
import javafx.stage.Window;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

/**
 * Control logic for {@link  StartScene}.
 */
public class StartController implements Initializable {
    @FXML
    private MapList mapList;

    @FXML
    private Button deleteButton;

    @FXML
    private Button openButton;

    /**
     * Initialize the controller.
     * Load the built-in maps to {@link this#mapList}.
     *
     * @param location  The location used to resolve relative paths for the root object, or
     *                  {@code null} if the location is not known.
     * @param resources The resources used to localize the root object, or {@code null} if
     *                  the root object was not localized.
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // TODO
        this.mapList.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        this.deleteButton.disableProperty().bind(mapList.getSelectionModel().selectedItemProperty().isNull());
        this.openButton.disableProperty().bind(mapList.getSelectionModel().selectedItemProperty().isNull());

        try {
            // load map00.map
            File file00 = new File(Objects.requireNonNull(StartController.class.getClassLoader().getResource("map00.map")).toURI());
            URL url00 = new URL("file", "", file00.getCanonicalPath());
            MapModel mapModel00 = MapModel.load(url00);
            this.mapList.getItems().add(0, mapModel00);

            // load map01.map
            File file01 = new File(Objects.requireNonNull(StartController.class.getClassLoader().getResource("map01.map")).toURI());
            URL url01 = new URL("file", "", file01.getCanonicalPath());
            MapModel mapModel01 = MapModel.load(url01);
            this.mapList.getItems().add(0, mapModel01);

        } catch (IOException | URISyntaxException e) {
            throw new RuntimeException(e);
        }

    }

    /**
     * Event handler for the open button.
     * Display a file chooser, load the selected map and add to {@link this#mapList}.
     * If the map is invalid or cannot be loaded, display an error message.
     *
     * @param event Event data related to clicking the button.
     */
    @FXML
    private void onLoadMapBtnClicked(ActionEvent event) {
        // TODO
        try {
            FileChooser chooser = new FileChooser();
            File file = chooser.showOpenDialog(null);
            if (checkFileValid(file)) {
                URL url = new URL("file", "", file.getCanonicalPath());
                MapModel mapModel = MapModel.load(url);
                mapModel.gameMap().getPlayerIds().size();
                this.mapList.getItems().add(0, mapModel);
            }
            else {
                Message.error("Invalid File", "This is not a valid file!");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Handle the event when the delete button is clicked.
     * Delete the selected map from the {@link this#mapList}.
     */
    @FXML
    public void onDeleteMapBtnClicked() {
        // TODO
        System.out.println("Delete Map" + this.mapList.getSelectionModel().getSelectedIndices());

    }

    /**
     * Handle the event when the map open button is clicked.
     * Retrieve the selected map from the {@link this#mapList}.
     * Fire an {@link MapEvent} so that the {@link hk.ust.comp3021.gui.App} can handle it and switch to the game scene.
     */
    @FXML
    public void onOpenMapBtnClicked() {
        // TODO
        System.out.println("Open Map");
        
    }

    /**
     * Handle the event when a file is dragged over.
     *
     * @param event The drag event.
     * @see <a href="https://docs.oracle.com/javafx/2/drag_drop/jfxpub-drag_drop.htm">JavaFX Drag and Drop</a>
     */
    @FXML
    public void onDragOver(DragEvent event) {
        // TODO
    }

    /**
     * Handle the event when the map file is dragged to this app.
     * <p>
     * Multiple files should be supported.
     * Display error message if some dragged files are invalid.
     * All valid maps should be loaded.
     *
     * @param dragEvent The drag event.
     * @see <a href="https://docs.oracle.com/javafx/2/drag_drop/jfxpub-drag_drop.htm">JavaFX Drag and Drop</a>
     */
    @FXML
    public void onDragDropped(DragEvent dragEvent) {
        // TODO
    }

    public boolean checkFileValid(File file) {
        // check exist
        if (!file.exists()) {
            return false;
        }

        // check extension
        if (!file.getName().endsWith(".map")) {
            return false;
        }

        return true;
    }

    // no. of players
    // same file?

}
