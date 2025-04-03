package snake.controller.objects;

import org.jetbrains.annotations.Nullable;
import snake.model.Food;
import snake.model.Position;
import snake.model.Snake;
import snake.view.Field;

import java.util.List;

public class CollisionsChecker {
    @Nullable
    public static Food collidesWithFood(Snake snake, List<Food> foods) {
        Position head = snake.getBody().getFirst();
        for (Food food : foods) {
            if (head.equals(food.getPosition())) {
                return food;
            }
        }
        return null;
    }

    public static boolean collidesWithField(Snake snake) {
        Position head = snake.getHead();
        return Field.isOutOfBounds(head);
    }

    public static boolean collidesWithSelf(Snake snake) {
        return snake.checkSelfCollision();
    }

    public static boolean collidesWithSnake(Snake snake, List<Snake> snakes) {
        for (Snake snake1 : snakes) {
            if (!snake1.equals(snake)) {
                if (snake1.collidesWith(snake.getHead())) {
                    return true;
                }
            }
        }
        return false;
    }
}
