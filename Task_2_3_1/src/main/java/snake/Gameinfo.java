package snake;

import lombok.Getter;

@Getter
public class Gameinfo {
    public static final int CELL_SIZE = 20;
    public static final int GRID_WIDTH = 20;
    public static final int GRID_HEIGHT = 20;
    public static final int WIN_SCORE = 20;
    
    // New configuration settings
    public static final int INITIAL_FOOD_COUNT = 3;
    public static final int MAX_FOOD_COUNT = 5;
    public static final long INITIAL_FRAME_INTERVAL = 150_000_000; // 150ms
    public static final long MIN_FRAME_INTERVAL = 50_000_000; // 50ms
    public static final int SPEED_INCREASE_INTERVAL = 5; // Speed up every 5 points
}
