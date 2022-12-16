/**
 * Normal Pair class which allows two different/similar elements
 * @param T (First Element)
 * @param E (Second Element)
 */
public class Pair<T,E> {

    private T first;
    private E second;

    public Pair(T first, E second) {
        this.first = first;
        this.second = second;
    }

    public T getFirst () { return first; }
    public E getSecond () { return second; }

    public void setFirst (T value) { this.first = value; }
    public void setSecond (E value) { this.second = value; }

    public Pair () { }
}