package snake.controller;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import lombok.Getter;
import snake.Gameinfo;
import snake.model.Food;
import snake.model.Position;

import java.util.ArrayList;
import java.util.List;

@Getter
public class FoodController {
    private final List<Food> foods;
    
    public FoodController() {
        foods = new ArrayList<>();
        for (int i = 0; i < Gameinfo.INITIAL_FOOD_COUNT; i++) {
            spawnFood(null);
        }
    }

    public void spawnFood(SnakeController snakeController) {
        if (foods.size() >= Gameinfo.MAX_FOOD_COUNT) {
            return;
        }

        int x, y;
        Position newPos = null;
        do {
            x = (int)(Math.random() * Gameinfo.GRID_WIDTH);
            y = (int)(Math.random() * Gameinfo.GRID_HEIGHT);
            newPos = new Position(x, y);
        } while (
                snakeController != null && snakeController.collidesWith(newPos)
        );
        
        foods.add(new Food(newPos,
                Math.random() < 0.8 ? Food.FoodType.REGULAR : Food.FoodType.BONUS));
    }

    public void removeFood(Food food) {
        foods.remove(food);
    }
}
