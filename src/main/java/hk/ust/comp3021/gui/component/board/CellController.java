package hk.ust.comp3021.gui.component.board;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import org.jetbrains.annotations.NotNull;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Control logic for  a {@link Cell}.
 */
public class CellController implements Initializable {
    @FXML
    private ImageView image;

    @FXML
    private Label mark;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    /**
     * Adds a check mark to the cell.
     * Should be called when the cell is one of the  destinations and there is a box.
     */
    public void markAtDestination() {
        StackPane stackPane = new StackPane();
        stackPane.getChildren().addAll(this.image, this.mark);
    }

    /**
     * Sets the image to be display on the cell.
     *
     * @param url The URL to the image.
     */
    public void setImage(@NotNull URL url) {
        try {
            this.image.setImage(new Image(url.openStream()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
