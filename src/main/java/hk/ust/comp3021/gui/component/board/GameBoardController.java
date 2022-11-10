package hk.ust.comp3021.gui.component.board;

import hk.ust.comp3021.entities.*;
import hk.ust.comp3021.game.GameState;
import hk.ust.comp3021.game.Position;
import hk.ust.comp3021.game.RenderingEngine;
import hk.ust.comp3021.gui.utils.Message;
import hk.ust.comp3021.gui.utils.Resource;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Control logic for a {@link GameBoard}.
 * <p>
 * GameBoardController serves the {@link RenderingEngine} which draws the current game map.
 */
public class GameBoardController implements RenderingEngine, Initializable {
    @FXML
    private GridPane map;

    @FXML
    private Label undoQuota;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // TODO
    }

    /**
     * Draw the game map in the {@link #map} GridPane.
     *
     * @param state The current game state.
     */
    @Override
    public void render(@NotNull GameState state) {
        for (int i = 0; i < state.getMapMaxHeight(); i++)  {
            for (int j = 0; j < state.getMapMaxWidth(); j++) {
                int finalI = i;
                int finalJ = j;
                Platform.runLater(() -> {
                        try {
                            Entity entity = state.getEntity(Position.of(finalJ, finalI));
                            Cell cell = new Cell();
                            if (entity instanceof Wall) {
                                cell.getController().setImage(Resource.getWallImageURL());
                                this.map.add(cell, finalJ, finalI);
                            }
                            else if (entity instanceof Empty) {
                                // Check is it destination
                                if (state.getDestinations().contains(Position.of(finalJ, finalI))) {
                                    cell.getController().setImage(Resource.getDestinationImageURL());
                                }
                                else { // Or just empty cell
                                    cell.getController().setImage(Resource.getEmptyImageURL());
                                }
                                this.map.add(cell, finalJ, finalI);
                            }
                            else if (entity instanceof Box) {
                                cell.getController().setImage(Resource.getBoxImageURL(((Box)entity).getPlayerId()));
                                // Check is it in the destination
                                if (state.getDestinations().contains(Position.of(finalJ, finalI))) {
                                    cell.getController().markAtDestination();
                                }
                                this.map.add(cell, finalJ, finalI);
                            }
                            else if (entity instanceof Player) {
                                cell.getController().setImage(Resource.getPlayerImageURL(((Player)entity).getId()));
                                this.map.add(cell, finalJ, finalI);
                            }
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    });
            }
        }
        // Set Undo Quota at the bottom of cells
        if (state.getUndoQuota().isPresent()) {
            this.undoQuota.setText("Undo Quota: " + state.getUndoQuota().get());
        }
        else {
            this.undoQuota.setText("Undo Quota: Unlimited");
        }

    }

    /**
     * Display a message via a dialog.
     *
     * @param content The message
     */
    @Override
    public void message(@NotNull String content) {
        Platform.runLater(() -> Message.info("Sokoban", content));
    }
}
