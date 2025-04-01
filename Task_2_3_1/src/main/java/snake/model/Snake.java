package snake.model;

import lombok.Getter;

import java.util.LinkedList;
import java.util.List;

public class Snake {
    @Getter
    private final LinkedList<Position> body;
    private Direction currentDirection;
    private boolean growing;

    public Snake(Position startPosition) {
        body = new LinkedList<>();
        body.add(startPosition);
        currentDirection = Direction.RIGHT;
        growing = false;
    }

    public void move() {
        Position newHead = body.getFirst().add(currentDirection.getVector());
        body.addFirst(newHead);
        if (!growing) {
            body.removeLast();
        }
        growing = false;
    }

    public void grow() {
        growing = true;
    }

    public void setDirection(Direction direction) {
        if (currentDirection.getVector().add(direction.getVector())
                .equals(new Position(0, 0))) {
            return;
        }
        if (body.size() > 1 &&
                body.getFirst().add(direction.getVector())
                .equals(body.get(1))) {
            return;
        }
        currentDirection = direction;
    }

}
