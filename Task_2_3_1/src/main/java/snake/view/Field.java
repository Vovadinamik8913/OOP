package snake.view;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import snake.Gameinfo;
import snake.model.Direction;
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
    
    public static void drawHead(GraphicsContext gc, Direction direction,
                                Position position, Color color) {
        double x = position.getX() * Gameinfo.CELL_SIZE;
        double y = position.getY() * Gameinfo.CELL_SIZE;
        double size = Gameinfo.CELL_SIZE - 1;

        gc.setFill(color);

        switch (direction) {
            case UP -> {
                gc.fillPolygon(
                        new double[]{x, x + size/2, x + size},
                        new double[]{y + size, y, y + size},
                        3
                );
            }
            case DOWN -> {
                gc.fillPolygon(
                        new double[]{x, x + size/2, x + size},
                        new double[]{y, y + size, y},
                        3
                );
            }
            case LEFT -> {
                gc.fillPolygon(
                        new double[]{x + size, x, x + size},
                        new double[]{y, y + size/2, y + size},
                        3
                );
            }
            case RIGHT -> {
                gc.fillPolygon(
                        new double[]{x, x + size, x},
                        new double[]{y, y + size/2, y + size},
                        3
                );
            }
        }
    }
}
