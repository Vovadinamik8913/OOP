package snake.view;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import snake.model.Food;

import java.util.List;

public class FoodView implements Drawable {
    private final List<Food> foods;

    public FoodView(List<Food> foods) {
        this.foods = foods;
    }

    @Override
    public void draw(GraphicsContext gc) {
        for (Food food : foods) {
            Color foodColor = food.getType() == Food.FoodType.REGULAR ?
                    Color.RED : Color.GOLD;
            Field.drawCell(gc, food.getPosition(), foodColor);
        }
    }
}
