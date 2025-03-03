package ru.nsu.abramenko.pizzeria;


import java.util.UUID;
import lombok.Getter;
import lombok.Setter;
import lombok.Synchronized;


/** order.
 *
 */
@Getter
public class Order {
    private final String name;
    @Getter(onMethod_ = {@Synchronized})
    @Setter(onMethod_ = {@Synchronized})
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
