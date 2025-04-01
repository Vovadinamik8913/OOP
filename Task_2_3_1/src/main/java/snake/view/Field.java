package snake.view;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import snake.Gameinfo;
import snake.model.Position;

public class Field {
    public static boolean isOutOfBounds(Position position) {
        return position.getX() < 0 || position.getX() >= Gameinfo.GRID_WIDTH ||
                position.getY() < 0 || position.getY() >= Gameinfo.GRID_HEIGHT;
    }

    public static void drawBackground(GraphicsContext gc, double width, double height) {
        gc.clearRect(0, 0, width, height);
        gc.setStroke(Color.WHITE);
        gc.setLineWidth(2);
        gc.strokeRect(0, 0, Gameinfo.GRID_WIDTH * Gameinfo.CELL_SIZE,
                Gameinfo.GRID_HEIGHT * Gameinfo.CELL_SIZE);
    }

    public static void drawCell(GraphicsContext gc, Position position, Color color) {
        gc.setFill(color);
        gc.fillRect(
                position.getX() * Gameinfo.CELL_SIZE,
                position.getY() * Gameinfo.CELL_SIZE,
                Gameinfo.CELL_SIZE - 1,
                Gameinfo.CELL_SIZE - 1);
    }
}
