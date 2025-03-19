package ru.nsu.abramenko.pizzeria;


import java.util.UUID;
import lombok.Getter;
import lombok.Setter;


/** order.
 *
 */
@Getter
public class Order {
    private final String name;
    @Setter
    private Status status;

    public Order(String name) {
        this.name = name;
        this.status = Status.NOTSTARTED;
    }

    public Order() {
        this.name = UUID.randomUUID().toString();
        this.status = Status.NOTSTARTED;
    }
}
