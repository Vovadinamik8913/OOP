package ru.nsu.abramenko.pizzeria;

import lombok.Getter;

/** status.
 *
 */
@Getter
public enum Status {
    NOTSTARTED("notstarted"),
    BAKING("baking"),
    BAKED("Baked"),
    DELIVERING("delivering"),
    DONE("done");

    private final String state;

    Status(String state) {
        this.state = state;
    }
}
