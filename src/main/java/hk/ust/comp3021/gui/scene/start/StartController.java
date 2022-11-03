package hk.ust.comp3021.gui.scene.start;

import hk.ust.comp3021.gui.App;
import hk.ust.comp3021.gui.component.maplist.*;
import hk.ust.comp3021.gui.utils.Message;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.SelectionMode;
import javafx.scene.input.DragEvent;
import javafx.scene.input.TransferMode;
import javafx.stage.FileChooser;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Path;
import java.util.List;
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
        // set elements setting
        this.mapList.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        this.deleteButton.disableProperty().bind(mapList.getSelectionModel().selectedItemProperty().isNull());
        this.openButton.disableProperty().bind(mapList.getSelectionModel().selectedItemProperty().isNull());
        // load 2 maps
        try {
            // load map00.map
            File file00 = new File(Objects.requireNonNull(StartController.class.getClassLoader().getResource("map00.map")).toURI());
            loadFile(file00);

            // load map01.map
            File file01 = new File(Objects.requireNonNull(StartController.class.getClassLoader().getResource("map01.map")).toURI());
            loadFile(file01);

        } catch (URISyntaxException e) {
            Message.error("Fail to load game map file", "Fail to load pre-loaded maps");
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
        FileChooser chooser = new FileChooser();
        List<File> files = chooser.showOpenMultipleDialog(null);
        // assume only support single file
        if (files != null) {
            loadFile(files.get(0));
        }
    }

    /**
     * Handle the event when the delete button is clicked.
     * Delete the selected map from the {@link this#mapList}.
     */
    @FXML
    public void onDeleteMapBtnClicked() {
        this.mapList.getItems().remove(this.mapList.getSelectionModel().getSelectedIndex());
    }

    /**
     * Handle the event when the map open button is clicked.
     * Retrieve the selected map from the {@link this#mapList}.
     * Fire an {@link MapEvent} so that the {@link hk.ust.comp3021.gui.App} can handle it and switch to the game scene.
     */
    @FXML
    public void onOpenMapBtnClicked() {
        MapEvent mapEvent = new MapEvent(MapEvent.OPEN_MAP_EVENT_TYPE, this.mapList.getItems().get(this.mapList.getSelectionModel().getSelectedIndex()));
        this.openButton.fireEvent(mapEvent);
    }

    /**
     * Handle the event when a file is dragged over.
     *
     * @param event The drag event.
     * @see <a href="https://docs.oracle.com/javafx/2/drag_drop/jfxpub-drag_drop.htm">JavaFX Drag and Drop</a>
     */
    @FXML
    public void onDragOver(DragEvent event) {
        if (event.getDragboard().hasFiles()) {
            event.acceptTransferModes(TransferMode.LINK);
        }
        event.consume();
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
        List<File> files = dragEvent.getDragboard().getFiles();
        for (int i = 0 ; i < files.size(); i++) {
            loadFile(files.get(i));
        }
        dragEvent.consume();
    }


    /**
     * load the given file
     * @param file the file to load
     */
    public void loadFile(File file) {
        if (checkFileValid(file)) { // check valid file or not
            try {
                URL url = new URL("file", "", file.getCanonicalPath());
                MapModel mapModel = MapModel.load(url);
                if (checkPlayerNumber(mapModel)) { // player no within [1, 4]
                    checkSameGameModel(mapModel); // check same file in the list -> remove
                    this.mapList.getItems().add(0, mapModel);
                }
                else {
                    Message.error("Fail to load game map file", "Number of player is invalid in map file " + file.getName());
                }

            } catch (IOException e) {
                Message.error("Fail to load game map file", "Cannot load the map file " + file.getName());
            }
        }
        else {
            Message.error("Fail to load game map file", "Invalid map file " + file.getName());
        }
    }

    /**
     * check whether the given file is valid
     * @param file the file to check
     * @return true if valid, false if not
     */
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

    /**
     * check no. of players in the mapModel
     * @param mapModel the model to check
     * @return true if no. of player within [1, 4], false o.w.
     */
    public boolean checkPlayerNumber(@NotNull MapModel mapModel) {
        if ((mapModel.gameMap().getPlayerIds().size() > 4) || (mapModel.gameMap().getPlayerIds().size() < 1)) {
            return false;
        }
        return true;
    }

    /**
     * Check same game model -> then remove
     * @param mapModel the model to check
     */
    public void checkSameGameModel(@NotNull MapModel mapModel) {
        Path mapModelPath = mapModel.file().toAbsolutePath();
        for (int i = 0; i < this.mapList.getItems().size(); i++) {
            Path mapListItemPath = this.mapList.getItems().get(i).file().toAbsolutePath();
            if (mapModelPath.equals(mapListItemPath)) { // if same then remove
                this.mapList.getItems().remove(i);
                break; // should be at most 1 same file
            }
        }
    }

}
