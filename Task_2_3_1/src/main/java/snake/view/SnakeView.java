package snake.view;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import snake.model.Position;
import snake.model.Snake;

public class SnakeView implements Drawable {
    private final Snake snake;

    public SnakeView(Snake snake) {
        this.snake = snake;
    }

    @Override
    public void draw(GraphicsContext gc) {
        for (Position p : snake.getBody()) {
            if (snake.getType() == Snake.SnakeType.PLAYER) {
                Field.drawCell(gc, p, Color.GREEN);
            } else {
                Field.drawCell(gc, p, Color.GRAY);
            }
        }
    }
}
