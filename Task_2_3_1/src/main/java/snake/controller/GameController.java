package snake.controller;

import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import lombok.Getter;
import snake.Gameinfo;
import snake.model.Direction;
import snake.model.Food;
import snake.model.Position;
import snake.view.Field;

@Getter
public class GameController {
    private final SnakeController snakeController;
    private final FoodController foodController;
    private int score = 0;
    private boolean gameInProgress = true;
    private boolean isWin = false;

    public GameController() {
        snakeController = new SnakeController(
                new snake.model.Snake(new Position(Gameinfo.GRID_WIDTH/2, Gameinfo.GRID_HEIGHT/2)));
        foodController = new FoodController();
        foodController.spawnFood(snakeController);
    }

    public void handleKeyPress(KeyEvent event) {
        switch (event.getCode()) {
            case KeyCode.UP:
            case KeyCode.W: snakeController.changeDirection(Direction.UP); break;
            case KeyCode.RIGHT:
            case KeyCode.D: snakeController.changeDirection(Direction.RIGHT); break;
            case KeyCode.DOWN:
            case KeyCode.S: snakeController.changeDirection(Direction.DOWN); break;
            case KeyCode.LEFT:
            case KeyCode.A: snakeController.changeDirection(Direction.LEFT); break;
            default: break;
        }
    }

    public void update() {
        if (!gameInProgress) {
            return;
        }
        snakeController.move();
        checkCollisions();
    }

    private void checkCollisions() {
        Position head = snakeController.getSnake().getBody().getFirst();

        if (Field.isOutOfBounds(head)) {
            gameOver(false);
            return;
        }

        if (snakeController.checkSelfCollision()) {
            gameOver(false);
            return;
        }

        Food collidedFood = null;
        for (Food food : foodController.getFoods()) {
            if (head.equals(food.getPosition())) {
                collidedFood = food;
                snakeController.grow();
                score += food.getType().getPoints();

                if (score >= Gameinfo.WIN_SCORE) {
                    gameOver(true);
                    return;
                }
                break;
            }
        }
        
        if (collidedFood != null) {
            foodController.removeFood(collidedFood);
            foodController.spawnFood(snakeController);
        }
    }

    private void gameOver(boolean win) {
        gameInProgress = false;
        isWin = win;
    }

}
