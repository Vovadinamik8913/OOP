package snake.model.ai;

import snake.model.Direction;

import java.util.Random;

public interface Ai {
    default Direction generate() {
        Random random = new Random();
        int ind = random.nextInt(Direction.values().length);
        return Direction.values()[ind];
    }
}
