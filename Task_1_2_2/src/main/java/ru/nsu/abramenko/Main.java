package ru.nsu.abramenko;

import ru.nsu.abramenko.hashtableown.HashTableOwn;

/** main class testing with out.
 *
 */
public class Main {

    /** main.
     *
     * @param args args
     */
    public static void main(String[] args) {
        HashTableOwn<String, Number> table = new HashTableOwn();
        table.put("one", 1);
        table.update("one", 1.0);
        table.put("two", 2);
        table.update("two", 2.0);
        System.out.println(table.get("one"));
        System.out.println(table.get("two"));
        table.forEach((s) -> System.out.print(s.getValue() + " "));
        System.out.println();
        for (HashTableOwn.Node i : table) {
            System.out.println(i.getValue());
        }
        System.out.println(table.toString());

        HashTableOwn<String, Number> table1 = new HashTableOwn();
        table1.put("one", 1);
        table1.update("one", 1.0);
        table1.put("two", 2);
        table1.update("two", 2.0);
        table1.put("three", 3);
        table1.forEach((s) -> System.out.print(s.getValue() + " "));
        System.out.println();
        System.out.println(table.equals(table1));
    }
}