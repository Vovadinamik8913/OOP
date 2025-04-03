package snake.controller.objects;

import lombok.AllArgsConstructor;
import lombok.Getter;
import snake.model.Direction;
import snake.model.Snake;
import snake.model.ai.Ai;

@Getter
@AllArgsConstructor
public class SnakeController {
    private Snake snake;
    private Ai ai;

    public SnakeController(Snake snake) {
        this.snake = snake;
        this.ai = null;
    }

    public void changeDirection(Direction direction) {
        snake.setDirection(direction);
    }

    public void move() {
        if (ai != null && snake.getType() == Snake.SnakeType.ENEMY) {
            changeDirection(ai.generate());
        }
        snake.move();
    }

    public void grow() {
        snake.grow();
    }
}
