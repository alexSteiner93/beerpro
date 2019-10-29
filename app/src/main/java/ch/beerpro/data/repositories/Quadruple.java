package ch.beerpro.data.repositories;

import java.io.Serializable;
import java.util.Objects;

import org.apache.commons.lang3.builder.CompareToBuilder;

public class Quadruple<T1, T2, T3, T4> implements Comparable<Quadruple<T1, T2, T3, T4>>, Serializable {
    private T1 first;
    private T2 second;
    private T3 third;
    private T4 fourth;
    private static final long serialVersionUID = 1L;

    public Quadruple() {
    }

    public Quadruple(T1 first, T2 second, T3 third, T4 fourth) {
        this.first = first;
        this.second = second;
        this.third = third;
        this.fourth = fourth;
    }

    public T1 getFirst() {
        return first;
    }

    public void setFirst(T1 first) {
        this.first = first;
    }

    public T2 getSecond() {
        return second;
    }

    public void setSecond(T2 second) {
        this.second = second;
    }

    public T3 getThird() {
        return third;
    }

    public void setThird(T3 third) {
        this.third = third;
    }

    public T4 getFourth() {
        return fourth;
    }

    public void setFourth(T4 fourth) {
        this.fourth = fourth;
    }

    @Override
    public int compareTo(final Quadruple<T1, T2, T3, T4> other) {
        return new CompareToBuilder().append(getFirst(), other.getFirst())
                .append(getSecond(), other.getSecond())
                .append(getThird(), other.getThird())
                .append(getFourth(), other.getFourth())
                .toComparison();
    }

    @Override
    public boolean equals(final Object o) {
        if (o == this) {
            return true;
        }
        if (o instanceof Quadruple<?, ?, ?, ?>) {
            final Quadruple<?, ?, ?, ?> other = (Quadruple<?, ?, ?, ?>) o;
            return Objects.equals(getFirst(), other.getFirst())
                    && Objects.equals(getSecond(), other.getSecond())
                    && Objects.equals(getThird(), other.getThird())
                    && Objects.equals(getFourth(), other.getFourth());
        }
        return false;
    }

    @Override
    public int hashCode() {
        return (getFirst() == null ? 0 : getFirst().hashCode()) ^
                (getSecond() == null ? 0 : getSecond().hashCode()) ^
                (getThird() == null ? 0 : getThird().hashCode()) ^
                (getFourth() == null ? 0 : getFourth().hashCode());
    }

    @Override
    public String toString() {
        return "(" + getFirst() + "," + getSecond() + "," + getThird() + "," + getFourth() + ")";
    }

    public String toString(final String format) {
        return String.format(format, getFirst(), getSecond(), getThird(), getFourth());
    }
}