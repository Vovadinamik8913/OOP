package snake;

import javafx.animation.AnimationTimer;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import snake.controller.GameController;
import snake.view.GameView;

public class Game {
    @FXML private Canvas gameCanvas;
    @FXML private Label scoreLabel;
    @FXML private Label gameStateLabel;

    private GameController gameController;
    private GameView gameView;
    private long lastUpdate = 0;
    private static final long FRAME_INTERVAL = 150_000_000; // 150ms
    private AnimationTimer animation;
    private long frameInterval = Gameinfo.INITIAL_FRAME_INTERVAL;

    @FXML
    public void initialize() {
        setupGame();
        
        gameCanvas.setOnKeyPressed(e -> {
            if (!gameController.isGameInProgress() && e.getCode() == KeyCode.ENTER) {
                restartGame();
            } else {
                gameController.handleKeyPress(e);
            }
        });
        
        animation = new AnimationTimer() {
            @Override
            public void handle(long now) {
                if (now - lastUpdate >= frameInterval) {
                    update();
                    render();
                    lastUpdate = now;
                }
            }
        };
        animation.start();
    }

    private void setupGame() {
        gameController = new GameController();
        gameView = new GameView(gameCanvas.getGraphicsContext2D(), gameController);
        gameCanvas.setFocusTraversable(true);
        frameInterval = Gameinfo.INITIAL_FRAME_INTERVAL;
        gameStateLabel.setVisible(false);
    }

    private void restartGame() {
        setupGame();
        animation.start();
    }

    private void update() {
        gameController.update();
        updateUI();
        // Update game speed based on score
        frameInterval = Math.max(
            Gameinfo.MIN_FRAME_INTERVAL,
            Gameinfo.INITIAL_FRAME_INTERVAL - 
            (gameController.getScore() / Gameinfo.SPEED_INCREASE_INTERVAL) * 10_000_000
        );
    }

    private void updateUI() {
        scoreLabel.setText("Score: " + gameController.getScore());
        
        if (!gameController.isGameInProgress()) {
            animation.stop();
            gameStateLabel.setText(gameController.isWin() ? "YOU WIN!" : "GAME OVER");
            gameStateLabel.setTextFill(gameController.isWin() ? Color.GREEN : Color.RED);
            gameStateLabel.setVisible(true);
        }
    }

    private void render() {
        gameView.render();
    }
}
