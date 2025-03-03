package ru.nsu.abramenko.pizzeria;

import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;
import lombok.Synchronized;


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
        this.name = String.valueOf(LocalDateTime.now());
        this.status = Status.NOTSTARTED;
    }
}
