package snake.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.LinkedList;
import java.util.List;

@EqualsAndHashCode
public class Snake {
    @Getter
    public enum SnakeType {
        PLAYER,
        ENEMY
    }

    @Getter
    private final LinkedList<Position> body;
    @Getter
    private Direction currentDirection;
    private boolean growing;
    @Getter
    private final SnakeType type;

    public Snake(Position startPosition, SnakeType type) {
        body = new LinkedList<>();
        body.add(startPosition);
        currentDirection = Direction.RIGHT;
        growing = false;
        this.type = type;
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

    public boolean canMove(Direction direction) {
        if (currentDirection.getVector().add(direction.getVector())
                .equals(new Position(0, 0))) {
            return false;
        }
        return body.size() <= 1 ||
                !body.getFirst().add(direction.getVector())
                        .equals(body.get(1));
    }

    public void setDirection(Direction direction) {
        if (!canMove(direction)) {
            return;
        }
        currentDirection = direction;
    }

    public Position getHead() {
        return body.getFirst();
    }

    public boolean checkSelfCollision() {
        if (body.size() <= 1) {
            return false;
        }
        Position head = body.getFirst();
        return body.subList(1, body.size()).contains(head);
    }

    public boolean collidesWith(Position position) {
        return body.contains(position);
    }

    public void destroy() {
        body.clear();
    }
}
