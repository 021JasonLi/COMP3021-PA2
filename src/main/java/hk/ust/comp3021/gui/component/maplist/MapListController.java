package hk.ust.comp3021.gui.component.maplist;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;

/**
 * Controller logic for {@link  MapList}.
 */
public class MapListController implements Initializable {
    @FXML
    private ListView<MapModel> list;

    /**
     * Initialize the controller.
     * You should customize the items in the list view here.
     * Set the item in the {@link MapList} to be {@link MapListCell}.
     *
     * @param location  The location used to resolve relative paths for the root object, or
     *                  {@code null} if the location is not known.
     * @param resources The resources used to localize the root object, or {@code null} if
     *                  the root object was not localized.
     * @see <a href="https://docs.oracle.com/javafx/2/ui_controls/list-view.htm">JavaFX ListView</a>
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // TODO
//        final var fxml = MapListItemController.class.getClassLoader().getResource("map00.map");
//        System.out.println(fxml);

        this.list = new ListView<>();
        List<MapModel> list = new ArrayList<>();



        ObservableList<MapModel> observableList = FXCollections.observableList(list);
        this.list.setItems(observableList);


    }
}
