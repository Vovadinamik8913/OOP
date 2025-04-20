package snake.controller;

import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import lombok.Getter;
import snake.Gameinfo;
import snake.controller.objects.CollisionsChecker;
import snake.controller.objects.FoodController;
import snake.controller.objects.SnakeController;
import snake.model.Direction;
import snake.model.Food;
import snake.model.Position;
import snake.model.Snake;
import snake.model.ai.SmartAi;
import snake.model.ai.SmartestAi;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Getter
public class GameController {
    private final List<SnakeController> snakeControllers;
    private final FoodController foodController;
    private int score = 0;
    private boolean gameInProgress = true;
    private boolean isWin = false;

    private Position getRandomPosition() {
        Random random = new Random();
        return new Position(
            random.nextInt(Gameinfo.GRID_WIDTH),
            random.nextInt(Gameinfo.GRID_HEIGHT)
        );
    }

    private boolean isPositionValid(Position position, List<Snake> existingSnakes) {
        if (position.getX() < 0 || position.getX() >= Gameinfo.GRID_WIDTH ||
            position.getY() < 0 || position.getY() >= Gameinfo.GRID_HEIGHT) {
            return false;
        }

        for (Snake snake : existingSnakes) {
            if (snake.collidesWith(position)) {
                return false;
            }
        }

        return true;
    }

    public GameController() {
        this.snakeControllers = new ArrayList<>();
        this.foodController = new FoodController();

        Snake playerSnake = new Snake(
                new Position(Gameinfo.GRID_WIDTH/2, Gameinfo.GRID_HEIGHT/2),
                Snake.SnakeType.PLAYER);
        snakeControllers.add(new SnakeController(playerSnake));

        List<Snake> existingSnakes = new ArrayList<>();
        existingSnakes.add(playerSnake);


        for (int i = 0; i < Gameinfo.ENEMY_SNAKES_COUNT; i++) {
            Position enemyPos;
            do {
                enemyPos = getRandomPosition();
            } while (!isPositionValid(enemyPos, existingSnakes));

            Snake enemySnake = new Snake(enemyPos, Snake.SnakeType.ENEMY);
            existingSnakes.add(enemySnake);
            SnakeController enemyController = new SnakeController(
                    enemySnake,
                    new SmartestAi(enemySnake, foodController.getFoods())
            );
            snakeControllers.add(enemyController);
        }

        foodController.spawnFood(getSnakes());
    }

    private SnakeController getUser() {
        return snakeControllers.getFirst();
    }

    public void handleKeyPress(KeyEvent event) {
        switch (event.getCode()) {
            case KeyCode.UP:
            case KeyCode.W: getUser().changeDirection(Direction.UP); break;
            case KeyCode.RIGHT:
            case KeyCode.D: getUser().changeDirection(Direction.RIGHT); break;
            case KeyCode.DOWN:
            case KeyCode.S: getUser().changeDirection(Direction.DOWN); break;
            case KeyCode.LEFT:
            case KeyCode.A: getUser().changeDirection(Direction.LEFT); break;
            default: break;
        }
    }

    public void update() {
        if (!gameInProgress) {
            return;
        }

        for (SnakeController controller : snakeControllers) {
            controller.move();
        }

        checkCollisionsAndFood();

        List<Snake> snakes = getSnakes();
        if (foodController.getFoods().size() < Gameinfo.MAX_FOOD_COUNT) {
            foodController.spawnFood(snakes);
        }
    }

    private void checkCollisionsAndFood() {
        List<Food> foodsToRemove = new ArrayList<>();
        List<SnakeController> snakesToRemove = new ArrayList<>();
        List<Snake> allSnakes = getSnakes();

        for (SnakeController controller : snakeControllers) {
            Snake snake = controller.getSnake();

            if (CollisionsChecker.collidesWithField(snake) || 
                CollisionsChecker.collidesWithSelf(snake)) {
                if (snake.getType() == Snake.SnakeType.PLAYER) {
                    gameOver(false);
                    return;
                } else {
                    snakesToRemove.add(controller);
                    continue;
                }
            }

            if (CollisionsChecker.collidesWithSnake(snake, allSnakes)) {
                if (snake.getType() == Snake.SnakeType.PLAYER) {
                    gameOver(false);
                    return;
                } else {
                    snakesToRemove.add(controller);
                    continue;
                }
            }

            Food collidedFood = CollisionsChecker.collidesWithFood(snake, foodController.getFoods());
            if (collidedFood != null) {
                controller.grow();
                if (snake.getType() == Snake.SnakeType.PLAYER) {
                    score += collidedFood.getType().getPoints();
                    
                    if (score >= Gameinfo.WIN_SCORE) {
                        gameOver(true);
                        return;
                    }
                }
                foodsToRemove.add(collidedFood);
            }
        }

        for (SnakeController snake : snakesToRemove) {
            snake.getSnake().destroy();
        }
        snakeControllers.removeAll(snakesToRemove);

        if (!foodsToRemove.isEmpty()) {
            foodController.removeFood(foodsToRemove);
            foodController.spawnFood(getSnakes());
        }
    }

    private List<Snake> getSnakes() {
        List<Snake> snakes = new ArrayList<>();
        for (SnakeController controller : snakeControllers) {
            snakes.add(controller.getSnake());
        }
        return snakes;
    }

    private void gameOver(boolean win) {
        gameInProgress = false;
        isWin = win;
    }
}
