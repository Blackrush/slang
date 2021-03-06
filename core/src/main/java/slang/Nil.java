package slang;

import java.util.AbstractList;
import java.util.function.BiFunction;
import java.util.function.Function;

/**
 * @author Antoine Chauvin
 */
public final class Nil extends AbstractList<Object> implements SList {
    public static final Nil NIL = new Nil();
    public static final Nil nil = NIL;

    private Nil() {}

    @Override
    public Object get(int index) {
        throw new IndexOutOfBoundsException();
    }

    @Override
    public int size() {
        return 0;
    }

    @Override
    public boolean isEmpty() {
        return true;
    }

    @Override
    public Object head() {
        throw new UnsupportedOperationException();
    }

    @Override
    public SList tail() {
        return this;
    }

    @Override
    public <T> T foldl(T initial, BiFunction<T, Object, T> function) {
        return initial;
    }

    @Override
    public Object fold(BiFunction<Object, Object, Object> function) {
        return this;
    }

    @Override
    public SList map(Function<Object, Object> function) {
        return this;
    }

    @Override
    public int hashCode() {
        return 0;
    }

    @Override
    public boolean equals(Object o) {
        return o == this;
    }

    @Override
    public String toString() {
        return "nil";
    }
}
