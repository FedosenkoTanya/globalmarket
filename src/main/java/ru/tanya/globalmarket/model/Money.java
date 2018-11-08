package ru.tanya.globalmarket.model;

/**
 * Деньги деньги дребеденьги, позабыв покой и лень :)
 */
public class Money {

    public Money(double value, Currency moneyCurrency) {
        this.value = value;
        this.moneyCurrency = moneyCurrency;
    }

    private double value;
    private Currency moneyCurrency;

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public Currency getMoneyCurrency() {
        return moneyCurrency;
    }

    public void setMoneyCurrency(Currency moneyCurrency) {
        this.moneyCurrency = moneyCurrency;
    }

    @Override
    public String toString() {
        return "Money{" +
                "value=" + value +
                ", moneyCurrency=" + moneyCurrency +
                '}';
    }
}