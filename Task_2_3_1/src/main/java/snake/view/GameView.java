package snake.view;

import javafx.scene.canvas.GraphicsContext;
import snake.controller.GameController;
import snake.controller.objects.SnakeController;

import java.util.ArrayList;
import java.util.List;

public class GameView {
    private final GraphicsContext gc;
    private final List<Drawable> drawables;

    public GameView(GraphicsContext gc, GameController gameController) {
        this.gc = gc;
        drawables = new ArrayList<>();
        for (SnakeController snakeController : gameController.getSnakeControllers()) {
            drawables.add(new SnakeView(snakeController.getSnake()));
        }
        drawables.add(new FoodView(gameController.getFoodController().getFoods()));
    }

    public void render() {
        Field.drawBackground(gc, gc.getCanvas().getWidth(), gc.getCanvas().getHeight());
        for (Drawable drawable : drawables) {
            drawable.draw(gc);
        }
    }
}
