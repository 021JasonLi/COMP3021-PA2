package hk.ust.comp3021.gui.component.control;

import hk.ust.comp3021.actions.Action;
import hk.ust.comp3021.actions.Move;
import hk.ust.comp3021.entities.Player;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Control logic for {@link MovementButtonGroup}.
 */
public class MovementButtonGroupController implements Initializable {
    @FXML
    private GridPane playerControl;

    @FXML
    private ImageView playerImage;

    private Player player = null;


    /**
     * Sets the player controller by the button group.
     *
     * @param player The player.
     */
    public void setPlayer(Player player) {
        this.player = player;
    }

    /**
     * The URL to the profile image of the player.
     *
     * @param url The URL.
     */
    public void setPlayerImage(URL url) {
        try {
            this.playerImage.setImage(new Image(url.openStream()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    @FXML
    private void moveUp() {
        try {
            Action action = new Move.Up(this.player.getId());
            ControlPanelController.actionQueue.put(action);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }


    }

    @FXML
    private void moveDown() {
        try {
            Action action = new Move.Down(this.player.getId());
            ControlPanelController.actionQueue.put(action);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }


    }

    @FXML
    private void moveLeft() {
        try {
            Action action = new Move.Left(this.player.getId());
            ControlPanelController.actionQueue.put(action);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

    }

    @FXML
    private void moveRight() {
        try {
            Action action = new Move.Right(this.player.getId());
            ControlPanelController.actionQueue.put(action);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
