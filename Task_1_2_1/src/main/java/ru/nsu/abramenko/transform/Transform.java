package ru.nsu.abramenko.transform;

/** interface.
 * for transforming string to T
 *
 * @param <T> classType
 */
public interface Transform<T> {
    /** transform String to T.
     *
     * @param str any value
     * @return T
     */
    T transform(String str);
}