package snake.view;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import snake.Gameinfo;
import snake.model.Position;
import snake.model.Snake;

public class SnakeView implements Drawable {
    private final Snake snake;

    public SnakeView(Snake snake) {
        this.snake = snake;
    }

    @Override
    public void draw(GraphicsContext gc) {
        Color color;
        if (snake.getType() == Snake.SnakeType.PLAYER) {
            color = Color.GREEN;
        } else {
            color = Color.GRAY;
        }
        for (int i = 0; i < snake.getBody().size(); i++) {
            Position p = snake.getBody().get(i);
            if (i == 0) {
                Field.drawHead(gc, snake.getCurrentDirection(), p, color);
            } else {
                Field.drawCell(gc, p, color.darker().desaturate());
            }
        }
    }
}
