package org.syracus.harominzer.common.tuples;

public class Tuple2<T1, T2> {

    private final T1 t1;
    private final T2 t2;

    public Tuple2(T1 t1, T2 t2) {
        this.t1 = t1;
        this.t2 = t2;
    }

    public T1 getT1() {
        return t1;
    }

    public T2 getT2() {
        return t2;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Tuple2<?, ?> tuple2 = (Tuple2<?, ?>) o;

        if (t1 != null ? !t1.equals(tuple2.t1) : tuple2.t1 != null) return false;
        return t2 != null ? t2.equals(tuple2.t2) : tuple2.t2 == null;
    }

    @Override
    public int hashCode() {
        int result = t1 != null ? t1.hashCode() : 0;
        result = 31 * result + (t2 != null ? t2.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return String.format("(%s, %s)", null != t1 ? t1.toString() : "NULL", null != t2 ? t2.toString() : "NULL");
    }
}
