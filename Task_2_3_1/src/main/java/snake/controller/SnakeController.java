package snake.controller;

import javafx.scene.input.KeyCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import snake.model.Direction;
import snake.model.Position;
import snake.model.Snake;

import java.util.List;

@Getter
@AllArgsConstructor
public class SnakeController {
    private Snake snake;

    public void changeDirection(Direction direction) {
        snake.setDirection(direction);
    }

    public void move() {
        snake.move();
    }

    public void grow() {
        snake.grow();
    }

    public boolean collidesWith(Position position) {
        return snake.getBody().contains(position);
    }

    public boolean checkSelfCollision() {
        List<Position> body = snake.getBody();
        if (body.size() <= 1) {
            return false;
        }
        Position head = body.getFirst();
        return body.subList(1, body.size()).contains(head);
    }
}
