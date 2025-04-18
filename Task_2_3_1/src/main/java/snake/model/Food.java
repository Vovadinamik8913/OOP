package snake.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Food {
    private final Position position;
    private final FoodType type;

    @Getter
    public enum FoodType {
        REGULAR(1),
        BONUS(2);

        private final int points;

        FoodType(int points) {
            this.points = points;
        }

    }
}
