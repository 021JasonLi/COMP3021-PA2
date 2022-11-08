package hk.ust.comp3021.gui;

import hk.ust.comp3021.game.GameState;
import hk.ust.comp3021.gui.component.maplist.MapEvent;
import hk.ust.comp3021.gui.scene.game.ExitEvent;
import hk.ust.comp3021.gui.scene.game.GameScene;
import hk.ust.comp3021.gui.scene.start.StartScene;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * The JavaFX application that launches the game.
 */
public class App extends Application {
    private Stage primaryStage;
    private Stage secondaryStage;

    /**
     * Set up the primary stage and show the {@link StartScene}.
     *
     * @param primaryStage the primary stage for this application, onto which
     *                     the application scene can be set.
     *                     Applications may create other stages, if needed, but they will not be
     *                     primary stages.
     * @throws Exception if something goes wrong.
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
        this.primaryStage = primaryStage;
        primaryStage.setTitle("Sokoban Game - COMP3021 2022Fall");
        Scene scene = new StartScene();
        this.primaryStage.setScene(scene);
        this.primaryStage.addEventHandler(MapEvent.OPEN_MAP_EVENT_TYPE, event -> {
            try {
                onOpenMap(event);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        this.primaryStage.show();
    }

    /**
     * Event handler for opening a map.
     * Switch to the {@link GameScene} in the {@link this#primaryStage}.
     *
     * @param event The event data related to the map being opened.
     * @throws IOException GameScene cannot load then throw it
     */
    public void onOpenMap(MapEvent event) throws IOException {
        this.secondaryStage = new Stage();
        secondaryStage.setTitle("Sokoban Game - COMP3021 2022Fall");
        Scene scene = new GameScene(new GameState(event.getModel().gameMap()));
        this.secondaryStage.setScene(scene);
        this.secondaryStage.addEventHandler(ExitEvent.EVENT_TYPE, this::onExitGame);
        this.primaryStage.hide();
        this.secondaryStage.show();
        
    }

    /**
     * Event handler for exiting the game.
     * Switch to the {@link StartScene} in the {@link this#primaryStage}.
     *
     * @param event The event data related to exiting the game.
     */
    public void onExitGame(ExitEvent event) {
        this.secondaryStage.hide();
        this.primaryStage.show();
    }
}
