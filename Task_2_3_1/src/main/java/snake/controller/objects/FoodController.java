package snake.controller.objects;

import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import snake.Gameinfo;
import snake.model.Food;
import snake.model.Position;
import snake.model.Snake;


@Getter
public class FoodController {
    private final List<Food> foods;
    
    public FoodController() {
        foods = new ArrayList<>();
    }

    private boolean collidesWithSnake(List<Snake> snakes, Position position) {
        if (snakes == null || snakes.isEmpty()) {
            return false;
        }

        for (Snake snake : snakes) {
            if (snake.collidesWith(position)) {
                return true;
            }
        }
        return false;
    }

    private boolean collidesAnotherFood(Position position) {
        for (Food food : foods) {
            if (food.getPosition().equals(position)) {
                return true;
            }
        }
        return false;
    }

    public void spawnFood(List<Snake> snakes) {
        if (foods.size() >= Gameinfo.MAX_FOOD_COUNT) {
            return;
        }

        int x, y;
        Position newPos;
        boolean validPosition;

        do {
            x = (int)(Math.random() * Gameinfo.GRID_WIDTH);
            y = (int)(Math.random() * Gameinfo.GRID_HEIGHT);
            newPos = new Position(x, y);
            validPosition = !collidesWithSnake(snakes, newPos) && !collidesAnotherFood(newPos);
        } while (!validPosition);
        
        foods.add(new Food(newPos,
                Math.random() < 0.8 ? Food.FoodType.REGULAR : Food.FoodType.BONUS));
    }

    public void removeFood(List<Food> food) {
        foods.removeAll(food);
    }
}
