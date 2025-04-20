package snake.model.ai;

import lombok.Setter;
import snake.model.Direction;
import snake.model.Food;
import snake.model.Position;
import snake.model.Snake;

import java.util.List;

public class SmartestAi implements Ai {
    @Setter
    private Snake snake;
    private final List<Food> foods;

    public SmartestAi(Snake snake, List<Food> foods) {
        this.snake = snake;
        this.foods = foods;
    }

    @Override
    public Direction generate() {
        Position head = snake.getHead();
        if (head == null || foods.isEmpty()) {
            return Ai.super.generate();
        }

        Food nearestFood = findNearestFood();
        Position target = nearestFood.getPosition();

        int dx = target.getX() - head.getX();
        int dy = target.getY() - head.getY();

        if (dx > 0 && snake.canMove(Direction.RIGHT)
                && snake.safeMove(Direction.RIGHT)) {
            return Direction.RIGHT;
        }
        if (dx < 0 && snake.canMove(Direction.LEFT)
                && snake.safeMove(Direction.LEFT)) {
            return Direction.LEFT;
        }
        if (dy > 0 && snake.canMove(Direction.DOWN)
                && snake.safeMove(Direction.DOWN)) {
            return Direction.DOWN;
        }
        if (dy < 0 && snake.canMove(Direction.UP)
                && snake.safeMove(Direction.UP)) {
            return Direction.UP;
        }

        return snake.getCurrentDirection();
    }

    private Food findNearestFood() {
        Position head = snake.getHead();
        Food nearest = foods.getFirst();
        int minDistance = getManhattanDistance(head, nearest.getPosition());

        for (Food food : foods) {
            int distance = getManhattanDistance(head, food.getPosition());
            if (distance < minDistance) {
                minDistance = distance;
                nearest = food;
            }
        }
        return nearest;
    }

    private int getManhattanDistance(Position a, Position b) {
        return Math.abs(a.getX() - b.getX()) + Math.abs(a.getY() - b.getY());
    }
}
