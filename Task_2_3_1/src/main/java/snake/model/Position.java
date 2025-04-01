package snake.model;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@EqualsAndHashCode
public class Position {
    private int x;
    private int y;

    public Position add(Position other) {
        return new Position(x + other.x, y + other.y);
    }
}
